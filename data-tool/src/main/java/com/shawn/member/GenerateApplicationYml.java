package com.shawn.member;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;

import java.util.*;

import static com.shawn.member.Utils.MapExt;

//    server:
//      port: 8081
//    spring:
//      jackson:
//        #    date-format: yyyy-MM-dd HH:mm:ss
//        date-format: yyyy-MM-dd'T'HH:mm:ss
//        time-zone: GMT+8
//        default-property-inclusion: non_null
//
//    canal.conf:
//      mode: rocketMQ #tcp kafka rocketMQ rabbitMQ
//      flatMessage: true
//    #  zookeeperHosts: ${ZK_HOST:10.195.1.57:2181}
//      syncBatchSize: 1000
//      retries: 0
//      timeout:
//      accessKey:
//      secretKey:
//      consumerProperties:

//    rocketmq.namespace:
//    rocketmq.namesrv.addr: ${ROCKETMQ_ADDR:rocketmq-ns-dev.mid.io:9876}
//    rocketmq.batch.size: 1000
//    rocketmq.enable.message.trace: false
//    rocketmq.customized.trace.topic:
//    rocketmq.access.channel:
//    rocketmq.subscribe.filter:

//  secretKey:
//  consumerProperties:
//    rocketmq.namespace:
//    rocketmq.namesrv.addr: ${ROCKETMQ_ADDR:rocketmq-ns-dev.mid.io:9876}
//    rocketmq.batch.size: 1000
//    rocketmq.enable.message.trace: false
//    rocketmq.customized.trace.topic:
//    rocketmq.access.channel:
//    rocketmq.subscribe.filter:
//  srcDataSources:
//    defaultDS:
//      url: jdbc:mysql://10.251.77.188:3306/member_center?useUnicode=true
//      username: yh_dev
//      password: DvpJe2x
//  canalAdapters:
//    - instance: t-member-binlog
public class GenerateApplicationYml {
    public static void main(String[] args) {
        String path = "/Users/luxufeng/work/githubspace/learn-center/data-tool/src/main/resources/es7-adapter/";
        String mq = "rocketmq-ns-dev.mid.io:9876";
        String ds1Key = "defaultDS";
        String ds1Url = "jdbc:mysql://10.251.77.188:3306/member_center?useUnicode=true";
        String ds1UserName = "yh_dev";
        String ds1Password = "DvpJe2x";

        MapExt mapExt = MapExt.builder().build();
        mapExt.put("server", MapExt.builder().build().put("port", 8081).getValue());
        mapExt.put("spring", MapExt.builder().build().put("jackson", MapExt.builder().build()
                .put("date-format", "yyyy-MM-dd'T'HH:mm:ss")
                .put("time-zone", "GMT+8")
                .put("default-property-inclusion", "non_null").getValue())
                .getValue());

        mapExt.put("canal.conf",
                MapExt.builder().build()
                        .put("mode", "rocketMQ")
                        .put("flatMessage", true)
                        .put("syncBatchSize", 1000)
                        .put("retries", 0)
                        .put("accessKey", "")
                        .put("secretKey", "")
                        .put("consumerProperties",
                                MapExt.builder().build()
                                        .put("rocketmq.namesrv.addr", mq)
                                        .put("rocketmq.batch.size", 1000)
                                        .put("rocketmq.enable.message.trace", false).getValue())
                        .put("srcDataSources", MapExt.builder().build().put(ds1Key, MapExt.builder().build()
                                .put("url", ds1Url)
                                .put("username", ds1UserName)
                                .put("password", ds1Password).getValue()).getValue())
                        .put("canalAdapters", buildList()).getValue());

        Utils.writeToFile(mapExt.getValue(), path, "application.yml");
    }

    // - name: es7
    //            key: t_member-es7-key
    //            hosts: es7101-dev.mid.io:9200 # 127.0.0.1:9200 for rest mode
    //            properties:
    //              mode: rest # or rest
    //              security.auth: elastic:ak7NEDt41fu43pY6U0J6y1k9 #  only used for rest mode
    //              cluster.name: hlwyf-search
    private static List<Object> buildList() {
        String esHosts = "es7101-dev.mid.io:9200";
        String esAuth = "elastic:ak7NEDt41fu43pY6U0J6y1k9";
        String esClusterName = "hlwyf-search";
        Utils.ListExt ext = Utils.ListExt.builder().build();
        for (String t : tablesNames()) {
            ext.add(MapExt.builder().build().put("instance", convertTableToTopic(t))
                    .put("groups", Utils.ListExt.builder().build().add(
                            MapExt.builder().build().put("groupId", convertTableToGroupId(t))
                                    .put("outerAdapters",
                                            Utils.ListExt.builder().build()
                                                    .add(
                                                            MapExt.builder().build()
                                                                    .put("name", "es7")
                                                                    .put("key", generateEsAdaptorKey(t))
                                                                    .put("hosts", esHosts)
                                                                    .put("properties", MapExt.builder().build()
                                                                            .put("mode", "rest")
                                                                            .put("security.auth", esAuth)
                                                                            .put("cluster.name", esClusterName).getValue())
                                                                    .getValue()).getValue()).getValue()
                    ).getValue()).getValue());
        }
        return ext.getValue();
    }


    static String generateEsAdaptorKey(String tableName) {
        return tableName + "-es7-key";
    }


    static String convertTableToGroupId(String tableName) {
        return "group_" + tableName.replaceAll("_", "-");
    }


    //- instance: t-member-binlog
    //  groups:
    //  - groupId: group_t-member
    //    outerAdapters:
    //          #        - name: es7
    //          - name: es7
    //            key: t_member-es7-key
    //            hosts: es7101-dev.mid.io:9200 # 127.0.0.1:9200 for rest mode
    //            properties:
    //              mode: rest # or rest
    //              security.auth: elastic:ak7NEDt41fu43pY6U0J6y1k9 #  only used for rest mode
    //              cluster.name: hlwyf-search
    //- instance: t-member-card-binlog

    static String[] tablesNames() {
        String s = "t-member\n" +
                "t-member-card\n" +
                "t-member-mobile\n" +
                "yh-auth-member-info\n" +
                "yh-delivery-address\n" +
                "yh-member-activity-auth-info\n" +
                "yh-member-alipay\n" +
                "yh-member\n" +
                "yh-member-information\n" +
                "yh-member-wechat\n" +
                "yh-super-member\n" +
                "yh-thirdparty-member\n" +
                "yh-unionid-member\n" +
                "yh-virtual-member\n" +
                "yh-market-channel-meta\n" +
                "yh-member-rights-setting\n" +
                "yh-member-extend-config\n" +
                "yh-member-unregister";
        return s.replaceAll("-", "_").split("\n");
    }

    static String convertTableToTopic(String tableName) {
        return tableName.replaceAll("_", "-") + "-binlog";
    }

    static String convert(Map<String, Object> m) {

        DumperOptions options = new DumperOptions();
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);
        String s = yaml.dumpAs(m, Tag.MAP, null);
        return s;
    }

}
