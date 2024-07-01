package com.shawn.kafka.demo.middle;

import lombok.*;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SdkConfig {
    private List<JedisConfigDetailVo> jedisConfigs;
    private List<HbaseConfigDetailVo> hbaseConfigs;
    private List<DruidConfigDetailVo> druidConfigs;
    private List<EsHighRestConfigDetailVo> esHighRestConfigs;
    private List<RocketMQConfigDetailVo> rocketMQConfigDetailVos;
    private List<RocketMQSpringProducerVo> rocketMQSpringProducerVos;
    private List<RocketMQSpringConsumerVo> rocketMQSpringConsumerVos;
    private Map<String,String> extProperties;

}