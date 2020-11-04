package com.shawn.concurrentdemo;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author luxufeng
 * @date 2020/10/26
 **/
public class CyclicBarrierDemo {
    public static class Solider implements Runnable {
        final CyclicBarrier cb;
        int code;

        Solider(CyclicBarrier cb, int code) {
            this.cb = cb;
            this.code = code;
        }

        @Override
        public void run() {
            try {
                cb.await();
                doWork();
                cb.await();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        void doWork() {
            try {
                System.out.println("士兵" + code + "在干活");
                Thread.sleep(Math.abs(new Random().nextInt() % 10000));
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static class BattierRun implements Runnable {
        boolean flag;
        int n;

        BattierRun(boolean flag, int n) {
            this.flag = flag;
            this.n = n;
        }

        public void run() {
            if (flag) {
                System.out.println("司令:[士兵" + n + "个任务完成]");
            } else {
                System.out.println("司令:[士兵" + n + "个集合完成]");
                flag = true;
            }
        }
    }

    public static void main(String[] args) {
        final int n = 10;
        Thread[] allSolider = new Thread[n];
        boolean flag = false;
        CyclicBarrier cb = new CyclicBarrier(n, new BattierRun(flag, n));
        System.out.println("集合队伍");
        for (int i = 0; i < n; i++) {
            allSolider[i] = new Thread(new Solider(cb, i));
            allSolider[i].start();
        }
    }
}
