package com.shawn.springboot.learn.controller;

import com.shawn.springboot.my.starter.HelloFormatTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luxufeng
 * @date 2020/11/3
 **/
@RestController
public class HelloController {

    @Autowired
    HelloFormatTemplate helloFormatTemplate;

    @GetMapping("/hello")
    public String format() {
        return helloFormatTemplate.doFormat();
    }
}

