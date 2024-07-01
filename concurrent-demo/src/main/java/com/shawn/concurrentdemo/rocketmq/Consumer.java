package com.shawn.concurrentdemo.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author luxufeng
 * @date 2019/9/6
 **/
@Slf4j
public class Consumer {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("local-test");

        consumer.setNamesrvAddr("rocketmq-ns-dev.mid.io:9876");
        consumer.subscribe("wms-sync", "*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                try {
                    String msgStr = new String(msgs.get(0).getBody(), "UTF-8");
                    System.out.println(new Date(msgs.get(0).getBornTimestamp()));
//                    System.out.printf("New Messages: %s", msgStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.setConsumeTimeout(10000L);
        //Launch the consumer instance.
        consumer.start();

        System.out.printf("Consumer Started.%n");
    }
}