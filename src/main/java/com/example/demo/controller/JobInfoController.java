package com.example.demo.controller;

import cn.cjk.timerM.domain.TaskEntity;
import cn.cjk.timerM.domain.TaskStatusEnum;
import com.example.demo.model.ReturnT;
import com.example.demo.model.ThreadConstant;
import com.example.demo.utils.DateUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/jobinfo")
public class JobInfoController {

    @Resource
    private RedisTemplate<String, TaskEntity> redisTemplate;

    @RequestMapping
    public String index(Model model) {
        System.err.println("===================");
        return "jobinfo/jobinfo.index";
    }

    //查看已进入wait队列数据
    @RequestMapping("/wait")
    @ResponseBody
    public List<TaskEntity> waits(String taskId, String dpId, Integer pageSize, Integer pageNum) {
        System.err.println("8888888888888888888888888888888");
        ListOperations<String, TaskEntity> listOperations = redisTemplate.opsForList();
        List<TaskEntity> taskEntities = listOperations.range(ThreadConstant.TASK_WAIT_KEY, 0, -1);
        List<TaskEntity> taskList = getTaskList(taskEntities, taskId, dpId, pageSize, pageNum, taskEntities.size(), "");
        System.err.println(taskList);
        return taskList;
    }

    //查看已进入线程池的BlockingQueue数据
    @RequestMapping("/queue")
    @ResponseBody
    public List<TaskEntity> queue(String taskId, String dpId, Integer pageSize, Integer pageNum) {

        HashOperations<String, String, TaskEntity> hashOperations = redisTemplate.opsForHash();
        Map<String, TaskEntity> taskEntityMap = hashOperations.entries(ThreadConstant.TASK_QUEUE_KEY);
        return getTaskList(taskEntityMap.values(), taskId, dpId, pageSize, pageNum, taskEntityMap.size(), "");
    }

    //查看正在线程池核心线程执行的数据
    @RequestMapping("/run")
    @ResponseBody
    public List<TaskEntity> run(String taskId, String dpId, Integer pageSize, Integer pageNum) {
        HashOperations<String, String, TaskEntity> hashOperations = redisTemplate.opsForHash();
        Map<String, TaskEntity> taskEntityMap = hashOperations.entries(ThreadConstant.TASK_RUN_KEY);
        return getTaskList(taskEntityMap.values(), taskId, dpId, pageSize, pageNum, taskEntityMap.size(), "");
    }

    //查看停止状态数据
    @RequestMapping("/stop")
    @ResponseBody
    public Map<String, Object> stop(String taskId, String dpId, Integer start, Integer length, String done) {

        Map<String, Object> maps = new HashMap<>();
        HashOperations<String, String, TaskEntity> hashOperations = redisTemplate.opsForHash();
//        Map<String, TaskEntity> taskEntityMap = hashOperations.entries(ThreadConstant.TASK_STOP_KEY);

        String pattern = "*" + (StringUtils.isEmpty(taskId) ? "_*" : "_" + taskId) + (StringUtils.isEmpty(dpId) ? "_*" : "_" + dpId);
        ScanOptions scanOptions = ScanOptions.scanOptions().match(pattern).count(1000).build();
        Cursor<Map.Entry<String, TaskEntity>> entryCursor = hashOperations.scan(ThreadConstant.TASK_STOP_KEY, scanOptions);

        List<TaskEntity> taskList = new ArrayList<>();
        int i = 0;
        while (entryCursor.hasNext()) {
            Map.Entry<String, TaskEntity> entityEntry = entryCursor.next();
            TaskEntity taskEntity = entityEntry.getValue();

            if (i >= start && taskList.size() < length) {
                taskEntity.setId(i + 1);
                taskList.add(taskEntity);
                System.out.println("遍历绑定键获取所有值:" + entityEntry.getKey() + "---" + entityEntry.getValue());
            }
            i++;
        }
        maps.put("recordsTotal", i);
        maps.put("recordsFiltered", i);
        maps.put("data", taskList);
        return maps;
    }

    @RequestMapping("/deleteTask")
    @ResponseBody
    public ReturnT<String> deleteTask(TaskEntity taskEntity) {
        System.err.println(taskEntity);
        HashOperations<String, String, TaskEntity> hashOperations = redisTemplate.opsForHash();
        String hashKey = taskEntity.getDate() + "_" + taskEntity.getTaskId() + "_" + taskEntity.getDpId();
        Long delete = hashOperations.delete(ThreadConstant.TASK_STOP_KEY, hashKey);

        if (delete > 0) {
            return ReturnT.SUCCESS;
        }

        return ReturnT.FAIL;
    }

    @RequestMapping("/retryTask")
    @ResponseBody
    public ReturnT<String> retryTask(TaskEntity taskEntity) {
        taskEntity.setHost(null);
        taskEntity.setPort(null);
        taskEntity.setStatus(TaskStatusEnum.WAIT.getCode());
        taskEntity.setRemark(TaskStatusEnum.WAIT.getStatus());
        taskEntity.setCreateTime(DateUtil.nowDateForStrYMDHMS());
        taskEntity.setUpdateTime(null);

        String hashKey = taskEntity.getDate() + "_" + taskEntity.getTaskId() + "_" + taskEntity.getDpId();

        Long delete = redisTemplate.opsForHash().delete(ThreadConstant.TASK_STOP_KEY, hashKey);
        if (delete > 0) {
            Long size = redisTemplate.opsForList().leftPush(ThreadConstant.TASK_WAIT_KEY, taskEntity);
            if (size != null && size > 0) {
                return ReturnT.SUCCESS;
            }
        }
        //1.2.将最新run状态写入新map
        return ReturnT.FAIL;
    }


    public List<TaskEntity> getTaskList(Collection<TaskEntity> taskCollection, String taskId, String dpId, Integer pageSize, Integer pageNum, int size, String done) {
        List<TaskEntity> list = new ArrayList<>();
        //wait数据为空
        if (size == 0) {
            return list;
        }

        if (StringUtils.isEmpty(done)) {
            list = taskCollection.stream().filter(taskEntity -> (StringUtils.isEmpty(dpId) || taskEntity.getTaskId().equals(taskId)) &&
                    (StringUtils.isEmpty(taskId) || taskEntity.getDpId().equals(dpId))).collect(Collectors.toList());
        } else {
            done = done.toLowerCase();
            //stop接口查询complete与exception需求 true为正常执行完毕任务逻辑
            if ("true".equals(done)) {
                list = taskCollection.stream().filter(taskEntity -> (StringUtils.isEmpty(dpId) || taskEntity.getTaskId().equals(taskId)) &&
                        (StringUtils.isEmpty(taskId) || taskEntity.getDpId().equals(dpId))
                        && taskEntity.getStatus().equals(TaskStatusEnum.COMPLETE.getCode())).collect(Collectors.toList());
            } else {
                list = taskCollection.stream().filter(taskEntity -> (StringUtils.isEmpty(dpId) || taskEntity.getTaskId().equals(taskId)) &&
                        (StringUtils.isEmpty(taskId) || taskEntity.getDpId().equals(dpId))
                        && !taskEntity.getStatus().equals(TaskStatusEnum.COMPLETE.getCode())).collect(Collectors.toList());
            }
        }

        if (pageNum == null || pageSize == null) {
            return list;
        }
        int maxPage = size % pageSize == 0 ? size / pageSize : size / pageSize + 1;
        if (pageNum > maxPage || pageNum <= 0) {
            return new ArrayList<>();
        } else {
            int begin = (pageNum - 1) * pageSize;
            int end = Math.min(pageNum * pageSize, size);
            return list.subList(begin, end);
        }
    }

}
