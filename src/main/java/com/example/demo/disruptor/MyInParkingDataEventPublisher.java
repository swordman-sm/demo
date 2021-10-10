package com.example.demo.disruptor;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.CountDownLatch;

/**
 * 生产者，进入停车场的车辆
 */
public class MyInParkingDataEventPublisher implements Runnable {

    // 用于监听初始化操作，等初始化执行完毕后，通知主线程继续工作
    private CountDownLatch countDownLatch;
    private Disruptor<MyInParkingDataEvent> disruptor;
    // 1,10,100,1000
    private static final Integer NUM = 1;

    public MyInParkingDataEventPublisher(CountDownLatch countDownLatch,
                                         Disruptor<MyInParkingDataEvent> disruptor) {
        this.countDownLatch = countDownLatch;
        this.disruptor = disruptor;
    }

    @Override
    public void run() {
        MyInParkingDataEventTranslator eventTranslator = new MyInParkingDataEventTranslator();
        try {
            for (int i = 0; i < NUM; i++) {
                disruptor.publishEvent(eventTranslator);
                // 假设一秒钟进一辆车
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 执行完毕后通知 await()方法
            countDownLatch.countDown();
            System.out.println(NUM + "辆车已经全部进入进入停车场！");
        }
    }

}

class MyInParkingDataEventTranslator implements EventTranslator<MyInParkingDataEvent> {

    @Override
    public void translateTo(MyInParkingDataEvent myInParkingDataEvent, long sequence) {
        this.generateData(myInParkingDataEvent);
    }

    private MyInParkingDataEvent generateData(MyInParkingDataEvent myInParkingDataEvent) {
        myInParkingDataEvent.setCarLicense("车牌号： 鄂A-" + (int) (Math.random() * 100000)); // 随机生成一个车牌号
        System.out.println("Thread Id " + Thread.currentThread().getId() + " 写完一个event");
        return myInParkingDataEvent;
    }

}
