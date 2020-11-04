package com.shawn.springboot.my.starter.autoconfiguration;

import com.shawn.springboot.my.starter.HelloFormatTemplate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luxufeng
 * @date 2020/11/3
 **/
@EnableConfigurationProperties(HelloProperties.class)
@Configuration
public class HelloAutoConfiguration {

    @Bean
    public HelloFormatTemplate helloFormatTemplate(HelloProperties helloProperties) {
        return new HelloFormatTemplate(helloProperties);
    }
}
