package com.shawn.mybatis.learn.dynamicproxy;

/**
 * @author luxufeng
 * @date 2020/10/19
 **/
public class ProxyObject implements SourceObject{
    private SourceObject sourceObject;
    private LogEnhance logEnhance = new LogEnhance();

    public ProxyObject(SourceObject sourceObject) {
        this.sourceObject = sourceObject;
    }

    @Override
    public void doMethod1() {
        logEnhance.logBefore();
        sourceObject.doMethod1();
        logEnhance.logAfter();
    }

    @Override
    public String doMethod2(String param) {
        logEnhance.logBefore();
        String result = sourceObject.doMethod2(param);
        logEnhance.logAfter();
        return result;

    }
}
