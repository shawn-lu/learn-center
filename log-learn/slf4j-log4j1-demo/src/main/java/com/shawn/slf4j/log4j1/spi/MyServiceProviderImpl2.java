package com.shawn.slf4j.log4j1.spi;

/**
 * @author luxufeng
 * @date 2020/11/4
 **/
public class MyServiceProviderImpl2 implements IMyServiceProvider {
    @Override
    public String getName() {
        return "name:ProviderImpl2";
    }
}
