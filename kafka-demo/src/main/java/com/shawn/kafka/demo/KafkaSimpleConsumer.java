package com.shawn.kafka.demo;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

@Slf4j
public class KafkaSimpleConsumer {
    static String address = "10.251.69.145:9092,10.251.69.106:9092,10.251.69.108:9092";
    static String topic = "datacenter-canal-fsc";
//    static String address = "10.251.69.145:9092,10.251.69.106:9092,10.251.69.108:9092";
//    static String topic = "datacenter-canal-channelcenter-sharding";




    public static void main(String[] args) {
        Properties props = new Properties();
        //kafka服务器地址
        props.put("bootstrap.servers", address);
//        props.put("bootstrap.servers", "10.251.92.10:9092,10.251.92.17:9092,10.251.92.49:9092");


        //必须指定消费者组
        props.put("group.id", "local-test-2022");
//        props.put("auto.offset.reset", "earliest");
        //设置数据key和value的序列化处理类
        props.put("key.deserializer", StringDeserializer.class);
        props.put("value.deserializer", StringDeserializer.class);
        //创建消息者实例
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        //订阅topic1的消息
//        consumer.subscribe(Arrays.asList("yh_ddz_pd_es", "yh_ddz_sa_es", "yh_ddz_rp_es"));

//        String topic = "yh_ddz_pd_es";
        consumer.subscribe(Arrays.asList(topic));


        log.info("start consumer");
        //到服务器中读取记录
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                log.info("kafka time {},message {}", new Date(record.timestamp()), record.value());
            }
        }

    }

}
