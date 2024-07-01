package com.shawn.kafka.demo;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class Consumer5 {

    public static void main(String[] args) throws Exception {
//        if(true){
//            return;
//        }
        //配置信息
        Properties props = new Properties();
        //kafka服务器地址
        props.put("bootstrap.servers", "10.251.69.145:9092,10.251.69.106:9092,10.251.69.108:9092");

//        props.put("bootstrap.servers", "10.251.69.145:9092,10.251.69.106:9092,10.251.69.108:9092");
        //必须指定消费者组
//        props.put("bootstrap.servers", "10.251.70.17:9092,10.251.70.97:9092,10.251.70.63:9092");
        props.put("group.id", "local-test-cool-2");
//        props.put("auto.offset.reset", "earliest");
        //设置数据key和value的序列化处理类
        props.put("key.deserializer", StringDeserializer.class);
        props.put("value.deserializer", StringDeserializer.class);
        //创建消息者实例
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        //订阅topic1的消息datacenter-canal-channelcenter-sharding
        consumer.subscribe(Arrays.asList("test_Canal-Product-Shop"));
        System.out.println("start consumer");
        //到服务器中读取记录
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
//            Thread.sleep(5000);
//            System.out.println("count " + records.count() + "---------------");
            for (ConsumerRecord<String, String> record : records) {
//                System.out.println(new Date(record.timestamp()));
                System.out.println(record.value());
//                if (record.timestamp() >= 1631257200000L) {
//                    logger.info(record.value());
//                if (record.value().contains("44b48755-bf9f-496f-a7de-84af87f33841")
//                        || record.value().contains("cd3b2c4a-ac40-4652-aa8c-7a206bae3995")) {
//                    logger.info("!!!!!!!!found {}", record.value());
//                }
////                    System.out.println("key:" + record.key() + "" + ",value:" + record.value());
//                }
//                }
            }
        }
    }
}