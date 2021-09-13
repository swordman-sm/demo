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
    public Map<String, Object> waits(String taskId, String dpId, Integer start, Integer length) {
        System.err.println("8888888888888888888888888888888");
        taskId = taskId.trim();
        dpId = dpId.trim();
        Map<String, Object> maps = new HashMap<>();
        ListOperations<String, TaskEntity> listOperations = redisTemplate.opsForList();
        List<TaskEntity> taskEntities = listOperations.range(ThreadConstant.TASK_WAIT_KEY, 0, -1);
        List<TaskEntity> taskList = new ArrayList<>();

        return getTaskMap(taskList, maps, taskEntities, taskId, dpId, start, length);
    }

    //查看已进入线程池的BlockingQueue数据
    @RequestMapping("/queue")
    @ResponseBody
    public Map<String, Object> queue(String taskId, String dpId, Integer start, Integer length) {
        System.err.println(taskId);
        System.err.println(dpId);
        System.err.println(start);
        System.err.println(length);
        Map<String, Object> maps = new HashMap<>();
        HashOperations<String, String, TaskEntity> hashOperations = redisTemplate.opsForHash();
        Map<String, TaskEntity> taskEntityMap = hashOperations.entries(ThreadConstant.TASK_QUEUE_KEY);
        List<TaskEntity> taskList = new ArrayList<>();
        return getTaskMap(taskList, maps, taskEntityMap.values(), taskId, dpId, start, length);
    }

    //查看正在线程池核心线程执行的数据
    @RequestMapping("/run")
    @ResponseBody
    public Map<String, Object> run(String taskId, String dpId, Integer start, Integer length) {
        Map<String, Object> maps = new HashMap<>();
        HashOperations<String, String, TaskEntity> hashOperations = redisTemplate.opsForHash();
        Map<String, TaskEntity> taskEntityMap = hashOperations.entries(ThreadConstant.TASK_RUN_KEY);
        List<TaskEntity> taskList = new ArrayList<>();
        return getTaskMap(taskList, maps, taskEntityMap.values(), taskId, dpId, start, length);
    }

    //查看停止状态数据
    @RequestMapping("/stop")
    @ResponseBody
    public Map<String, Object> stop(String taskId, String dpId, Integer start, Integer length, String done) {

        //每页第一个元素的id
        start = start == null ? 0 : start;
        //每页的容量 默认为1000个
        length = length == null ? 1000 : length;
        boolean isEmpty = StringUtils.isEmpty(done);
        done = isEmpty ? "all" : done.toLowerCase();

        Map<String, Object> maps = new HashMap<>();
        HashOperations<String, String, TaskEntity> hashOperations = redisTemplate.opsForHash();

        String pattern = "*" + (StringUtils.isEmpty(taskId) ? "_*" : "_" + taskId) + (StringUtils.isEmpty(dpId) ? "_*" : "_" + dpId);
        ScanOptions scanOptions = ScanOptions.scanOptions().match(pattern).count(1000).build();
        Cursor<Map.Entry<String, TaskEntity>> entryCursor = hashOperations.scan(ThreadConstant.TASK_STOP_KEY, scanOptions);

        List<TaskEntity> taskList = new ArrayList<>();
        int i = 0;
        int k = 0;
        if ("true".equals(done)) {
            while (entryCursor.hasNext()) {
                Map.Entry<String, TaskEntity> entityEntry = entryCursor.next();
                TaskEntity taskEntity = entityEntry.getValue();
                if (taskEntity.getStatus().equals(TaskStatusEnum.COMPLETE.getCode())) {
                    if (i >= start && taskList.size() < length) {
                        taskEntity.setId(i + 1);
                        taskList.add(taskEntity);
                    }
                    i++;
                }
                k++;
            }
        }

        if ("false".equals(done)) {
            while (entryCursor.hasNext()) {
                Map.Entry<String, TaskEntity> entityEntry = entryCursor.next();
                TaskEntity taskEntity = entityEntry.getValue();
                if (!taskEntity.getStatus().equals(TaskStatusEnum.COMPLETE.getCode())) {
                    if (i >= start && taskList.size() < length) {
                        taskEntity.setId(i + 1);
                        taskList.add(taskEntity);
                    }
                    i++;
                }
                k++;
            }
        }

        if ("all".equals(done)) {
            while (entryCursor.hasNext()) {
                Map.Entry<String, TaskEntity> entityEntry = entryCursor.next();
                TaskEntity taskEntity = entityEntry.getValue();
                if (i >= start && taskList.size() < length) {
                    taskEntity.setId(i + 1);
                    taskList.add(taskEntity);
                }
                i++;
                k++;
            }
        }
        maps.put("recordsTotal", i);
        maps.put("recordsFiltered", k);
        maps.put("data", taskList);
        System.err.println(maps);
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
        System.err.println(taskEntity.getPriority());
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


    public Map<String, Object> getTaskMap(List<TaskEntity> taskList, Map<String, Object> maps, Collection<TaskEntity> taskEntities, String taskId, String
            dpId, Integer start, Integer length) {
        int i = 0;
        int k = 0;
        if (taskEntities != null) {
            if (StringUtils.isEmpty(taskId) && StringUtils.isEmpty(dpId)) {
                for (TaskEntity taskEntity : taskEntities) {
                    if (i >= start && taskList.size() < length) {
                        taskEntity.setId(i + 1);
                        taskList.add(taskEntity);
                    }
                    i++;
                    k++;
                }
            }

            if (!StringUtils.isEmpty(taskId) && StringUtils.isEmpty(dpId)) {
                for (TaskEntity taskEntity : taskEntities) {
                    if (taskEntity.getTaskId().equals(taskId)) {
                        if (i >= start && taskList.size() < length) {
                            taskEntity.setId(i + 1);
                            taskList.add(taskEntity);
                        }
                        i++;
                    }
                    k++;
                }
            }

            if (StringUtils.isEmpty(taskId) && !StringUtils.isEmpty(dpId)) {
                for (TaskEntity taskEntity : taskEntities) {
                    if (taskEntity.getDpId().equals(dpId)) {
                        if (i >= start && taskList.size() < length) {
                            taskEntity.setId(i + 1);
                            taskList.add(taskEntity);
                        }
                        i++;
                    }
                    k++;
                }
            }

            if (!StringUtils.isEmpty(taskId) && !StringUtils.isEmpty(dpId)) {
                for (TaskEntity taskEntity : taskEntities) {
                    if (taskEntity.getTaskId().equals(taskId) && taskEntity.getDpId().equals(dpId)) {
                        if (i >= start && taskList.size() < length) {
                            taskEntity.setId(i + 1);
                            taskList.add(taskEntity);
                        }
                        i++;
                    }
                    k++;
                }
            }
        }
        maps.put("recordsTotal", k);
        maps.put("recordsFiltered", i);
        maps.put("data", taskList);
        return maps;
    }

}
