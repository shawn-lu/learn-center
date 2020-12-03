package com.shawn.concurrentdemo;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author luxufeng
 * @date 2020/10/22
 **/
public class LockDemo {

    public static void main(String[] args) {
        final Lock lock = new ReentrantLock();

        new Thread(() -> {
            while(true){
                lock.lock();
                System.out.println("xx");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
        lock.lock();
        lock.lock();
        lock.unlock();
        lock.unlock();
        Account account = new Account();
        for (int i = 1; i <= 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        int i = new Random().nextInt(4);
                        if (i != 0) {
                            account.plus(new Random().nextInt(4));
                        }

                    }
                }
            }).start();
        }

        for (int i = 1; i <= 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        int i = new Random().nextInt(4);
                        if (i != 0) {
                            account.minus(i);
                        }

                    }
                }
            }).start();
        }
    }


    final static class Account {
        final Lock lock = new ReentrantLock();
        String userName = "张三";
        Integer money = 10;

        private void plus(Integer i) {
            try {
                Thread.sleep(2000);
                lock.lock();
                money = money + i;
                System.out.println(userName + "存储" + i + "元,当前余额" + money);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        private void minus(Integer i) {
            try {
                Thread.sleep(2000);
                lock.lock();
                if (money - i < 0) {
                    System.out.println(userName + "取出" + i + "元失败,余额不足,当前余额" + money);
                    return;
                }
                money = money - i;
                System.out.println(userName + "取出" + i + "元,当前余额" + money);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public Integer getMoney() {
            return money;
        }

        public Account setMoney(Integer money) {
            this.money = money;
            return this;
        }

        public String getUserName() {
            return userName;
        }

        public Account setUserName(String userName) {
            this.userName = userName;
            return this;
        }
    }
}
