package com.shawn.demo.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.implementation.FixedValue;

import java.util.Arrays;

import static net.bytebuddy.matcher.ElementMatchers.*;

/**
 * @author luxufeng
 * @date 2020-12-2
 **/
public class MethodInteceptorDemo {

    public static void main(String[] args) throws Exception {
//        ByteBuddyAgent.install();

        Foo dynamicFoo = new ByteBuddy()
                .subclass(Foo.class)
                // 匹配由Foo.class声明的方法
                .method(isDeclaredBy(Foo.class)).intercept(FixedValue.value("One!"))
                // 匹配名为foo的方法
//                .method(named("foo")).intercept(FixedValue.value("Two!"))
                // 匹配名为foo，入参数量为1的方法
//                .method(named("foo").and(takesArguments(1))).intercept(FixedValue.value("Three!"))
                .make()
                .load(MethodInteceptorDemo.class.getClassLoader())
                .getLoaded()
                .newInstance();
        String s = dynamicFoo.bar();
        System.out.println(s);
        Arrays.stream(dynamicFoo.getClass().getMethods()).forEach(m -> {
            System.out.println(m.getName());
        });
    }
}
