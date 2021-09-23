package com.example.demo.model;

/**
 * @author Timer Tsui
 * @version 1.0
 * @description 常量
 * @date 2021/08/20 13:51
 **/
public class ThreadConstant {

    //日报相关redis key设定
    public static final int INTERVAL = 30;

    public static final String MACHINE_CONFIG = "dtimer_m_machine_config";

    public static final String urlFormat = "http://%s:%s/timer/report/beat";

    public static final String TASK_WAIT_KEY = "dtimer_m_task_detail_wait";

    public static final String TASK_QUEUE_KEY = "dtimer_m_task_detail_queue";

    public static final String TASK_RUN_KEY = "dtimer_m_task_detail_run";

    public static final String TASK_STOP_KEY = "dtimer_m_task_detail_stop";


}
