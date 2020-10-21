package com.shawn;

import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        GenericObjectPool<StringBuffer> pool = new GenericObjectPool<StringBuffer>(new StringBufferFactory());
        System.out.println(pool.getMaxTotal());
//        pool.setMaxTotal(11);
//        pool.setBlockWhenExhausted(false);
//        pool.setMaxWaitMillis(3000);

        System.out.println(pool.getMaxBorrowWaitTimeMillis());
        final ReaderUtil readerUtil = new ReaderUtil(pool);
        ExecutorService es = Executors.newFixedThreadPool(4);
        for (int i = 0; i <= 3; i++) {
            es.submit(() -> {
                String s = null;
                try {
                    System.out.println("Start");
                    s = readerUtil.readToString(new FileReader("E:\\myspace\\commons-pool2-learn\\pom.xml"));
                    System.out.println("End");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        while (true) {
        }
    }


//    void demo() {
//
//        Object obj = null;
//
//        try {
//            obj = pool.borrowObject();
//            try {
//                //...use the object...
//            } catch (Exception e) {
//                // invalidate the object
//                pool.invalidateObject(obj);
//                // do not return the object to the pool twice
//                obj = null;
//            } finally {
//                // make sure the object is returned to the pool
//                if (null != obj) {
//                    pool.returnObject(obj);
//                }
//            }
//        } catch (Exception e) {
//            // failed to borrow an object
//        }
//    }
}
