package com.shawn.concurrentdemo.rocketmq;
//
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
//import org.apache.rocketmq.remoting.common.RemotingHelper;

//import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
//import com.alibaba.rocketmq.client.producer.SendResult;
//import com.alibaba.rocketmq.common.message.Message;

/**
 * @author luxufeng
 * @date 2019/9/6
 **/
public class SyncProducer {
    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new
                DefaultMQProducer("local");
        // Specify name server addresses.
        producer.setNamesrvAddr("10.0.0.2:9876;10.0.0.9:9876;10.0.0.10:9876;");
//        producer.createTopic("a","TopicTest",4);
        //Launch the instance.
        producer.start();
        for (int i = 0; i < 1; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("canal-type3-test" /* Topic */,
                    "" /* Tag */,
                    ("Hello RocketMQ " +
                            i).getBytes("UTF-8") /* Message body */
            );
            //Call send message to deliver message to one of brokers.
            SendResult sendResult = producer.send(msg,1000);
            System.out.printf("%s%n", sendResult);
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
