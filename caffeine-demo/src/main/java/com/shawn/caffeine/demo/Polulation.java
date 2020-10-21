package com.shawn.caffeine.demo;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author luxufeng
 * @date 2020/10/19
 **/
public class Polulation {
    static void manual() {
        String key = "manual_key";
        Cache<String, Graph> cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(10_000)
                .build();

        // Lookup an entry, or null if not found
        Graph graph = cache.getIfPresent(key);
        // Lookup and compute an entry if absent, or null if not computable
        graph = cache.get(key, k -> createExpensiveGraph(key));
        System.out.println(graph);
        // Insert or update an entry
        cache.put(key, graph);
        // Remove an entry
        cache.invalidate(key);
    }

    private static Graph createExpensiveGraph(String key) {
        Graph graph = new Graph("name:" + key);
        System.out.println("do createExpensiveGraph");
//        return graph;
        return graph;
    }

    static void loading() {
        LoadingCache<String, Graph> cache = Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(key -> createExpensiveGraph("loadCache"));

        String key = "loading_key";
        // Lookup and compute an entry if absent, or null if not computable
        //手动指定创建方法就用指定的，忽略loadingCache定义的
        Graph graph = cache.get(key, k -> createExpensiveGraph(k));
        System.out.println(graph);

        // Lookup and compute entries that are absent
//        Map<String, Graph> graphs = cache.getAll(Arrays.asList("a", "loading_key"));
    }

    static void asyncManualLoading() {
        String key = "async_manual_key";
        AsyncCache<String, Graph> cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(10_000)
                .buildAsync();

        // 查找缓存元素，如果不存在，则异步生成
        CompletableFuture<Graph> future = cache.get(key, k -> {
            Graph graph2 = createExpensiveGraph(key);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return graph2;
        });
        while (true) {
            try {
                Thread.sleep(1000);
                System.out.println(future);
                if (future.isDone()) {
                    System.out.println(future.get());
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    static void asyncLoading() {
        AsyncLoadingCache<String, Graph> cache = Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                // 你可以选择: 去异步的封装一段同步操作来生成缓存元素
                .buildAsync(key -> createExpensiveGraph(key));
        // 你也可以选择: 构建一个异步缓存元素操作并返回一个future
//            .buildAsync((key, executor) -> createExpensiveGraphAsync(key, executor));

        String key = "async_loading_key";
        // 查找缓存元素，如果其不存在，将会异步进行生成
        CompletableFuture<Graph> graph = cache.get(key);
        // 批量查找缓存元素，如果其不存在，将会异步进行生成
        //        CompletableFuture<Map<String, Graph>> graphs = cache.getAll(keys);
    }


    public static void main(String[] args) {
//        asyncManualLoading();
//        asyncLoading();
        testRefreshAndExpireAfterWrite();
        System.out.println("done");
    }



    static void testRefreshAndExpireAfterWrite(){
        LoadingCache<String, Graph> cache = Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterWrite(1,TimeUnit.MILLISECONDS)
                .refreshAfterWrite(1, TimeUnit.MINUTES)
                .build(key -> createExpensiveGraph(key));
        String key = "key1";
        System.out.println(cache.get(key));
        System.out.println(cache.get(key));
        cache.refresh(key);
        System.out.println(cache.get(key));
        System.out.println(cache.get(key));
        System.out.println(cache.get(key));
    }

    public static class Graph {
        Graph(String name) {
            this.name = name;
        }

        private String name;

        public String getName() {
            return name;
        }

        public Graph setName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public String toString() {
            return "[Graph]" + getName();
        }
    }
}

