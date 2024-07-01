package com.shawn.kafka.demo.middle;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class HbaseConfigDetailVo implements Serializable {
	/* 原生 */
	@Field(label = "maxThread")
	private Integer maxThread;

	/* 扩展 */
	@Field(label = "hbaseZookeeper地址")
	private String address;
	@Field(label = "zookeeper port")
	private String clientPort;
	@Field(label = "bean后缀")
	private String beanSuffix;


}