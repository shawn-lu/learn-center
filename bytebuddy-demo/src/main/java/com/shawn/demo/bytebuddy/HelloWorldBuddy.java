package com.shawn.demo.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * Hello world!
 */
public class HelloWorldBuddy {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        Class<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
//                .name("example.Type")
                .method(ElementMatchers.named("toString"))
                .intercept(FixedValue.value("Hello World"))
                .make()
                .load(HelloWorldBuddy.class.getClassLoader())
                .getLoaded();

        Object instance = dynamicType.newInstance();
        String toString = instance.toString();
        System.out.println(toString);
        System.out.println(instance.getClass().getCanonicalName());
        System.out.println(instance.getClass().getPackage());
//
//        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
//                .subclass(Object.class)
//                .name("example.Type")
//                .make();
//        System.out.println(dynamicType);
    }
}
