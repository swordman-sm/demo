package com.example.demo;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

public class Test2 {

    public static void main(String[] args) {
//        char[] aI = "1234567".toCharArray();
//        char[] aC = "ABCDEFG".toCharArray();
//
//        TransferQueue<Character> queue = new LinkedTransferQueue<>();
//
//        new Thread(() -> {
//            try {
//                for (char c : aI) {
//                    System.err.println(queue.take());
//                    queue.transfer(c);
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, "t1").start();
//
//        new Thread(() -> {
//            try {
//                for (char c : aC) {
//                    queue.transfer(c);
//                    System.err.println(queue.take());
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, "t2").start();

        Object o = new Object();

    }

}
