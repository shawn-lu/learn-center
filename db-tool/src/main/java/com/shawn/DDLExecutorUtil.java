package com.shawn;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.QueryRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


//
//alter table sdk_cfg_rocketmq_spring_consumer modify column user_id varchar(255) default null,
//        modify column `last_updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近更新时间';
//        alter table sdk_cfg_rocketmq_spring_producer modify column user_id varchar(255) default null,
//        modify column `last_updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近更新时间';
@Slf4j
public class DDLExecutorUtil {

    public static void main(String[] args) throws FileNotFoundException {
        String p = "dms";
        InputStream is = new FileInputStream("/Users/luxufeng/work/githubspace/learn-center/db-tool/src/main/resources/db-info/" + p + ".json");


        List<DBInfo> dbInfoList = JsonUtils.fromJson(is, List.class, DBInfo.class);

    String str = "\n" +
            "\n" +
            "CREATE TABLE `message_batch_task` (\n" +
            "  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',\n" +
            "  `task_name` varchar(255) NOT NULL COMMENT '任务名称',\n" +
            "  `operator` varchar(255) NOT NULL COMMENT '操作人',\n" +
            "  `file_key` varchar(1000)  NULL COMMENT '文件key',\n" +
            "  `file_name` varchar(1000)  NULL COMMENT '文件名',\n" +
            "  `parse_mode` varchar(1000) NOT NULL COMMENT '解析方式:MSG_ID,MSG_CONTENT',\n" +
            "  `task_type` varchar(1000) NOT NULL COMMENT '任务类型:RESEND,RECONSUME',\n" +
            "  `name_srv` varchar(500) NOT NULL COMMENT 'nameserver',\n" +
            "  `sn` varchar(50) DEFAULT NULL COMMENT 'sn',\n" +
            "  `topic` varchar(255) DEFAULT  NULL COMMENT 'topic',\n" +
            "  `tag` varchar(255) DEFAULT NULL COMMENT 'tag',\n" +
            "  `msg_key` varchar(255) DEFAULT NULL COMMENT 'key',\n" +
            "  `consumer_group` varchar(255) DEFAULT NULL COMMENT 'group',\n" +
            "  `task_status` varchar(255) DEFAULT NULL COMMENT '状态',\n" +
            "  `process_message` varchar(1000) DEFAULT NULL COMMENT '处理消息',\n" +
            "  `execute_start_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '执行开始时间',\n" +
            "  `execute_end_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '执行结束时间',\n" +
            "  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除',\n" +
            "  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
            "  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近更新时间',\n" +
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;\n" +
            "\n" +
            "\n" +
            "CREATE TABLE `message_batch_task_detail` (\n" +
            "  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',\n" +
            "  `task_id` bigint(20) NOT NULL COMMENT '任务id',\n" +
            "  `task_name` varchar(255) NOT NULL COMMENT '任务名',\n" +
            "  `name_srv` varchar(500) NOT NULL COMMENT 'nameserver',\n" +
            "  `sn` varchar(50) DEFAULT NULL COMMENT 'sn',\n" +
            "  `topic` varchar(255) DEFAULT  NULL COMMENT 'topic',\n" +
            "  `tag` varchar(255) DEFAULT NULL COMMENT 'tag',\n" +
            "  `msg_key` varchar(255) DEFAULT NULL COMMENT 'key',\n" +
            "  `consumer_group` varchar(255) DEFAULT NULL COMMENT 'group',\n" +
            "  `msg_id` varchar(255) DEFAULT  NULL COMMENT 'msgid',\n" +
            "  `msg_content` longtext DEFAULT  NULL COMMENT '消息内容',\n" +
            "  `msg_format` varchar(50) DEFAULT  NULL COMMENT '可选,message2,string',\n" +
            "  `process_status` varchar(255) DEFAULT NULL COMMENT '状态',\n" +
            "  `process_message` varchar(1000) DEFAULT NULL COMMENT '处理消息',\n" +
            "  `execute_start_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '执行开始时间',\n" +
            "  `execute_end_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '执行结束时间',\n" +
            "  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除',\n" +
            "  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
            "  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近更新时间',\n" +
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4";



        List<String> sqls = Arrays.stream(str.split(";")).collect(Collectors.toList());



//        sqls.add("alter table sdk_saas_rocketmq_consumer add column app_group varchar(100) DEFAULT NULL z


//
        for(String sql:sqls){
            for (DBInfo dbInfo : dbInfoList) {
                try {
                    if (dbInfo.getEnv().contains("prod")) {
                        continue;
                    }
                    QueryRunner qr = new QueryRunner(dbInfo.getDS());
                    System.out.println(sql.trim());
                    qr.execute(sql);
                } catch (Exception e) {
                    log.error("[{}]环境,sql语句执行失败", dbInfo.getEnv(), e);
                    continue;

                }
                log.warn("[{}]环境,sql语句执行成功", dbInfo.getEnv());
            }
        }

        log.warn("done");
    }


//    https://mmp-api-fz-sit.yonghuivip.com/ 10.208.16.167
}
//
//