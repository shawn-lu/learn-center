package com.shawn.concurrentdemo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.StampedLock;

public class Client {

    private double x, y;
    final StampedLock sl = new StampedLock();
    // 存在问题的方法
    void moveIfAtOrigin(double newX, double newY) {
        long stamp = sl.readLock();
        try {
            while (x == 0.0 && y == 0.0) {
                long ws = sl.tryConvertToWriteLock(stamp);
                System.out.println(Thread.currentThread().getName()+":"+x+","+y);
                if (ws != 0L) {
                    x = newX;
                    y = newY;
                    System.out.println(Thread.currentThread().getName()+":"+x+","+y);

                    stamp = ws;
                    break;
                } else {
                    sl.unlockRead(stamp);
                    stamp = sl.writeLock();
                }
            }
        } finally {
            sl.unlock(stamp);
        }
    }

    public static void main(String[] args) {
        CopyOnWriteArrayList x;

        Client client = new Client();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                client.moveIfAtOrigin(1D,2D);
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                client.moveIfAtOrigin(11D,22D);
            }
        });
        t1.start();
        t2.start();

        AtomicInteger i = new AtomicInteger(0);
        i.incrementAndGet();
    }
}

class Allocator {
    private List<Object> als = new ArrayList<>();

    // 一次性申请所有资源
    synchronized boolean apply(
            Object from, Object to) {
        if (als.contains(from) ||
                als.contains(to)) {
            return false;
        } else {
            als.add(from);
            als.add(to);
        }
        return true;
    }

    // 归还资源
    synchronized void free(
            Object from, Object to) {
        als.remove(from);
        als.remove(to);
    }
}

class Account {
    private String name;
    // actr 应该为单例
    private Allocator actr;
    private int balance;

    public Account(String name, int balance) {
        this.name = name;
        this.balance = balance;
    }

    // 转账
    void transfer(Account target, int amt) {
        // 一次性申请转出账户和转入账户，直到成功
        while (!actr.apply(this, target)) {
            try {
                // 锁定转出账户
                synchronized (this) {
                    // 锁定转入账户
                    synchronized (target) {
                        if (this.balance > amt) {
                            this.balance -= amt;
                            target.balance += amt;
                        }
                    }
                }
            } finally {
                actr.free(this, target);
            }
        }
    }
}

