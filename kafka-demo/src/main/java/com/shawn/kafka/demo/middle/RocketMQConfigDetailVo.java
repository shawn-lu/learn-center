package com.shawn.kafka.demo.middle;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RocketMQConfigDetailVo implements Serializable {
	/* 原生 */

	/* 扩展 */
	@Field(label = "bean后缀")
	private String beanSuffix;
	@Field(label = "服务器地址")
	private String address;

}