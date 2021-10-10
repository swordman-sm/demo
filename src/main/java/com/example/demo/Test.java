package com.example.demo;

import org.openjdk.jol.info.ClassLayout;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Timer Tsui
 * @version 1.0
 * @description
 * @date 2021/09/13 09:23
 **/
public class Test {

    private static final ScheduledExecutorService scheduledExecutorService =
            new ScheduledThreadPoolExecutor(2 * Runtime.getRuntime().availableProcessors(), r -> new Thread(r, "notice-send-sms-schedule"));


    private void tt(AtomicReference<String> code, AtomicReference<String> check) {
        code.set("qqq");
        check.set("wwww");
    }

    public static void main(String[] args) {

//        for (int i = 0; i < 15; i++) {
//            System.err.println("延迟任务: " + i);
//            int finalI = i;
//            scheduledExecutorService.schedule(() -> {
//                System.err.println("延迟任务打印: " + finalI);
//            }, 3, TimeUnit.SECONDS);
//        }

//        AtomicReference<String> code = new AtomicReference<>("8888");
//        AtomicReference<String> check = new AtomicReference<>("8888");
//
//
//        Test test = new Test();
//        test.tt(code, check);
//
//        System.err.println(code);
//        System.err.println(check);

//        DeferredResult<String> deferredResult = new DeferredResult<>();
//
//        for (int i = 0; i < 10; i++) {
//            CompletableFuture.supplyAsync(() -> {
//                System.err.println("测试....." + new Random().nextInt());
//                try {
//                    TimeUnit.SECONDS.sleep(2);
//                } catch (InterruptedException e) {
//                }
//                return "success";
//            }).whenComplete((r, t) -> {
//                if (t != null) {
//                    deferredResult.setErrorResult(t);
//                } else {
//                    deferredResult.setResult(r);
//                }
//            });
//            Object result = deferredResult.getResult();
//
//            System.err.println(result);
//        }

        Object o = new Object();
        System.err.println(ClassLayout.parseInstance(o).toPrintable());


    }

}
