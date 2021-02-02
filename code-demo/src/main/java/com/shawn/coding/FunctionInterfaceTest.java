package com.shawn.coding;

import java.util.Comparator;
import java.util.function.*;

/**
 * Created by lxf on 2021/1/31.
 * 函数是接口
 */
public class FunctionInterfaceTest {

    public static void main(String[] args) {
        //内置函数式接口Runnable,Comparator<
        Runnable x = () -> System.out.println("gogogo");
        Comparator<Integer> c = (t1, t2) -> t1 > t2 ? 1 : -1;
        //自定义函数式接口
        GreetingService greetService1 = message -> System.out.println("Hello " + message);
        greetService1.sayMessage("abc");

        //java.util.function中函数式接口
        BiConsumer<Integer, Integer> f1 = (a, b) -> System.out.println(a + b); //入参a,b,return void
        f1.accept(1, 2);
        BiFunction<Integer, Integer, String> f2 = (a, b) -> String.valueOf(a + b); //入参a,b return String
        System.out.println(f2.apply(1, 2));
        BinaryOperator<Integer> f3 = (a, b) -> (a + b); //BiFunction的子接口,入参出参同类型
        System.out.println(f2.apply(1, 2));
        BiPredicate<String, Integer> f4 = (a, b) -> a.equals(String.valueOf(b)); //入参a,b return boolean
        System.out.println(f4.test("1",1));
        BooleanSupplier f5 = () -> "abc".equals("ddd");  //无入参，return boolean
        System.out.println(f5.getAsBoolean());
        Consumer<String> f6 = (a) -> System.out.println(a); //入参a,return void
        f6.accept("55");
    }


    @FunctionalInterface
    interface GreetingService {
        void sayMessage(String message);
    }
}
