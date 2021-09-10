package cn.cjk.timerM.domain;

import java.io.Serializable;

/**
 * @author Timer Tsui
 * @version 1.0
 * @description 节点相关配置
 * @date 2021/08/16 14:22
 **/
public class MachineRelate implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer maxRetry;

    private Integer maxQueueSize;

    private Integer execTimeout;

    private Integer alarmTimeout;

    private String host;

    private String port;

    private Integer liveStatus;

    private Boolean enabled;

    private String startTime;

    private String lastAliveTime;

    public Integer getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(Integer liveStatus) {
        this.liveStatus = liveStatus;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getMaxRetry() {
        return maxRetry;
    }

    public void setMaxRetry(Integer maxRetry) {
        this.maxRetry = maxRetry;
    }

    public Integer getAlarmTimeout() {
        return alarmTimeout;
    }

    public void setAlarmTimeout(Integer alarmTimeout) {
        this.alarmTimeout = alarmTimeout;
    }


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getLastAliveTime() {
        return lastAliveTime;
    }

    public void setLastAliveTime(String lastAliveTime) {
        this.lastAliveTime = lastAliveTime;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getMaxQueueSize() {
        return maxQueueSize;
    }

    public void setMaxQueueSize(Integer maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
    }

    public Integer getExecTimeout() {
        return execTimeout;
    }

    public void setExecTimeout(Integer execTimeout) {
        this.execTimeout = execTimeout;
    }

    @Override
    public String toString() {
        return "MachineRelate{" +
                "maxRetry=" + maxRetry +
                ", maxQueueSize=" + maxQueueSize +
                ", execTimeout=" + execTimeout +
                ", alarmTimeout=" + alarmTimeout +
                ", host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", liveStatus=" + liveStatus +
                ", enabled=" + enabled +
                ", startTime='" + startTime + '\'' +
                ", lastAliveTime='" + lastAliveTime + '\'' +
                '}';
    }
}
