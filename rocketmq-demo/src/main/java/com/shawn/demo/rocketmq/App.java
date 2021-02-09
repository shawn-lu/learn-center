package com.shawn.demo.rocketmq;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.google.common.collect.Sets;
import com.yonghui.framework.job.ShardingHelper;
import org.springframework.cglib.core.ReflectUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        doShard("9M4Z");
        doShard("9096");
        doShard("9H05");
        doShard("12371");
        doShard("CQ0010");
        doShard("FZ0041");
        ShardingContext shardingContext = new ShardingContext(new ShardingContexts(null, null, 10, null, new HashMap<>(), 0), 4);
        for (String s : shopSets()) {
            boolean flag = ShardingHelper.checkShardingExecutionByShop(shardingContext, s);
            System.out.println(flag);
        }
    }

    static void doShard(String shopId) {
        int i = 10;
        int index = Math.abs(shopId.hashCode() % i);
        System.out.println(shopId.hashCode());
        System.out.println(index);
    }

    private static Set<String> shopSets(){
        return Sets.newHashSet("9M4Z","9096","9H05","12371","CQ0010","FZ0041");
    }
}
