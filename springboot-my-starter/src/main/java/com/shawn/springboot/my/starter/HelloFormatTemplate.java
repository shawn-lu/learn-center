package com.shawn.springboot.my.starter;

import com.alibaba.fastjson.JSON;
import com.shawn.springboot.my.starter.autoconfiguration.HelloProperties;

/**
 * @author luxufeng
 * @date 2020/11/3
 **/
public class HelloFormatTemplate {

    private HelloProperties helloProperties;

    public HelloFormatTemplate(HelloProperties helloProperties) {
        this.helloProperties = helloProperties;
    }

    public <T> String doFormat() {
        return JSON.toJSONString(helloProperties.getInfo());
    }
}
