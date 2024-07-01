package com.shawn.kafka.demo;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndTimestamp;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class Consumer2 {
    final static Logger logger = LoggerFactory.getLogger("com.shawn.kafka.demo.Consumer");
    static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static void main(String[] args) throws Exception {
//        if(true){
//            return;
//        }
        //配置信息
        Properties props = new Properties();
        //kafka服务器地址
        props.put("bootstrap.servers", "10.251.70.17:9092,10.251.70.97:9092,10.251.70.63:9092");
//        props.put("bootstrap.servers", "10.251.92.10:9092,10.251.92.17:9092,10.251.92.49:9092");


        //必须指定消费者组
        props.put("group.id", "local-test");
//        props.put("auto.offset.reset", "earliest");
        //设置数据key和value的序列化处理类
        props.put("key.deserializer", StringDeserializer.class);
        props.put("value.deserializer", StringDeserializer.class);
        //创建消息者实例
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        //订阅topic1的消息
//        consumer.subscribe(Arrays.asList("yh_ddz_pd_es", "yh_ddz_sa_es", "yh_ddz_rp_es"));
        String topic = "datacenter-canal-productcenter";
//        String topic = "yh_ddz_pd_es";
//        String topic = "yhddztdsqlTopic";
//        consumer.subscribe(Arrays.asList("yh_ddz_pd_es"));

        List<PartitionInfo> partitionInfos = consumer.partitionsFor(topic);

        Date now = new Date();
        long nowTime = now.getTime();
//        long fetchDataTime = nowTime - 1000 * 60 * 60 * 48;  // 计算30分钟之前的时间戳
        long fetchDataTime = nowTime - 1000 * 60 *30;
        List<TopicPartition> topicPartitions = new ArrayList<>();
        Map<TopicPartition, Long> timestampsToSearch = new HashMap<>();

        for (PartitionInfo partitionInfo : partitionInfos) {
            topicPartitions.add(new TopicPartition(partitionInfo.topic(), partitionInfo.partition()));
            timestampsToSearch.put(new TopicPartition(partitionInfo.topic(), partitionInfo.partition()), fetchDataTime);
        }
        // 获取每个partition一个小时之前的偏移量
        Map<TopicPartition, OffsetAndTimestamp> map = consumer.offsetsForTimes(timestampsToSearch);

        OffsetAndTimestamp offsetTimestamp = null;
        System.out.println("开始设置各分区初始偏移量...");
        for (Map.Entry<TopicPartition, OffsetAndTimestamp> entry : map.entrySet()) {
            // 如果设置的查询偏移量的时间点大于最大的索引记录时间，那么value就为空
            offsetTimestamp = entry.getValue();
            if (offsetTimestamp != null) {
                int partition = entry.getKey().partition();
                long timestamp = offsetTimestamp.timestamp();
                long offset = offsetTimestamp.offset();
                System.out.println("partition = " + partition +
                        ", time = " + df.format(new Date(timestamp)) +
                        ", offset = " + offset);
                // 设置读取消息的偏移量
                consumer.assign(Arrays.asList(entry.getKey()));
                consumer.seek(entry.getKey(), offset);
            }
        }
        System.out.println("设置各分区初始偏移量结束...");

        logger.info("start consumer");
        //到服务器中读取记录
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
//            Thread.sleep(5000);
//            System.out.println("count " + records.count() + "---------------");
            for (ConsumerRecord<String, String> record : records) {
//                System.out.println(new Date(record.timestamp()));
                logger.info("kafka time {},message {}", new Date(record.timestamp()), record.value());
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
