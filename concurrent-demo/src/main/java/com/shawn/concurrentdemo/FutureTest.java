package com.shawn.concurrentdemo;

import java.util.concurrent.*;

/**
 * @author luxufeng
 * @date 2019/9/6
 **/
public class FutureTest {
    public static void main(String[] args) throws Exception{
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("before thread");
                Thread.sleep(5000);
                System.out.println("after thread");
                return 1;
            }
        });
        System.out.println("go omn");

        Integer result = future.get(1000,TimeUnit.SECONDS);
        System.out.println(result);
        System.out.println("over");
    }

}
