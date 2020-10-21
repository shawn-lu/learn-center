package com.shawn;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Pipeline;

import java.util.Collection;
import java.util.List;

/**
 * @author luxufeng
 * @date 2020/10/13
 **/
public class JedisUsePipline {

    static void standone(){
        Jedis jedis = new Jedis("10.19.187.125", 6379);
        Pipeline pipeline = jedis.pipelined();
        pipeline.set("xiaoming", "11");
        Jedis jedis2 = new Jedis("10.19.187.125", 6379);
        System.out.println(jedis2.get("xiaoming"));
        pipeline.set("xiaozhang", "22");
        pipeline.set("totalstudent", "33");
        List<Object> students = pipeline.syncAndReturnAll();
        System.out.println(jedis2.get("xiaoming"));
        p(students);
    }
//    static void cluster(){
//
//        JedisCluster jedisCluster = new JedisCluster("10.0.0.2", 7001);
//        Pipeline pipeline = jedisCluster();
//        pipeline.set("xiaoming", "11");
//        Jedis jedis2 = new Jedis("10.19.187.125", 6379);
//        System.out.println(jedis2.get("xiaoming"));
//        pipeline.set("xiaozhang", "22");
//        pipeline.set("totalstudent", "33");
//        List<Object> students = pipeline.syncAndReturnAll();
//        System.out.println(jedis2.get("xiaoming"));
//        p(students);
//    }


    public static void main(String[] args) {
//        standone();
    }

    static void p(Collection c) {
        c.stream().forEach(cc -> {
            System.out.println(cc);
        });
    }
}
