package cn.cjk.timerM.domain;

public enum TaskStatusEnum {

    WAIT(1, "WAIT"),
    QUEUE(2, "QUEUE"),
    RUN(3, "RUN"),
    REVIVAL(4, "REVIVAL"),
    COMPLETE(5, "COMPLETE"),
    CANCEL(6, "CANCEL"),
    EXCEPT(7, "Exception");

    private Integer code;
    private String status;

    TaskStatusEnum(Integer code, String status) {
        this.code = code;
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
