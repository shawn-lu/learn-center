package com.shawn;

import com.alibaba.fastjson.JSON;
import com.shawn.mapper.TableColumnMapper;
import com.shawn.model.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataUtils {
    public static void main(String[] args) {
        new DataUtils().parseTableToEsMapping();
    }

    public void parseTableToEsMapping() {
        SqlSessionFactory sqlSessionFactory = loadFromXml();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        TableColumnMapper tableColumnMapper = sqlSession.getMapper(TableColumnMapper.class);
        String tableName = "bhd_base_supplier_purchasing_info_sap";
        List<Column> tableColumns = tableColumnMapper.selectColumnByTable(tableName);
        System.out.println(tableColumns);
        Map<String,EsColumn> m = new HashMap<>();
        for (Column c:tableColumns){
            m.put(c.getColumnName(),new EsColumn(typeConvert(c.getDataType())));
        }

        EsMapping esMapping = new EsMapping();
        esMapping.put(m);
        System.out.println(JSON.toJSON(esMapping));
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class EsMapping{
        Map<String,Map<String,Map<String,EsColumn>>> mapping = new HashMap<>();

        void put(Map<String,EsColumn> m){
            Map<String,Map<String,EsColumn>> p = new HashMap<>();
            p.put("properties",m);
            this.mapping.put("_doc",p);
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class EsColumn{
        String type;
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

//    "mappings": {
//        "_default_": {
//            "dynamic_date_formats": [
//            "yyyy-MM-dd HH:mm:ss"
//                ]
//        },
//        "type_base_supplier_purchasing_info": {
//            "properties": {
//                "clear_location_code": {
//                    "type": "keyword"
//                },
//                "clear_purchase_org"

    public String typeConvert(String mysqlType) {
        switch (mysqlType.toLowerCase()) {
            case "tinyint":
                return "integer";
            case "bigint":
                return "long";
            case "decimal":
            case "varchar":
                return "text";
            case "timestamp":
            case "datetime":
                return "date";
            default:
                throw new RuntimeException("没有找到[" + mysqlType + "]对应的es类型");
        }
    }

}
