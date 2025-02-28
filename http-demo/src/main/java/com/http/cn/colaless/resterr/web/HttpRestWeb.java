package com.http.cn.colaless.resterr.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController("rest")
@RequestMapping("rest")
@Slf4j
public class HttpRestWeb {
    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/{times}")
    public void multiGet(@PathVariable Integer times) {
        log.info("multi get " + times);
        for (int i = 0; i < times; i++) {
            int finalI = i;
            new Thread(() -> {
                doGet(finalI);
            }).start();
        }
    }

//    @GetMapping()
//    public void get() {
//      doGet(9);
//    }

    private void doGet(int i) {
        log.info("start-{}", i);
//        Resource forObject = restTemplate.getForObject("http://pro.file-storage-service.jszt-003.gw.yonghui.cn/download/tagx/41dd3e52-f28f-11ef-9c9f-fe55273424d0/crowd-14385-2025-02-24.csv", Resource.class);
        ResponseEntity<InputStreamResource> forObject = restTemplate.getForEntity("http://pro.file-storage-service.jszt-003.gw.yonghui.cn/download/tagx/41dd3e52-f28f-11ef-9c9f-fe55273424d0/crowd-14385-2025-02-24.csv",
                InputStreamResource.class);

        log.info(forObject.getBody().toString());
        log.info("succ-{}", i);
    }
}


