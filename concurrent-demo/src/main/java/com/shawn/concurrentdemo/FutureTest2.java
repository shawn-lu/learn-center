package com.shawn.concurrentdemo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author luxufeng
 * @date 2019/9/7
 **/
public class FutureTest2 {
    public static void main(String[] args) {
        Callable<String> t1 = new Task1();
        FutureTask<String> f = new FutureTask<>(t1);
        Thread th1 = new Thread(f);
        th1.start();
        try{
            Thread.sleep(2000);
            f.cancel(false);
            String result = f.get();
            System.out.println(result);
        }catch (InterruptedException ie){
            ie.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("main over");
    }

    static class Task1 implements Callable<String>{
        @Override
        public String call() {
            System.out.println("task1 start");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("task1 interrupt");
                e.printStackTrace();
            }
            System.out.println("task1 over");
            return "abc";
        }
    }
}
