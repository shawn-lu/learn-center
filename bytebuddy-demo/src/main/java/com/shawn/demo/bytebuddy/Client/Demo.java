package com.shawn.demo.bytebuddy.Client;

import com.shawn.demo.bytebuddy.Foo;

import java.util.Arrays;

/**
 * @author luxufeng
 * @date 2020-12-2
 **/
public class Demo {
    static {
        System.out.println("abc");
    }

    public static void main(String[] args) {
        System.out.println("demo finish");
        String result = new Foo().bar();
        System.out.println(result);
        Arrays.stream(Foo.class.getMethods()).forEach(m -> {
            System.out.println(m.getName());
        });
    }
}
