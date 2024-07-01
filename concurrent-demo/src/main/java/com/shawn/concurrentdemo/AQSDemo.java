package com.shawn.concurrentdemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AQSDemo {
    private final static ReentrantLock lock = new ReentrantLock();

    private final static ExecutorService es = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        Counter counter = new Counter();
        for(int i=0;i<=49;i++){
            es.submit(new Runnable() {
                @Override
                public void run() {
                    counter.add(1);
                }
            });
        }


        Runnable t1 = new Runnable(){
            @Override
            public void run() {
                try{
                   lock.lock();
                    System.out.println("first");
                }finally {
                    lock.unlock();
                }
            }
        };

        counter.add(1);

        Thread t2 = new Thread();




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

        public int getCount() {
            return count;
        }
    }
}
