package com.example.demo.model;

import cn.cjk.timerM.domain.MachineRelate;

/**
 * @author Timer Tsui
 * @version 1.0
 * @description 节点相关配置
 * @date 2021/08/16 14:22
 **/
public class MachineRelateResponseVO extends MachineRelate {

    private Long run;

    private Long queue;

    public MachineRelateResponseVO() {
    }

    public MachineRelateResponseVO(MachineRelate machineRelate) {
        this.setEnable(machineRelate.getEnable());
        this.setLiveStatus(machineRelate.getLiveStatus());
        this.setAlarmTimeout(machineRelate.getAlarmTimeout());
        this.setExecTimeout(machineRelate.getExecTimeout());
        this.setHost(machineRelate.getHost());
        this.setPort(machineRelate.getPort());
        this.setLastAliveTime(machineRelate.getLastAliveTime());
        this.setMaxQueueSize(machineRelate.getMaxQueueSize());
        this.setMaxRetry(machineRelate.getMaxRetry());
        this.setStartTime(machineRelate.getStartTime());
    }

    public Long getRun() {
        return run;
    }

    public void setRun(Long run) {
        this.run = run;
    }

    public Long getQueue() {
        return queue;
    }

    public void setQueue(Long queue) {
        this.queue = queue;
    }
}
