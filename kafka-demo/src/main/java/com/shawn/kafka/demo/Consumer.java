package com.shawn.kafka.demo;

import ch.qos.logback.classic.util.StatusViaSLF4JLoggerFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

public class Consumer {
    final static Logger logger = LoggerFactory.getLogger("com.shawn.kafka.demo.Consumer");

    public static void main(String[] args) throws Exception {
//        if(true){
//            return;
//        }
        //配置信息
        Properties props = new Properties();
        //kafka服务器地址
        props.put("bootstrap.servers", "10.251.69.145:9092,10.251.69.106:9092,10.251.69.108:9092");
        //必须指定消费者组
        props.put("group.id", "local-test");
//        props.put("auto.offset.reset", "earliest");
        //设置数据key和value的序列化处理类
        props.put("key.deserializer", StringDeserializer.class);
        props.put("value.deserializer", StringDeserializer.class);
        //创建消息者实例
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        //订阅topic1的消息
        consumer.subscribe(Arrays.asList("tenant_base_region_product_sap"));
        logger.info("start consumer");
        //到服务器中读取记录
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
//            Thread.sleep(5000);
//            System.out.println("count " + records.count() + "---------------");
            for (ConsumerRecord<String, String> record : records) {
//                System.out.println(new Date(record.timestamp()));
                logger.info(record.value());
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
