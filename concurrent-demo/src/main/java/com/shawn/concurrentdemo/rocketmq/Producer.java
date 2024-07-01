package com.shawn.concurrentdemo.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

public class Producer {
    static String address = "10.251.76.180:9876";
    static String topic = "inventory-notify-scm-accounting-document";
    public static void main(String[] args) throws Exception{

        //Instantiate with a producer group name.
        DefaultMQProducer producer = new
                DefaultMQProducer("local");
        // Specify name server addresses.
        producer.setNamesrvAddr(address);
//        producer.createTopic("a","TopicTest",4);
        //Launch the instance.
        producer.start();
        for (int i = 0; i < 1; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message(topic /* Topic */,
                    "" /* Tag */,
                    ("Hello RocketMQx " + i).getBytes("UTF-8") /* Message body */
            );
            //Call send message to deliver message to one of brokers.
            SendResult sendResult = producer.send(msg,1000);
            System.out.printf("%s%n", sendResult);
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
