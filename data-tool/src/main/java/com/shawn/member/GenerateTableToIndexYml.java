package com.shawn.member;

import com.google.common.base.CaseFormat;
import com.shawn.mapper.TableColumnMapper;
import com.shawn.model.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.shawn.member.Utils.MapExt;


public class GenerateTableToIndexYml {

    public static void main(String[] args) {
        String path = "/Users/luxufeng/work/githubspace/learn-center/data-tool/src/main/resources/es7-adapter/";
        String datasource = "defaultDS";
        String topic, index, groupId, esKey;
        String ss = "";
        for (String tableName : tablesNames()) {
            esKey = generateEsAdaptorKey(tableName);
            topic = convertTableToTopic(tableName);
            index = convertTableToEs(tableName);
            groupId = convertTableToGroupId(tableName);
            MapExt mapExt = MapExt.builder().build();
            mapExt.put("dataSourceKey", datasource)
                    .put("destination", topic)
                    .put("groupId", groupId)
                    .put("outerAdapterKey", esKey)
                    .put("esMapping",MapExt.builder().build()
                            .put("_index",index)
                            .put("_id","_id")
                            .put("sql",generateSql(tableName))
                            .put("commitBatch",3000)
                            .getValue());
            Utils.writeToFile(mapExt.getValue(), path, tableName + ".yml");
        }
    }


    static String generateSql(String tableName) {
        SqlSessionFactory sqlSessionFactory = loadFromXml();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        TableColumnMapper tableColumnMapper = sqlSession.getMapper(TableColumnMapper.class);
        List<Column> columns = tableColumnMapper.selectColumnByTable(tableName);
        List<String> columnNames = columns.stream().map(c -> c.getColumnName()).collect(Collectors.toList());
        String column = generateColumns(columnNames);
        // a.id as _id,a.member_code as memberCode,a.mobile,a.email,a.we
        String sql = String.format("select %s from %s a", column, tableName);
        return sql;

    }


    private static SqlSessionFactory loadFromXml() {
        SqlSessionFactory sqlSessionFactory = null;
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqlSessionFactory;
    }

    static String fomcat(String c) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, c);
    }


    static String generateColumns(List<String> columnNames) {
        StringBuilder sb = new StringBuilder();
        for (String c : columnNames) {
            if (c.equalsIgnoreCase("id")) {
                sb = sb.append("a.id as _id").append(",a.id as id,");
            } else {
                sb = sb.append("a.").append(c).append(" as ").append(fomcat(c)).append(",");
            }
        }
        String result = sb.toString();
        System.out.println(result);
        return result.substring(0, result.length() - 1);
    }

    static String generateEsAdaptorKey(String tableName) {
        return tableName + "-es7-key";
    }


    static String convertTableToTopic(String tableName) {
        return tableName.replaceAll("_", "-") + "-binlog";
    }


    static String convertTableToEs(String tableName) {
        return "member_" + tableName;
    }

    static String convertTableToGroupId(String tableName) {
        return "group_" + tableName.replaceAll("_", "-");
    }

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

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class ConsumerConfig {
        private String dataSourceKey;
        private String destination;
        private String groupId;
        private String outerAdapterKey;
        private Map<String, Object> esMapping = new LinkedHashMap<>();


    }
}
