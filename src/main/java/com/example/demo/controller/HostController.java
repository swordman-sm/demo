package com.example.demo.controller;

import cn.cjk.timerM.domain.MachineRelate;
import cn.cjk.timerM.domain.TaskEntity;
import com.example.demo.model.MachineRelateResponseVO;
import com.example.demo.model.ReturnT;
import com.example.demo.model.ThreadConstant;
import com.example.demo.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Timer Tsui
 * @version 1.0
 * @description
 * @date 2021/09/10 16:38
 **/
@Controller
@Component
@RequestMapping("/hostinfo")
@Slf4j
public class HostController {


    @Resource
    private RedisTemplate<String, TaskEntity> redisTemplate;


    @PostMapping("/getConfig")
    @ResponseBody
    public Map<String, Object> getConfig() {
        Map<String, Object> maps = new HashMap<>();
        List<MachineRelateResponseVO> machineRelateList = new ArrayList<>();

        HashOperations<String, String, MachineRelate> hashOperations1 = redisTemplate.opsForHash();
        HashOperations<String, String, TaskEntity> hashOperations2 = redisTemplate.opsForHash();
        Map<String, MachineRelate> configMap = hashOperations1.entries(ThreadConstant.MACHINE_CONFIG);
        Map<String, TaskEntity> runTaskMap = hashOperations2.entries(ThreadConstant.TASK_RUN_KEY);
        Map<String, Long> runCountMap = runTaskMap.values().stream().collect(Collectors.
                groupingBy(ts -> ts.getHost() + "_" + ts.getPort(), Collectors.counting()));
        Map<String, TaskEntity> queueTaskMap = hashOperations2.entries(ThreadConstant.TASK_QUEUE_KEY);
        Map<String, Long> timeoutMap = runTaskMap.values().stream()
                .filter(taskEntity -> {
                    try {
                        return DateUtil.getDistanceMinutes(taskEntity.getUpdateTime(), DateUtil.nowDateForStrYMDHMS()) > 10;
                    } catch (Exception e) {
                        return false;
                    }
                }).collect(Collectors.
                        groupingBy(ts -> ts.getHost() + "_" + ts.getPort(), Collectors.counting()));
        Map<String, Long> queueCountMap = queueTaskMap.values().stream().collect(Collectors.
                groupingBy(ts -> ts.getHost() + "_" + ts.getPort(), Collectors.counting()));

        TreeMap<String, MachineRelate> treeMap = new TreeMap<>(configMap);
        int id = 1;
        for (Map.Entry<String, MachineRelate> entry : treeMap.entrySet()) {
            MachineRelate machineRelate = entry.getValue();
            MachineRelateResponseVO machineRelateResponseVO = new MachineRelateResponseVO(machineRelate);
            machineRelateResponseVO.setId(id);
            machineRelateResponseVO.setRun(runCountMap.getOrDefault(entry.getKey(), 0L));
            machineRelateResponseVO.setQueue(queueCountMap.getOrDefault(entry.getKey(), 0L));
            machineRelateResponseVO.setTimeout(timeoutMap.getOrDefault(entry.getKey(), 0L));
            machineRelateList.add(machineRelateResponseVO);
            id++;
        }
        maps.put("recordsTotal", configMap.size());
        maps.put("recordsFiltered", configMap.size());
        maps.put("data", machineRelateList);
        log.error(machineRelateList.toString());
        return maps;
    }

    /**
     * ??????????????????
     *
     * @param
     * @return
     */
    @PostMapping("/setConfig")
    @ResponseBody
    public ReturnT<String> setConfig(String id, String type) {
        ReturnT<String> result = ReturnT.FAIL;
        if (!StringUtils.hasLength(id) || !StringUtils.hasLength(type)) {
            result.setMsg("id or type is null");
            return result;
        }

        HashOperations<String, String, MachineRelate> hashOperations = redisTemplate.opsForHash();
        MachineRelate machineRelate = hashOperations.get(ThreadConstant.MACHINE_CONFIG, id);
        if (machineRelate == null) {
            result.setMsg("host info is already deleted or not exists!");
            return result;
        }
        result = ReturnT.SUCCESS;
        switch (type) {
            case "disabled":
                machineRelate.setEnabled(false);
                redisTemplate.opsForHash().put(ThreadConstant.MACHINE_CONFIG, id, machineRelate);
                break;
            case "enabled":
                machineRelate.setEnabled(true);
                redisTemplate.opsForHash().put(ThreadConstant.MACHINE_CONFIG, id, machineRelate);
                break;
            case "delete":
                redisTemplate.opsForHash().delete(ThreadConstant.MACHINE_CONFIG, id);
                break;
            default:
                result = ReturnT.FAIL;
                result.setMsg("type not support!");
        }
        return result;
    }

    /**
     * ??????????????????
     *
     * @param
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    public ReturnT<String> update(MachineRelate machineRelate) {
        ReturnT<String> result = ReturnT.SUCCESS;
        String hashKey = machineRelate.getHost() + "_" + machineRelate.getPort();
        HashOperations<String, String, MachineRelate> hashOperations = redisTemplate.opsForHash();
        if (hashOperations.hasKey(ThreadConstant.MACHINE_CONFIG, hashKey)) {
            redisTemplate.opsForHash().put(ThreadConstant.MACHINE_CONFIG, hashKey, machineRelate);
            return result;
        }
        return ReturnT.FAIL;
    }


    /**
     //     * ??????????????????
     //     *
     //     * @param machineRelate
     //     * @return
     //     */
//    @PostMapping("/setConfig")
//    @ResponseBody
//    public String setConfig(@RequestBody MachineRelate machineRelate) {
//        if (machineRelate == null) {
//            return "?????????????????????...";
//        }
//
//        String hashKey = machineRelate.getHost() + "_" + machineRelate.getPort();
//
//        //????????????????????????????????????0
//        if (machineRelate.getAlarmTimeout() == null || machineRelate.getAlarmTimeout() < 0) {
//            machineRelate.setAlarmTimeout(10);
//        }
//        if (machineRelate.getExecTimeout() == null || machineRelate.getExecTimeout() < 0) {
//            machineRelate.setExecTimeout(20);
//        }
//        if (machineRelate.getMaxQueueSize() == null || machineRelate.getMaxQueueSize() < 0) {
//            machineRelate.setMaxQueueSize(30);
//        }
//        if (machineRelate.getMaxRetry() == null || machineRelate.getMaxRetry() < 0) {
//            machineRelate.setMaxRetry(3);
//        }
//
//        redisTemplate.opsForHash().put(ThreadConstant.MACHINE_CONFIG, hashKey, machineRelate);
//        return "success";
//    }


//    /**
//     * ??????????????????
//     *
//     * @param machineRelate
//     * @return
//     */
//    @PostMapping("/setConfig")
//    @ResponseBody
//    public String setConfig(@RequestBody MachineRelate machineRelate) {
//        if (machineRelate == null) {
//            return "?????????????????????...";
//        }
//
//        String hashKey = machineRelate.getHost() + "_" + machineRelate.getPort();
//
//        //????????????????????????????????????0
//        if (machineRelate.getAlarmTimeout() == null || machineRelate.getAlarmTimeout() < 0) {
//            machineRelate.setAlarmTimeout(10);
//        }
//        if (machineRelate.getExecTimeout() == null || machineRelate.getExecTimeout() < 0) {
//            machineRelate.setExecTimeout(20);
//        }
//        if (machineRelate.getMaxQueueSize() == null || machineRelate.getMaxQueueSize() < 0) {
//            machineRelate.setMaxQueueSize(30);
//        }
//        if (machineRelate.getMaxRetry() == null || machineRelate.getMaxRetry() < 0) {
//            machineRelate.setMaxRetry(3);
//        }
//
//        redisTemplate.opsForHash().put(ThreadConstant.MACHINE_CONFIG, hashKey, machineRelate);
//        return "success";
//    }

}
