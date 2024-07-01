package com.shawn.kafka.demo.middle;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class JedisConfigDetailVo implements Serializable {
	/* 原生 */
	@Field(label = "集群最大重定向次数")
	private Integer maxRedirects;
	@Field(label = "maxIdle")
	private Integer maxIdle;
	@Field(label = "maxTotal")
	private Integer maxTotal;
	@Field(label = "maxWaitMillis")
	private Integer maxWaitMillis;
	@Field(label = "minIdle")
	private Integer minIdle;
	@Field(label = "testOnBorrow")
	private Boolean testOnBorrow;

	/* 扩展 */
	@Field(label = "服务器地址")
	private String address;
	@Field(label = "集群类型")
	private String clusterType;
	@Field(label = "bean后缀")
	private String beanSuffix;
	@Field(label = "redis密码")
	private String password;
}