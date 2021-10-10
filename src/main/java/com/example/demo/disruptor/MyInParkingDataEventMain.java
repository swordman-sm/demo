package com.example.demo.disruptor;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyInParkingDataEventMain {
    public static void main(String[] args) {
        long beginTime = System.currentTimeMillis();
        // 2的N次方
        int bufferSize = 2048;
        try {
            //创建线程池，负责处理Disruptor的四个消费者
            ExecutorService executor = Executors.newFixedThreadPool(4);

            // 初始化一个 Disruptor
            Disruptor<MyInParkingDataEvent> disruptor = new Disruptor<MyInParkingDataEvent>(() -> {
                return new MyInParkingDataEvent(); // Event 初始化工厂
            }, bufferSize, executor, ProducerType.SINGLE, new YieldingWaitStrategy());

            // 使用disruptor创建消费者组 MyParkingDataInDbHandler 和 MyParkingDataToKafkaHandler
            EventHandlerGroup<MyInParkingDataEvent> handlerGroup = disruptor.handleEventsWith(
                    new MyParkingDataInDbHandler(), new MyParkingDataToKafkaHandler());

            // 当上面两个消费者处理结束后在消耗 smsHandler
            MyParkingDataSmsHandler myParkingDataSmsHandler = new MyParkingDataSmsHandler();
            handlerGroup.then(myParkingDataSmsHandler);

            // 启动Disruptor
            disruptor.start();
            // 一个生产者线程准备好了就可以通知主线程继续工作了
            CountDownLatch countDownLatch = new CountDownLatch(1);
            // 生产者生成数据
            executor.submit(new MyInParkingDataEventPublisher(countDownLatch, disruptor));
            // 等待生产者结束
            countDownLatch.await();

            disruptor.shutdown();
            executor.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("总耗时:" + (System.currentTimeMillis() - beginTime));
    }

}
