package com.shawn.clazzloader;

import sun.misc.Launcher;

/**
 * Hello world!
 */
public class App {
    //sun.boot.class.path、java.ext.dirs和java.class.path
    public static void main(String[] args) {
//        System.out.println("BootstrapClassLoader:");
//        printProperty("sun.boot.class.path");
//        System.out.println("ExtClassLoader:");
//        printProperty("java.ext.dirs");
//        System.out.println("AppClassLoader:");
//        printProperty("java.class.path");
//        test1();
//        test2();
//        new App().loadClassDemo1();
        new App().testLoadFromCache();
    }

    void testLoadFromCache(){
        try {
            ClassLoader cl = getClass().getClassLoader();
            System.out.println(App.class.getCanonicalName());
            Class clazz = cl.loadClass(App.class.getCanonicalName());
            System.out.println(clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    void loadClassDemo1() {
        try {
            String s = "a";
            ClassLoader cl = getClass().getClassLoader();
            System.out.println(cl);
            Class clazz = cl.loadClass("java.lang.String");
            System.out.println(clazz);
            Class clazz2 = getClass().getClassLoader().getParent().loadClass("java.lang.String");
            System.out.println(clazz.getClassLoader());
            System.out.println(clazz2.getClassLoader());
            System.out.println(clazz2 == clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    static void test1() {
        ClassLoader cl = App.class.getClassLoader();
        System.out.println("ClassLoader is:" + cl.toString());
        cl = int.class.getClassLoader();
        System.out.println("ClassLoader is:" + cl.toString());
    }

    static void test2() {
        ClassLoader cl = App.class.getClassLoader();
        System.out.println("ClassLoader current is:" + cl.toString());
        System.out.println("ClassLoader parent is:" + cl.getParent().toString());
        System.out.println("ClassLoader parent parent is:" + cl.getParent().getParent().toString()); //NPE
    }

    static void printProperty(String propertyName) {
        String path = System.getProperty(propertyName);
        System.out.println(path);
    }
}
