package com.example.demo.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

public class MyParkingDataInDbHandler implements EventHandler<MyInParkingDataEvent>, WorkHandler<MyInParkingDataEvent> {

    @Override
    public void onEvent(MyInParkingDataEvent myInParkingDataEvent) throws Exception {
        // 获取当前线程id
        long threadId = Thread.currentThread().getId();
        // 获取车牌号
        String carLicense = myInParkingDataEvent.getCarLicense();
        System.out.println(String.format("Thread Id %s 保存 %s 到数据库中 ....", threadId, carLicense));
    }

    @Override
    public void onEvent(MyInParkingDataEvent myInParkingDataEvent, long sequence, boolean endOfBatch)
            throws Exception {
        this.onEvent(myInParkingDataEvent);
    }

}
