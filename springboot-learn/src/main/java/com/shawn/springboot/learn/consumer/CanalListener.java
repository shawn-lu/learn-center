package com.shawn.springboot.learn.consumer;


import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.yonghui.mid.sdk.rocketmq.consumer.annotation.RocketMQOriginalConsumer;
import com.yonghui.mid.sdk.rocketmq.consumer.annotation.RocketMQOriginalListener;
import lombok.extern.slf4j.Slf4j;

@RocketMQOriginalConsumer(group = "canal_fp_listener")
@Slf4j
public class CanalListener {
    //声明监听方法，指定需要监听的topic
    @RocketMQOriginalListener(topic = "presale-sku-inventory-search")
    public ConsumeConcurrentlyStatus onMessage(MessageExt messageExt) {
        //获取到发送方发出的消息内容
        try {
            String messageExtBody = new String(messageExt.getBody(), "UTF-8");
            log.info("canal json : " + messageExtBody);
            //执行正确返回成功
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ////发生异常，需要返回RECONSUME_LATER，会尝试重试
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }
}
