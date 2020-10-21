package com.shawn;

import com.google.common.base.Stopwatch;
import org.joda.time.DateTime;
import redis.clients.jedis.Jedis;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author luxufeng
 * @date 2020/10/15
 * 测试redis键同时过期对性能的影响
 **/
public class TestRedisExpireAffect {

    public static void main(String[] args) throws Exception {
        Stopwatch stopwatch = Stopwatch.createUnstarted();
        DateTime now = DateTime.now();
        long expireAt = now.plusSeconds(60).getMillis();
        String host = "192.168.122.130";
        int port = 6379;

        Jedis jedis = new Jedis(host, port);
        jedis.flushDB();
        long size = jedis.dbSize();
        System.out.println(size);
        if (size == 0) {
            jedis.set("a", "123");
            for (int i = 1; i < 100_000; i++) {
                jedis.set("todelete" + i, "abc");
                jedis.pexpireAt("todelete" + i, expireAt);
                if (i == 100) {
                    System.out.println("set big");
                    jedis.set("big", new String(serialize(generateBig())));
                    jedis.pexpireAt("todelete" + i, expireAt);
                    System.out.println("set big done");
                }
            }
            System.out.println("set done");
            while (true) {
                Thread.sleep(10);
                testCost(jedis, expireAt, stopwatch);
                if (System.currentTimeMillis() > expireAt + 5000) {
                    break;
                }
            }
        }
    }

    static List<String> generateBig() {
        List<String> big = new ArrayList<>();
        for (int i = 0; i <= 10_000_000; i++) {
            big.add(i + "xx");
        }
        System.out.println("generate done");
        return big;
    }

    static void testCost(Jedis jedis, long expireAt, Stopwatch stopwatch) {
        Long startTime = System.nanoTime();
        long before = jedis.dbSize();
        jedis.get("a");
        long after = jedis.dbSize();
        long cost = System.nanoTime() - startTime;
//        long cost = stopwatch.elapsed(TimeUnit.MICROSECONDS);
        if (cost > 10_000_000) {
            System.out.println("span:" + (System.currentTimeMillis() - expireAt) + ",cost:" + cost + ",before:" + before + ",after:" + after);
        }
    }

    public static byte[] serialize(Object obj) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            byte[] bs = baos.toByteArray();
            baos.close();
            oos.close();

            return bs;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
