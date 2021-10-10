package com.example.demo.disruptor;


import com.lmax.disruptor.EventHandler;

public class MyParkingDataToKafkaHandler implements EventHandler<MyInParkingDataEvent> {

    @Override
    public void onEvent(MyInParkingDataEvent myInParkingDataEvent, long sequence, boolean endOfBatch)
            throws Exception {
        // 获取当前线程id
        long threadId = Thread.currentThread().getId();
        // 获取车牌号
        String carLicense = myInParkingDataEvent.getCarLicense();
        System.out.println(String.format("Thread Id %s 发送 %s 进入停车场信息给 kafka系统...", threadId, carLicense));
    }

}
