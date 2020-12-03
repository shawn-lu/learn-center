package com.shawn.demo.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import static net.bytebuddy.matcher.ElementMatchers.isDeclaredBy;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;

/**
 * @author luxufeng
 * @date 2020-12-2
 **/
public class BuddyStarter {
    public static void premain(String arg, Instrumentation inst) {
        inst.addTransformer((loader, className, classBeingRedefined, protectionDomain, classfileBuffer) -> {
//            System.out.println(className);
            if (className.contains("Foo")) {
                System.out.println(className + " redefined");
                DynamicType.Unloaded unloaded = null;
                try {
                    System.out.println(classBeingRedefined);
                    unloaded = new ByteBuddy().redefine(Foo.class)
//                        .subclass(Foo.class)
                            // 匹配由Foo.class声明的方法
                            .method(isDeclaredBy(classBeingRedefined)).intercept(FixedValue.value("One!"))
                            // 匹配名为foo的方法
//                        .method(named("foo")).intercept(FixedValue.value("Two!"))
                            // 匹配名为foo，入参数量为1的方法
//                        .method(named("foo").and(takesArguments(1))).intercept(FixedValue.value("Three!"))
                            .make();
//                            .load(MethodInteceptorDemo.class.getClassLoader())
//                            .getLoaded()
//                            .newInstance();
                    System.out.println("save file start");
                    unloaded.saveIn(new File("E:\\myspace\\learn-center\\bytebuddy-demo"));
                    System.out.println("save file end");
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                return unloaded.getBytes();

            }
            return classfileBuffer;
        });
    }
}
