package com.example.demo.controller;

import cn.cjk.timerM.domain.MachineRelate;
import com.example.demo.model.MachineRelateResponseVO;
import com.example.demo.model.TaskEntity;
import com.example.demo.model.ThreadConstant;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Timer Tsui
 * @version 1.0
 * @description
 * @date 2021/09/10 16:38
 **/
@Controller
@RequestMapping("/hostinfo")
public class HostController {


    @Resource
    private RedisTemplate<String, TaskEntity> redisTemplate;

    @PostMapping("/getConfig")
    @ResponseBody
    public Map<String, Object> getConfig() {
        System.err.println("11111111111111111111111111111111111");
        Map<String, Object> maps = new HashMap<>();
        List<MachineRelateResponseVO> machineRelateList = new ArrayList<>();

        HashOperations<String, String, MachineRelate> hashOperations1 = redisTemplate.opsForHash();
        HashOperations<String, String, TaskEntity> hashOperations2 = redisTemplate.opsForHash();
        Map<String, MachineRelate> configMap = hashOperations1.entries(ThreadConstant.MACHINE_CONFIG);
        Map<String, TaskEntity> runTaskMap = hashOperations2.entries(ThreadConstant.TASK_RUN_KEY);
        Map<String, Long> runCountMap = runTaskMap.values().stream().collect(Collectors.
                groupingBy(ts -> ts.getHost() + "_" + ts.getPort(), Collectors.counting()));
        Map<String, TaskEntity> queueTaskMap = hashOperations2.entries(ThreadConstant.TASK_QUEUE_KEY);
        Map<String, Long> queueCountMap = queueTaskMap.values().stream().collect(Collectors.
                groupingBy(ts -> ts.getHost() + "_" + ts.getPort(), Collectors.counting()));

        for (Map.Entry<String, MachineRelate> entry : configMap.entrySet()) {
            MachineRelate machineRelate = entry.getValue();
            MachineRelateResponseVO machineRelateResponseVO = new MachineRelateResponseVO(machineRelate);
            machineRelateResponseVO.setRun(runCountMap.getOrDefault(entry.getKey(), 0L));
            machineRelateResponseVO.setQueue(queueCountMap.getOrDefault(entry.getKey(), 0L));
            machineRelateList.add(machineRelateResponseVO);
        }
        maps.put("data", machineRelateList);
        return maps;
    }

}
