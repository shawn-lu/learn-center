//package com.shawn;
//
//
//import com.google.common.base.CaseFormat;
//import com.shawn.mapper.TableColumnMapper;
//import com.shawn.model.Column;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.apache.ibatis.io.Resources;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//import org.yaml.snakeyaml.DumperOptions;
//import org.yaml.snakeyaml.Yaml;
//import org.yaml.snakeyaml.nodes.Tag;
//
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.StringWriter;
//import java.util.*;
//import java.util.stream.Collectors;
//
//public class AdaptorProcesser {
//
//
//    //            - name: member-es7-config
//    //              mountPath: /app/resources/es7/yh_member.yml
//    //              subPath: yh_member.yml
//    //            - name: member-es7-config
//    //              mountPath: /app/resources/es7/t_
//
//    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Builder
//    static class ConfigMeta{
//        private String name;
//        private String mountPath;
//        private String subPath;
//
//    }
//
//    static void printConfigMap() {
//        List<Map<String,String>> configMetas =new ArrayList<>();
//        for (String tableName : tablesNames()) {
//            Map<String,String> configMeta = new LinkedHashMap<>();
//            configMeta.put("name","member-es7-config");
//            configMeta.put("mountPath","/app/resources/es7/"+tableName+".yml");
//            configMeta.put("subPath",tableName+".yml");
//            configMetas.add(configMeta);
//        }
//        DumperOptions options = new DumperOptions();
//        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
//        StringWriter sw = new StringWriter();
//        Yaml yaml = new Yaml(options);
//        String s = yaml.dumpAs(configMetas, Tag.MAP, DumperOptions.FlowStyle.BLOCK);
//        System.out.println(s);
//
//    }
//    public static void main(String[] args) {
////        printConfigMap();
////        if(true){
////            return;
////        }
//        List<Map<String,Object>> result = new ArrayList<>();
//        String datasource = "defaultDS";
//        String topic, index, groupId,esKey;
//        String ss = "";
//        for (String tableName : tablesNames()) {
//
//            esKey  = generateEsAdaptorKey(tableName);
//            System.out.println("process "+tableName);
//            topic = convertTableToTopic(tableName);
//            System.out.println("topic:" + topic);
//            index = convertTableToEs(tableName);
//            groupId = convertTableToGroupId(tableName);
//
//            Map<String, Object> esMapping = new LinkedHashMap<>();
//            esMapping.put("_index", index);
//            esMapping.put("_id", "_id");
//            esMapping.put("sql", generateSql(tableName));
//            esMapping.put("commitBatch", 3000);
//            ConsumerConfig consumerConfig = ConsumerConfig.builder()
//                    .dataSourceKey(datasource)
//                    .destination(topic)
//                    .groupId(groupId)
//                    .outerAdapterKey(esKey)
//                    .esMapping(esMapping).build();
//
//            generateAndWrite(tableName, consumerConfig);
//            Map<String,Object> s = new LinkedHashMap<>();
//            s.put("instance",topic);
//            List<Map<String,Object>> groups = new ArrayList<>();
//            Map<String,Object> groupMap = new LinkedHashMap<>();
//            groupMap.put("groupId",groupId);
//            groupMap.put("outerAdapters","oooo"+tableName);
//            groups.add(groupMap);
//            s.put("groups",groups);
//            result.add(s);
//        }
//
//        DumperOptions options = new DumperOptions();
//        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
//        Yaml yaml = new Yaml(options);
//        ss = yaml.dumpAsMap(result);
//
//        for (String tableName : tablesNames()) {
//            ss = ss.replace("    outerAdapters: oooo"+tableName,"    outerAdapters:\n" +
//                    "          #        - name: es7\n" +
//                    "          - name: es7\n" +
//                    "            key: " + generateEsAdaptorKey(tableName) + "\n" +
//                    "            hosts: es7101-dev.mid.io:9200 # 127.0.0.1:9200 for rest mode\n" +
//                    "            properties:\n" +
//                    "              mode: rest # or rest\n" +
//                    "              security.auth: elastic:ak7NEDt41fu43pY6U0J6y1k9 #  only used for rest mode\n" +
//                    "              cluster.name: hlwyf-search");
//        }
//        writeToYaml("application",ss);
//    }
//
//
//
//    static String generateSql(String tableName){
//        SqlSessionFactory sqlSessionFactory = loadFromXml();
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        TableColumnMapper tableColumnMapper = sqlSession.getMapper(TableColumnMapper.class);
//        List<Column>  columns  = tableColumnMapper.selectColumnByTable(tableName);
//        List<String> columnNames = columns.stream().map(c -> c.getColumnName()).collect(Collectors.toList());
//        String column = generateColumns(columnNames);
//        // a.id as _id,a.member_code as memberCode,a.mobile,a.email,a.we
//        String sql = String.format("select %s from %s a",column,tableName);
//        return sql;
//
//    }
//
//    static String generateColumns(List<String> columnNames) {
//        StringBuilder sb = new StringBuilder();
//        for (String c : columnNames) {
//            if (c.equalsIgnoreCase("id")) {
//                sb = sb.append("a.id as _id").append(",");
//            } else {
//                sb = sb.append("a.").append(c).append(" as ").append(fomcat(c)).append(",");
//            }
//        }
//        String result = sb.toString();
//        System.out.println(result);
//        return result.substring(0, result.length() - 1);
//    }
//
//    static String fomcat(String c){
//        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, c);
//    }
//
//
//    static void generateAndWrite(String tableName, ConsumerConfig consumerConfig) {
//        DumperOptions options = new DumperOptions();
//        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
//        Yaml yaml = new Yaml(options);
//        StringWriter writer = new StringWriter();
//        String s = yaml.dumpAsMap(consumerConfig);
//        System.out.println("-----");
//        System.out.println(s);
//        writeToYaml(tableName, s);
////        System.out.println(writer.toString());
//    }
//
//    static void writeToYaml(String tableName, String s) {
//        FileWriter fw = null;
//        try {
//            String path = "/Users/luxufeng/work/githubspace/learn-center/data-tool/src/main/resources/es7-adapter/";
//            fw = new FileWriter(path + tableName + ".yml");
//            fw.write(s);
//            fw.close();
//        } catch (Exception e) {
//
//        } finally {
//            if (null != fw) {
//                try {
//                    fw.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }
//    }
//
//    static String generateEsAdaptorKey(String tableName){
//        return tableName + "-es7-key";
//    }
//
//
//    static String convertTableToTopic(String tableName) {
//        return tableName.replaceAll("_", "-") + "-binlog";
//    }
//
//
//    static String convertTableToEs(String tableName) {
//        return "member_" + tableName;
//    }
//
//    static String convertTableToGroupId(String tableName) {
//        return "group_" + tableName.replaceAll("_", "-");
//    }
//
//
//    private static SqlSessionFactory loadFromXml() {
//        SqlSessionFactory sqlSessionFactory = null;
//        try {
//            String resource = "mybatis-config.xml";
//            InputStream inputStream = Resources.getResourceAsStream(resource);
//            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return sqlSessionFactory;
//    }
//
//
////    - instance: yh-member-binlog # canal instance Name or mq topic name
////      groups:
////        - groupId: canal-es7-adaptor-ymb
////          outerAdapters:
////            #        - name: es7
////            - name: es7
////              hosts: es7101-dev.mid.io:9200 # 127.0.0.1:9200 for rest mode
////              properties:
////                mode: rest # or rest
////                security.auth: elastic:ak7NEDt41fu43pY6U0J6y1k9 #  only used for rest mode
////                cluster.name: hlwyf-search
//
//    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Builder
//    static class InstanceConfig {
//        String instance;
//        List<Group> groups = new ArrayList<>();
//
//        InstanceConfig(String instance, String groupId) {
//            this.instance = instance;
//            groups.add(new Group(groupId));
//        }
//
//    }
//
//    @Data
//    static class Group{
//        String groupId;
//        String outerAdapters = "oooo";
//        Group(String groupId){
//            this.groupId = groupId;
//        }
//    }
//    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Builder
//    static class ConsumerConfig {
//        private String dataSourceKey;
//        private String destination;
//        private String groupId;
//        private String outerAdapterKey;
//        private Map<String, Object> esMapping = new LinkedHashMap<>();
//
//
//    }
//
//    //dataSourceKey: defaultDS        # 源数据源的key, 对应上面配置的srcDataSources中的值
//    //destination: yh-member-binlog            # cannal的instance或者MQ的topic
//    //groupId: canal-es7-adaptor-ymb               # 对应MQ模式下的groupId, 只会同步对应groupId的数据
//    //esMapping:
//    //  _index: member_yh_member           # es 的索引名称
//    //  _id: _id                      # es 的_id, 如果不配置该项必须配置下面的pk项_id则会由es自动分配
//    //  #  pk: id                       # 如果不需要_id, 则需要指定一个属性为主键属性
//    //  # sql映射
//    //  sql: "select a.id as _id,a.member_code as memberCode,a.mobile,a.email,a.wechat_openid as wechatOpenid,a.account_type as accountType,a.password,a.status,a.associate_id as associateId,a.signup_time as signupTime,a.created_at as createdAt,a.created_by as createdBy,a.updated_at as updatedAt,a.updated_by as updatedBy,a.wechat_unionid as wechatUnionid,a.binding_time as bindingTime,a.last_updated_at as lastUpdatedAt,a.source,a.bravo_user_flag as bravoUserFlag,a.bravo_user_binding_flag as bravoUserBindingFlag,a.member_type as memberType,a.is_initial_password as isInitialPassword,a.member_group as memberGroup,a.member_no as memberNo,a.platform,a.agent_support_tag as agentSupportTag from yh_member a"
//    //  #  objFields:
//    //  #    _labels: array:;           # 数组或者对象属性, array:; 代表以;字段里面是以;分隔的
//    //  #    _obj: object               # json对象
//    //  #  etlCondition: "where a.last_updated_at>='{0}'"     # etl 的条件参数
//    //  commitBatch: 3000
//
//
//    static String[] tablesNames(){
//        String s = "t-member\n" +
//                "t-member-card\n" +
//                "t-member-mobile\n" +
//                "yh-auth-member-info\n" +
//                "yh-delivery-address\n" +
//                "yh-member-activity-auth-info\n" +
//                "yh-member-alipay\n" +
//                "yh-member\n" +
//                "yh-member-information\n" +
//                "yh-member-wechat\n" +
//                "yh-super-member\n" +
//                "yh-thirdparty-member\n" +
//                "yh-unionid-member\n" +
//                "yh-virtual-member\n" +
//                "yh-market-channel-meta\n" +
//                "yh-member-rights-setting\n" +
//                "yh-member-extend-config\n" +
//                "yh-member-unregister";
//        return s.replaceAll("-","_").split("\n");
//    }
//}
