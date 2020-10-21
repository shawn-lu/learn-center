package com.shawn.mybatis.learn.dynamicproxy;

import javax.xml.transform.Source;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author luxufeng
 * @date 2020/10/19
 **/
public class Client {

    public static void main(String[] args) {
        SourceObject sourceObject = new SourceObjectImpl();
        SourceObject proxyObject = new ProxyObject(sourceObject);
        proxyObject.doMethod1();
        System.out.println("-----------------------");
        SourceObject dynamicProxy = (SourceObject) Proxy.newProxyInstance(sourceObject.getClass().getClassLoader(), new Class[]{SourceObject.class}, new InvocationHandler() {
            private SourceObject sourceObject = new SourceObjectImpl();
            private LogEnhance logEnhance = new LogEnhance();
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) {
//                System.out.println(proxy);
//                return method.invoke(proxy, args);
                logEnhance.logBefore();
                if ("doMethod1".equals(method.getName())) {
                    sourceObject.doMethod1();

                } else if ("doMethod2".equals(method.getName())) {
                    sourceObject.doMethod2((String) args[0]);
                } else {
                    throw new IllegalArgumentException("不支持的方法");
                }
                logEnhance.logAfter();
                System.out.println("current method:" + method.getName());
                return null;
            }
        });

        dynamicProxy.doMethod1();
        System.out.println("-------------");
        dynamicProxy.doMethod2("abc");

    }
}
