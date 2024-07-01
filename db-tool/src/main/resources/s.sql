

CREATE TABLE `data_source_meta_for_sync` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `ds_key` varchar(255) DEFAULT NULL COMMENT '唯一匹配',
  `url` varchar(255) DEFAULT NULL COMMENT '连接url',
  `username` varchar(100) DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `db_name` varchar(100) DEFAULT NULL COMMENT '数据名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `driver_class_name` varchar(255) DEFAULT NULL COMMENT '备注',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_n_key` (`ds_key`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='数据源信息'

alter table st_hosts add column   `is_delete` tinyint(1) NOT null comment '删除标记',
                     modify column `name` varchar(100) DEFAULT NULL comment '名称';

--1221
alter table mid_elasticsearch
         add column deploy_area varchar(100)  DEFAULT NULL comment '部署位置：（上海唐镇，福州马尾，腾讯公有云等)',
         add column index_num varchar(10)  DEFAULT NULL comment '索引数',
         add column run_type varchar(100)  DEFAULT NULL comment '运行类型:容器或虚拟机',
         add column ext_prop varchar(100)  DEFAULT NULL comment '扩展属性,json格式',
         add column remarks varchar(100)  DEFAULT NULL comment '备注',
         add column monitor_address varchar(200)  DEFAULT NULL comment '监控地址',
         add column enabled_password tinyint DEFAULT 1 comment '是否启用密码,0不启用，1启用';


CREATE TABLE `dms_rocket_mq_meta` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `name_server` varchar(255) DEFAULT NULL COMMENT '服务器地址',
  `version` varchar(100) DEFAULT NULL COMMENT '服务器版本',
  `has_hide` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否界面上隐藏',
  `outer_sn` varchar(50) DEFAULT NULL COMMENT '外部唯一标识',
  `region` varchar(255) DEFAULT NULL,
  `domain_range` varchar(255) DEFAULT NULL,
  `console_address` varchar(255) DEFAULT NULL,
  `monitor_address` varchar(255) DEFAULT NULL,
  `cluster_name` varchar(255) DEFAULT NULL,
  `broker_num` int(10) DEFAULT NULL,
  `deploy_mode` varchar(255) DEFAULT NULL COMMENT '部署模式,cvm,容器等',
  `machine_config` varchar(1000) DEFAULT NULL COMMENT 'cpu,内存,网卡等描述',
  `deploy_area` varchar(255) DEFAULT NULL COMMENT '部署位置',
  `real_address` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `outer_sn` (`outer_sn`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;



alter table mid_rocketmq
         add column deploy_area varchar(100)  DEFAULT NULL comment '部署位置：（上海唐镇，福州马尾，腾讯公有云等)',
         add column broker_num varchar(10)  DEFAULT NULL comment 'broker数',
         add column run_type varchar(100)  DEFAULT NULL comment '运行类型:容器或虚拟机',
         add column ext_prop varchar(100)  DEFAULT NULL comment '扩展属性,json格式',
         add column remarks varchar(100)  DEFAULT NULL comment '备注',
         add column monitor_address varchar(200)  DEFAULT NULL comment '监控地址',
         add column console_address varchar(200)  DEFAULT NULL comment '控制台地址';

