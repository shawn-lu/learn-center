package com.shawn.kafka.demo.middle;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RocketMQSpringProducerVo {
    /* 扩展 */
    @Field(label = "bean后缀")
    private String beanSuffix;
    @Field(label = "服务器地址")
    private String address;

    private Boolean retryAnotherBrokerWhenNotStore;
    private int maxMessageSize;
}
