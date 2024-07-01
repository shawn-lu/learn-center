package com.shawn.kafka.demo;

import com.alibaba.fastjson.JSON;
import com.beust.jcommander.internal.Lists;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.*;
import java.util.concurrent.Future;

/**
 * Hello world!
 */
public class App {

    static void createTopic(){
        AdminClient adminClient = null;
        try{
            String servers = "10.251.70.224:9092";

            Properties properties = new Properties();
            properties.put("bootstrap.servers", servers);
            adminClient = AdminClient.create(properties);

            List<String> topics = Lists.newArrayList("yh_ddz_sa_es");


            //创建topic
            List<NewTopic> newTopics = new ArrayList<>();
            for (String  topic : topics) {

                newTopics.add(new NewTopic(topic,3,(short)1));

            }
            CreateTopicsResult createTopicsResult = adminClient.createTopics(newTopics);
            Set<String> errorTopic = new HashSet<>();
            for (Map.Entry<String, KafkaFuture<Void>> item : createTopicsResult.values().entrySet()) {
                try{
                    KafkaFuture<Void> future = item.getValue();
                    future.get();
                    if (!future.isDone()) {
                        errorTopic.add(item.getKey());
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (adminClient != null) {
                adminClient.close();
            }
        }
    }

    public static void main(String[] args) throws Exception {
//        createTopic();
        System.out.println("Hello World!");

        String servers = "kfk-srv-3.pro.yonghui.cn:9092,kfk-srv-2.pro.yonghui.cn:9092,kfk-srv-1.pro.yonghui.cn:9092";



        Properties props = new Properties();
//        kafkaProperties.put("max.in.flight.requests.per.connection", 1);
//        kafkaProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, addr);

        props.put("bootstrap.servers", servers);
        props.put("acks", "1");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        String topicName = "yh_ddz_sa_es";
        String msg = msg();
        Producer producer = new KafkaProducer<>(props);
        ProducerRecord record = new ProducerRecord<>(topicName,
                0,
                null,
                msg);
        Future<RecordMetadata> future = producer.send(record);
        RecordMetadata metadata = future.get();

        System.out.println(JSON.toJSON(metadata));
    }

    static String msg() {
        return "{\n" +
                "\t\"data\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"id\": \"s2023120322001438751403850987-d650223-sc9650-a20231203182532\",\n" +
                "\t\t\t\"pay_type\": \"ZZ88\",\n" +
                "\t\t\t\"merchant_no\": null,\n" +
                "\t\t\t\"merchant_name\": null,\n" +
                "\t\t\t\"third_shop_code\": null,\n" +
                "\t\t\t\"is_shop_trans\": \"1\",\n" +
                "\t\t\t\"yh_shop_code\": \"9650\",\n" +
                "\t\t\t\"yh_shop_name\": null,\n" +
                "\t\t\t\"trade_date\": \"2023-12-03\",\n" +
                "\t\t\t\"terminal_no\": \"9650\",\n" +
                "\t\t\t\"card_no\": null,\n" +
                "\t\t\t\"trade_amount\": \"800.0\",\n" +
                "\t\t\t\"clear_amount\": \"798.24\",\n" +
                "\t\t\t\"poundage\": \"1.76\",\n" +
                "\t\t\t\"poundage_Rate\": null,\n" +
                "\t\t\t\"d_poundage\": null,\n" +
                "\t\t\t\"d_trade_amount\": null,\n" +
                "\t\t\t\"d_clear_amount\": null,\n" +
                "\t\t\t\"third_discount\": null,\n" +
                "\t\t\t\"refund_third_discount\": null,\n" +
                "\t\t\t\"actual_amount\": null,\n" +
                "\t\t\t\"refund_amount\": \"0.0\",\n" +
                "\t\t\t\"matching_status\": \"0\",\n" +
                "\t\t\t\"reference_no\": null,\n" +
                "\t\t\t\"serial_no\": null,\n" +
                "\t\t\t\"trade_type\": \"01\",\n" +
                "\t\t\t\"card\": null,\n" +
                "\t\t\t\"merchant_pay_no\": \"202312031916032316474757121\",\n" +
                "\t\t\t\"third_pay_no\": \"2023120322001438751403850987\",\n" +
                "\t\t\t\"merchant_refund_no\": \"\",\n" +
                "\t\t\t\"third_refund_no\": null,\n" +
                "\t\t\t\"currency_code\": null,\n" +
                "\t\t\t\"rrn\": \"202312031916032316474757121\",\n" +
                "\t\t\t\"sdate\": \"2023-12\",\n" +
                "\t\t\t\"trade_time\": \"18:25:32\",\n" +
                "\t\t\t\"create_time\": \"2023-12-04 16:00:12\",\n" +
                "\t\t\t\"update_time\": \"0000-00-00 00:00:00\",\n" +
                "\t\t\t\"file_name\": null,\n" +
                "\t\t\t\"summary_state\": \"0\",\n" +
                "\t\t\t\"state\": \"1\",\n" +
                "\t\t\t\"data_type\": \"1\",\n" +
                "\t\t\t\"is_online\": null,\n" +
                "\t\t\t\"random\": \"62\",\n" +
                "\t\t\t\"channel_id\": \"102176\",\n" +
                "\t\t\t\"repayment_type\": \"01\",\n" +
                "\t\t\t\"repayment_type_n\": null,\n" +
                "\t\t\t\"company_code\": \"5000\",\n" +
                "\t\t\t\"source_system\": \"1\",\n" +
                "\t\t\t\"merchant_assumes_discount\": null,\n" +
                "\t\t\t\"third_assumes_preference\": null,\n" +
                "\t\t\t\"daily_shop_code\": \"1000\",\n" +
                "\t\t\t\"is_daily_review\": null\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"database\": \"yh_ddz\",\n" +
                "\t\"es\": 1701676811000,\n" +
                "\t\"id\": 6353648,\n" +
                "\t\"isDdl\": false,\n" +
                "\t\"mysqlType\": {\n" +
                "\t\t\"id\": \"varchar(200)\",\n" +
                "\t\t\"pay_type\": \"varchar(50)\",\n" +
                "\t\t\"merchant_no\": \"varchar(50)\",\n" +
                "\t\t\"merchant_name\": \"varchar(50)\",\n" +
                "\t\t\"third_shop_code\": \"varchar(50)\",\n" +
                "\t\t\"is_shop_trans\": \"varchar(50)\",\n" +
                "\t\t\"yh_shop_code\": \"varchar(50)\",\n" +
                "\t\t\"yh_shop_name\": \"varchar(50)\",\n" +
                "\t\t\"trade_date\": \"varchar(50)\",\n" +
                "\t\t\"terminal_no\": \"varchar(50)\",\n" +
                "\t\t\"card_no\": \"varchar(50)\",\n" +
                "\t\t\"trade_amount\": \"decimal(50,2)\",\n" +
                "\t\t\"clear_amount\": \"decimal(50,2)\",\n" +
                "\t\t\"poundage\": \"decimal(50,2)\",\n" +
                "\t\t\"poundage_Rate\": \"decimal(50,2)\",\n" +
                "\t\t\"d_poundage\": \"decimal(50,2)\",\n" +
                "\t\t\"d_trade_amount\": \"decimal(50,2)\",\n" +
                "\t\t\"d_clear_amount\": \"decimal(50,2)\",\n" +
                "\t\t\"third_discount\": \"decimal(50,2)\",\n" +
                "\t\t\"refund_third_discount\": \"decimal(50,2)\",\n" +
                "\t\t\"actual_amount\": \"decimal(50,2)\",\n" +
                "\t\t\"refund_amount\": \"decimal(50,2)\",\n" +
                "\t\t\"matching_status\": \"varchar(50)\",\n" +
                "\t\t\"reference_no\": \"varchar(50)\",\n" +
                "\t\t\"serial_no\": \"varchar(50)\",\n" +
                "\t\t\"trade_type\": \"varchar(50)\",\n" +
                "\t\t\"card\": \"varchar(50)\",\n" +
                "\t\t\"merchant_pay_no\": \"varchar(50)\",\n" +
                "\t\t\"third_pay_no\": \"varchar(50)\",\n" +
                "\t\t\"merchant_refund_no\": \"varchar(50)\",\n" +
                "\t\t\"third_refund_no\": \"varchar(50)\",\n" +
                "\t\t\"currency_code\": \"varchar(50)\",\n" +
                "\t\t\"rrn\": \"varchar(50)\",\n" +
                "\t\t\"sdate\": \"varchar(50)\",\n" +
                "\t\t\"trade_time\": \"varchar(50)\",\n" +
                "\t\t\"create_time\": \"timestamp\",\n" +
                "\t\t\"update_time\": \"timestamp\",\n" +
                "\t\t\"file_name\": \"varchar(255)\",\n" +
                "\t\t\"summary_state\": \"varchar(10)\",\n" +
                "\t\t\"state\": \"varchar(10)\",\n" +
                "\t\t\"data_type\": \"varchar(10)\",\n" +
                "\t\t\"is_online\": \"varchar(10)\",\n" +
                "\t\t\"random\": \"varchar(10)\",\n" +
                "\t\t\"channel_id\": \"varchar(50)\",\n" +
                "\t\t\"repayment_type\": \"varchar(50)\",\n" +
                "\t\t\"repayment_type_n\": \"varchar(50)\",\n" +
                "\t\t\"company_code\": \"varchar(50)\",\n" +
                "\t\t\"source_system\": \"varchar(5)\",\n" +
                "\t\t\"merchant_assumes_discount\": \"decimal(15,2)\",\n" +
                "\t\t\"third_assumes_preference\": \"decimal(15,2)\",\n" +
                "\t\t\"daily_shop_code\": \"varchar(8)\",\n" +
                "\t\t\"is_daily_review\": \"varchar(2)\"\n" +
                "\t},\n" +
                "\t\"old\": null,\n" +
                "\t\"pkNames\": [\n" +
                "\t\t\"id\",\n" +
                "\t\t\"random\"\n" +
                "\t],\n" +
                "\t\"sql\": \"\",\n" +
                "\t\"sqlType\": {\n" +
                "\t\t\"id\": 12,\n" +
                "\t\t\"pay_type\": 12,\n" +
                "\t\t\"merchant_no\": 12,\n" +
                "\t\t\"merchant_name\": 12,\n" +
                "\t\t\"third_shop_code\": 12,\n" +
                "\t\t\"is_shop_trans\": 12,\n" +
                "\t\t\"yh_shop_code\": 12,\n" +
                "\t\t\"yh_shop_name\": 12,\n" +
                "\t\t\"trade_date\": 12,\n" +
                "\t\t\"terminal_no\": 12,\n" +
                "\t\t\"card_no\": 12,\n" +
                "\t\t\"trade_amount\": 3,\n" +
                "\t\t\"clear_amount\": 3,\n" +
                "\t\t\"poundage\": 3,\n" +
                "\t\t\"poundage_Rate\": 3,\n" +
                "\t\t\"d_poundage\": 3,\n" +
                "\t\t\"d_trade_amount\": 3,\n" +
                "\t\t\"d_clear_amount\": 3,\n" +
                "\t\t\"third_discount\": 3,\n" +
                "\t\t\"refund_third_discount\": 3,\n" +
                "\t\t\"actual_amount\": 3,\n" +
                "\t\t\"refund_amount\": 3,\n" +
                "\t\t\"matching_status\": 12,\n" +
                "\t\t\"reference_no\": 12,\n" +
                "\t\t\"serial_no\": 12,\n" +
                "\t\t\"trade_type\": 12,\n" +
                "\t\t\"card\": 12,\n" +
                "\t\t\"merchant_pay_no\": 12,\n" +
                "\t\t\"third_pay_no\": 12,\n" +
                "\t\t\"merchant_refund_no\": 12,\n" +
                "\t\t\"third_refund_no\": 12,\n" +
                "\t\t\"currency_code\": 12,\n" +
                "\t\t\"rrn\": 12,\n" +
                "\t\t\"sdate\": 12,\n" +
                "\t\t\"trade_time\": 12,\n" +
                "\t\t\"create_time\": 93,\n" +
                "\t\t\"update_time\": 93,\n" +
                "\t\t\"file_name\": 12,\n" +
                "\t\t\"summary_state\": 12,\n" +
                "\t\t\"state\": 12,\n" +
                "\t\t\"data_type\": 12,\n" +
                "\t\t\"is_online\": 12,\n" +
                "\t\t\"random\": 12,\n" +
                "\t\t\"channel_id\": 12,\n" +
                "\t\t\"repayment_type\": 12,\n" +
                "\t\t\"repayment_type_n\": 12,\n" +
                "\t\t\"company_code\": 12,\n" +
                "\t\t\"source_system\": 12,\n" +
                "\t\t\"merchant_assumes_discount\": 3,\n" +
                "\t\t\"third_assumes_preference\": 3,\n" +
                "\t\t\"daily_shop_code\": 12,\n" +
                "\t\t\"is_daily_review\": 12\n" +
                "\t},\n" +
                "\t\"table\": \"sa_third_pay_detail\",\n" +
                "\t\"ts\": 1701676811681,\n" +
                "\t\"type\": \"INSERT\"\n" +
                "}";
    }
}
