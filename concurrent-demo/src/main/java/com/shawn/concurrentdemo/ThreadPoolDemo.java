package com.shawn.concurrentdemo;

import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author luxufeng
 * @date 2020/10/23
 **/
public class ThreadPoolDemo {
    public static void main(String[] args) {
        //code 2,max 2,keepalive 0 LinkedBlockingQueue(Integer.MAX_VALUE) 因为队列太长容易OOM，不建议用
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
        //code 0,max 60,keepalive 60s SynchronousQueue()
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        //code 4,max Integer.MAX_VALUE,keepalive 0 delayQueue() //使用DelayedWorkQueue实现
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(4);
        scheduledThreadPool.schedule(() -> System.out.println("exec"),3000, TimeUnit.MILLISECONDS);
    }
}
