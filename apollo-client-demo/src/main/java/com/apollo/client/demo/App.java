package com.apollo.client.demo;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;

public class App {
    public static void main(String[] args) throws Exception {
        System.setProperty("app.id", "apollo-client-local-demo");
        System.setProperty("apollo.meta", "http://10.228.224.122:8080");
        System.setProperty("apollo.cache-dir", "/Users/luxufeng/work/githubspace/learn-center/apollo-client-demo/cache");
        //config instance is singleton for each namespace and is never null
        Config config = ConfigService.getAppConfig();
        String someKey = "key-a";
//        String someDefaultValue = "someDefaultValueForTheKey";
        String value = config.getProperty(someKey, "not found");
        while (true) {
            value = config.getProperty(someKey, "not found");
            System.out.println(value);
            Thread.sleep(5000);
        }
//        System.out.println(value);
    }
}
