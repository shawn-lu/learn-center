package com.shawn.mybatis.learn.dynamicproxy;

/**
 * @author luxufeng
 * @date 2020/10/19
 **/
public class SourceObjectImpl implements SourceObject {
    @Override
    public void doMethod1() {
        System.out.println("SourceObjectImpl do method1");

    }

    @Override
    public String doMethod2(String param) {
        System.out.println(param);
        return "method2:" + param;
    }
}
