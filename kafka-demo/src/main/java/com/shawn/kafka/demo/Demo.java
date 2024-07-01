package com.shawn;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

@Slf4j
public class Demo {
    public static void main(String[] args) throws Exception{
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("local%test0726");
//        System.setProperty("LOG_FILE_PATH","/Users/luxufeng/work/yh-dms-rocketmq/dms-rocketmq/log");
//        consumer.setNamesrvAddr("10.251.80.44:9876;10.251.80.21:9876;10.251.80.8:9876;10.251.80.6:9876");
        consumer.setNamesrvAddr("rocketmq-ns-dev.mid.io:9876");
        consumer.subscribe("o2o-support-account-center-syn", "*");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
//        log.info("xxxx");
//
//        if(true){
//            return;
//        }
//        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);
//        consumer.setConsumeTimestamp("1658815200000");
//        consumer.setConsumeThreadMin(1);
//        consumer.setConsumeThreadMax(1);
//        log.info("xxxx");
        //Thu Jan 27 11:22:56 CST 2022
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                try {
                    String msgStr = new String(msgs.get(0).getBody(), "UTF-8");
                    log.info("message:[{}]", msgStr);
//                    Thread.sleep(5000);
//                    System.out.println(DateUtils.formatYMDHMS(msgs.get(0).getBornTimestamp()) + "---" + msgs.get(0).getMsgId());
//                    System.out.printf("New Messages: %s", msgStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
//        consumer.setConsumeTimeout(10000L);
        //Launch the consumer instance.
        consumer.start();

        while(true){

        }
    }
}
