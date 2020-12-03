package com.shawn.concurrentdemo;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author luxufeng
 * @date 2020/10/21
 **/
public class ExecutorDemo {
    //用一个变量存储两个状态，互不干扰。不必为了维护两者的一致，而占用锁资源。
    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    private static final int COUNT_BITS = Integer.SIZE - 3;  //29
    private static final int CAPACITY = (1 << COUNT_BITS) - 1; //0001...1

    // runState is stored in the high-order bits 看前3位高位
    private static final int RUNNING = -1 << COUNT_BITS; //1110...0
    private static final int SHUTDOWN = 0 << COUNT_BITS; // 0
    private static final int STOP = 1 << COUNT_BITS; //0010...0
    private static final int TIDYING = 2 << COUNT_BITS; //0100...0
    private static final int TERMINATED = 3 << COUNT_BITS; //0110...0

    // Packing and unpacking ctl
    private static int runStateOf(int c) {
        return c & ~CAPACITY;
    } //计算当前运行状态

    private static int workerCountOf(int c) {
        return c & CAPACITY;
    } //计算当前线程数量

    private static int ctlOf(int rs, int wc) {
        return rs | wc;
    } //通过状态和工作线程数生成ctl

    public static void main(String[] args) {
//        ExecutorService es = Executors.newFixedThreadPool(10);
//        es.execute(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("do something");
//            }
//        });
        for (;;) {
            try {
                Thread.sleep(2000);
                System.out.println("xx");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void s(){

        System.out.println("xx");

    }
}
