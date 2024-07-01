package com.shawn.kafka.demo.middle;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class EsHighRestConfigDetailVo implements Serializable {
    /* 原生 */
    @Field(label = "password")
    private String password;

    @Field(label = "username")
    private String username;


    /* 扩展 */
    @Field(label = "bean后缀")
    private String beanSuffix;
    @Field(label = "服务器地址")
    private String address;
    @Field(label = "集群名称")
    private String name;

//    @NotNull
    private Integer connectTimeoutMs = 10_000;

//    @NotNull
    private Integer socketTimeoutMs = 60_000;

//    @NotNull
    private Integer connectionRequestTimeoutMs = 60_000;
}