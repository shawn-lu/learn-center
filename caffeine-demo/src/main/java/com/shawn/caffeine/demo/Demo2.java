package com.shawn.caffeine.demo;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author luxufeng
 * @date 2020/10/21
 **/
public class Demo2 {
    public static void main(String[] args) {
        testCache();
//        testConcurrentHashMap();
    }

    static void testConcurrentHashMap() {
        Map<String, String> map = new ConcurrentHashMap<>(16);
        for (int i = 1; i <= 20; i++) {
            map.put("key" + i, "key" + i);
        }
        System.out.println(map);
    }

    static void testCache() {
        //simple cache
        Cache<String, String> simpleCache = Caffeine.newBuilder().maximumSize(10).build();
        for (int i = 1; i <= 16; i++) {
            simpleCache.put("key" + i, "key" + i);
        }
        String key = simpleCache.getIfPresent("key15");
        System.out.println(key);
        System.out.println(simpleCache.getClass().getName());
        System.out.println(simpleCache.estimatedSize());
        simpleCache.cleanUp();
        System.out.println(simpleCache.estimatedSize());

//        LoadingCache<String, Graph> cache = Caffeine.newBuilder()
//                .maximumSize(10_000)
//                .expireAfterWrite(1,TimeUnit.MILLISECONDS)
//                .refreshAfterWrite(1, TimeUnit.MINUTES)
//                .build(key -> createExpensiveGraph(key));
//        cache.get()
    }
}
