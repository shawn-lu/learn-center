package com.shawn.slf4j.log4j1.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author luxufeng
 * @date 2020/11/4
 **/
public class TestClass {
    public static void main(String[] argus) {
        ServiceLoader<IMyServiceProvider> serviceLoader = ServiceLoader.load(IMyServiceProvider.class);

        Iterator iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            IMyServiceProvider item = (IMyServiceProvider) iterator.next();
            System.out.println(item.getName() + ": " + item.hashCode());
        }
    }
}
