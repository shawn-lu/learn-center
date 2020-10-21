package com.shawn.mybatis.learn.dynamicproxy;

/**
 * @author luxufeng
 * @date 2020/10/19
 **/
public class LogEnhance {

    public void logBefore() {
        System.out.println("log before enhance xxxx");
    }

    public void logAfter() {
        System.out.println("log after enhance xxxx");
    }
}
