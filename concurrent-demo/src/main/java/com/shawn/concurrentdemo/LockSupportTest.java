package com.shawn.concurrentdemo;

import java.util.concurrent.locks.LockSupport;

/**
 * @author luxufeng
 * @date 2020/10/22
 **/
public class LockSupportTest {
    public static void main(String[] args) throws InterruptedException {
        parkEg();
//        waitEg();
        System.out.println("主线程执行结束");
    }

    static void parkEg() throws InterruptedException {
        Thread t = new Thread(new ParkThread());
        t.start();
        System.out.println(t.isInterrupted()); //false
        Thread.sleep(100); //①
        System.out.println(Thread.currentThread().getName() + "开始唤醒阻塞线程");
        t.interrupt(); //中断
        System.out.println(t.isInterrupted()); //true
        System.out.println(Thread.currentThread().getName() + "结束唤醒");
    }

    static void waitEg() throws InterruptedException {
        Thread t = new Thread(new WaitThread());
        t.start();
        System.out.println(t.isInterrupted()); //false
        Thread.sleep(100); //①
        System.out.println(Thread.currentThread().getName() + "开始唤醒阻塞线程");
        t.interrupt(); //中断
        System.out.println(t.isInterrupted()); //true
        System.out.println(Thread.currentThread().getName() + "结束唤醒");
    }
}

class ParkThread implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ",ParkThread开始阻塞");
        LockSupport.park();
        System.out.println(Thread.currentThread().getName() + ",ParkThread第一次结束阻塞");
//        Thread.currentThread().interrupted(); ////清除中断标志 重置为false //如果这行被注释掉，后面的park不会阻塞
        LockSupport.park();
        System.out.println(Thread.currentThread().getName() + ",ParkThread第二次结束阻塞");
        System.out.println(Thread.currentThread().getName() + ",ParkThread执行结束");
    }
}


class WaitThread implements Runnable {

    @Override
    public synchronized void run() {
        System.out.println(Thread.currentThread().getName() + ",WaitThread开始阻塞");
        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ",WaitThread第一次结束阻塞");
//        Thread.currentThread().interrupted(); ////清除中断标志 重置为false //如果这行被注释掉，后面的park不会阻塞
        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ",WaitThread第二次结束阻塞");
        System.out.println(Thread.currentThread().getName() + ",WaitThread执行结束");
    }
}