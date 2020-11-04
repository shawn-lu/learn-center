package com.shawn.concurrentdemo;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author luxufeng
 * @date 2020/10/26
 **/
public class CountDownLatchDemo implements Runnable {
    static CountDownLatch end = new CountDownLatch(5);
    static CountDownLatchDemo demo = new CountDownLatchDemo();

    @Override
    public void run() {
        try {
            Thread.sleep(new Random().nextInt(10) * 1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("do something");
        end.countDown();
    }

    public static void main(String[] args) {
        ExecutorService p = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 5; i++) {
            p.submit(demo);
        }
        try {
            end.await();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("finish ");

        try {
            end.await();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("finish twice");
    }
}