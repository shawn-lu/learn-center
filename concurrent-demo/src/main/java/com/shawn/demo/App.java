package com.shawn.demo;

import sun.misc.Unsafe;

public class App {
    public static void main(String[] args) {
        Unsafe.getUnsafe(); //Exception in thread "main" java.lang.SecurityException: Unsafe

/**
 *  CAS
 * @param o         包含要修改field的对象
 * @param offset    对象中某field的偏移量
 * @param expected  期望值
 * @param update    更新值
 * @return          true | false
 */
//        public final native boolean compareAndSwapObject(Object o, long offset,  Object expected, Object update);
//
//        public final native boolean compareAndSwapInt(Object o, long offset, int expected,int update);
//
//        public final native boolean compareAndSwapLong(Object o, long offset, long expected, long update);

//        System.out.println("done");
    }
}
