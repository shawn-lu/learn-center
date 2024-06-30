package com.shawn.concurrentdemo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {

    public static void main(String[] args) throws Exception{
        Counter counter = new Counter();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000; ++i) {
                counter.add(1);
            }
        });
        t1.start();
        Thread t2 =new Thread(() -> {
            for (int i = 0; i < 100000; ++i) {
                counter.add(-1);
            }
        });
        t2.start();
        t1.join();
        t2.join();
        System.out.println(counter.count);
    }


    public static class Counter {
        private final Lock lock = new ReentrantLock();
        private int count;

        public void add(int n) {
            lock.lock();
            try {
                count += n;
            } finally {
                lock.unlock();
            }
        }
    }
}
