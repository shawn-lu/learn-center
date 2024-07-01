CREATE TABLE `sdk_cfg_rocketmq_spring_producer_v2` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rocketmq_id` varchar(255) DEFAULT NULL COMMENT 'rocketmq_id',
  `address` varchar(255) DEFAULT NULL COMMENT 'address',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `topic` varchar(255) DEFAULT NULL COMMENT '主题',
  `retry_another_broker_when_not_store` tinyint(4) DEFAULT NULL,
  `max_message_size` bigint(20) DEFAULT NULL,
  `queue_num` int(11) DEFAULT NULL COMMENT '队列数',
  `is_deleted` tinyint(4) DEFAULT NULL COMMENT '标记删除',
  `has_created` tinyint(4) DEFAULT '0' COMMENT '是否已经创建到rocketmq',
  `create_error_message` varchar(255) DEFAULT NULL COMMENT 'TOPIC创建异常信息',
  `app_group` varchar(255) DEFAULT NULL COMMENT '分组',
  `application_id` varchar(255) DEFAULT NULL COMMENT 'mid_application的id',
  `last_updated_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '最后更新时间',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `user_id` varchar(255) DEFAULT NULL COMMENT '操作人',
  `project_app_id` bigint(10) DEFAULT NULL COMMENT 'project_app_id',
  `real_topic` varchar(255) DEFAULT NULL COMMENT '真实主题',
  `label_mq` varchar(255) DEFAULT NULL COMMENT 'MQ标签',
  PRIMARY KEY (`id`),
  UNIQUE KEY `topic_app_group_idx` (`topic`,`app_group`),
  KEY `idx_app` (`application_id`),
  KEY `idx_mq` (`address`,`rocketmq_id`),
  KEY `idx_ten` (`topic`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='rocketmq生产者topic信息';
CREATE TABLE `sdk_cfg_rocketmq_spring_consumer_v2` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rocketmq_id` varchar(255) DEFAULT NULL COMMENT 'rocketmq_id',
  `address` varchar(255) DEFAULT NULL COMMENT 'address',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `topic` varchar(255) DEFAULT NULL COMMENT '主题',
  `application_id_with_topic` varchar(255) DEFAULT NULL COMMENT '主题所属服务id',
  `app_group_with_topic` varchar(255) DEFAULT NULL COMMENT '主题所属服务分组',
  `consumer_group` varchar(255) DEFAULT NULL COMMENT '消费组',
  `application_id` varchar(255) DEFAULT NULL COMMENT '所属服务id',
  `app_group` varchar(255) DEFAULT NULL COMMENT '所属服务分组',
  `has_created` tinyint(4) DEFAULT '0' COMMENT '是否已经创建到rocketmq',
  `create_error_message` varchar(255) DEFAULT NULL COMMENT '消费组创建异常信息',
  `is_deleted` tinyint(4) DEFAULT NULL COMMENT '标记删除',
  `last_updated_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '最后更新时间',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `user_id` varchar(255) DEFAULT NULL COMMENT '操作人',
  `real_consumer_group` varchar(255) DEFAULT NULL COMMENT '真实消费组',
  `real_topic` varchar(255) DEFAULT NULL COMMENT '真实主题',
  `project_app_id` bigint(10) DEFAULT NULL COMMENT 'project_app_id',
  `label_mq` varchar(255) DEFAULT NULL COMMENT 'MQ标签',
  PRIMARY KEY (`id`),
  UNIQUE KEY `topic_consumer_group_app_group_idx` (`topic`,`consumer_group`,`app_group`),
  KEY `idx_app` (`application_id`),
  KEY `idx_topic_app` (`application_id_with_topic`),
  KEY `idx_mq` (`address`,`rocketmq_id`),
  KEY `idx_t_a` (`topic`),
  KEY `idx_g` (`consumer_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='rocketmq消费者信息';

alter table sdk_cfg_druid add column param_config_json  varchar(1000) DEFAULT NULL COMMENT 'druid参数';
alter table  mid_application add column  `project_app_id` bigint(10) DEFAULT NULL COMMENT 'project_app_id';



