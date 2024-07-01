package com.shawn.db;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class DDLValidateUtil {
    public static void main(String[] args) throws Exception {
        DBInfo standardDBInfo = DBInfo.builder().env("sh-dev").url("jdbc:mysql://10.251.77.235:3306/dms_rocket_mq?characterEncoding=utf-8&useSSL=false").username("yh_canal").password("j8,X/~1;95|8D63D").dbName("dms_rocket_mq").build();
        Map<String, TableInfo> standardMap = listMap(standardDBInfo);
        standardDBInfo.setTableInfoMap(standardMap);


        List<DBInfo> dbInfoList = Lists.newArrayList(
                DBInfo.builder().env("sh-dev").url("jdbc:mysql://10.251.77.235:3306/dms_rocket_mq?characterEncoding=utf-8&useSSL=false").username("yh_canal").password("j8,X/~1;95|8D63D").dbName("dms_rocket_mq").build(),
                DBInfo.builder().env("sh-sit").url("jdbc:mysql://10.246.193.14:3306/dms_rocket_mq?characterEncoding=utf-8&useSSL=false").username("yh_dev").password("X520inzWrG7skGTa").dbName("dms_rocket_mq").build(),
                DBInfo.builder().env("fz-sit").url("jdbc:mysql://10.205.0.243:3306/dms_rocket_mq?characterEncoding=utf-8&useSSL=false").username("yh_dev").password("DvpJe2x").dbName("dms_rocket_mq").build(),
                DBInfo.builder().env("sh-prod").url("jdbc:mysql://10.247.42.178:13306/dms_rocket_mq?characterEncoding=utf-8&useSSL=false").username("yh_product").password("),f#c+i+3B^g7NXa").dbName("dms_rocket_mq").build(),
                DBInfo.builder().env("fz-prod").url("jdbc:mysql://10.208.133.108:3306/dms_rocket_mq?characterEncoding=utf-8&useSSL=false").username("yh_product").password("),f#c+i+3B^g7NXa").dbName("dms_rocket_mq").build()

        );

        for (DBInfo d : dbInfoList) {
            Map<String, TableInfo> m = listMap(d);
            d.setTableInfoMap(m);
        }


        for (DBInfo d : dbInfoList) {
            compare(standardDBInfo, d);
        }

    }

    private static void compare(DBInfo standardDBInfo, DBInfo dbInfo) {
        log.info("参照环境[{}],比较环境[{}],库[{}]", standardDBInfo.getEnv(), dbInfo.getEnv(), dbInfo.getDbName());
        Map<String, TableInfo> standardMap = standardDBInfo.getTableInfoMap();
        Map<String, TableInfo> toCompareMap = dbInfo.getTableInfoMap();
        TableInfo tableInfo = null;
        TableInfo standardInfo = null;

        for (String tableName : standardMap.keySet()) {
            tableInfo = toCompareMap.get(tableName);
            standardInfo = standardMap.get(tableName);

            if (null == tableInfo) {
                log.warn("环境[{}],比较表[{}]不存在", dbInfo.getEnv(), tableName);
                continue;
            }
            compareColumn(standardInfo, tableInfo, dbInfo.getEnv());
        }
    }

    private static void compareColumn(TableInfo standardInfo, TableInfo tableInfo, String env) {
        log.info("比较表[{}]", tableInfo.getTableName());
        ColumnInfo columnInfo;
        ColumnInfo standardColumnInfo;
        if (standardInfo.getColumnInfoMap().size() != tableInfo.getColumnInfoMap().size()) {
            log.warn("目标环境[{}],表[{}]列数量不一致", env, tableInfo.getTableName());
        }
        for (String c : standardInfo.getColumnInfoMap().keySet()) {
            standardColumnInfo = standardInfo.getColumnInfoMap().get(c);
            columnInfo = tableInfo.getColumnInfoMap().get(c);
            if (null == columnInfo) {
                log.warn("目标环境[{}],比较表[{}],列[{}]不存在", env, tableInfo.getTableName(), standardColumnInfo.getColumnName());
                continue;
            }
            if (null != columnInfo && null != standardColumnInfo) {
                if (!columnInfo.equals(standardColumnInfo)) {
                    log.warn("目标环境[{}],比较表[{}],列[{}]存在不一致,标准[{}],目标[{}]", env, tableInfo.getTableName(), standardColumnInfo.getColumnName(), JSON.toJSON(standardColumnInfo), JSON.toJSON(columnInfo));
                }
            }

        }
    }

    public static Map<String, TableInfo> listMap(DBInfo dbInfo) throws Exception {
        QueryRunner qr = new QueryRunner(dbInfo.getDS());
        String sql = "select table_name as tableName from information_schema.TABLES t where t.TABLE_SCHEMA  = '" + dbInfo.getDbName() + "'";


//        select * from information_schema.TABLES t where t.TABLE_SCHEMA  = 'dms_rocket_mq'
//        select * from information_schema.`COLUMNS` t  where t.TABLE_NAME  = 'consumer_limiter_config' and t.TABLE_SCHEMA = 'dms_rocket_mq'
        List<TableInfo> tables = null;
        try {
            tables = qr.query(sql, new BeanListHandler<>(TableInfo.class));
        } catch (Exception e) {
            log.error("[{}],数据库连接异常", dbInfo.getEnv());
            throw e;
        }
        Map<String, TableInfo> tableInfoMap = new HashMap<>();


        for (TableInfo tableInfo : tables) {
            String sql2 = "select column_name as columnName,column_default as columnDefault ,is_nullable as hasNullable,column_type as columnType from information_schema.`COLUMNS` t " +
                    " where t.TABLE_NAME  = '" + tableInfo.getTableName() + "' and t.TABLE_SCHEMA = '" + dbInfo.getDbName() + "'";
            List<ColumnInfo> columnInfos = qr.query(sql2, new BeanListHandler<>(ColumnInfo.class));
            Map<String, ColumnInfo> columnInfoMap = new HashMap<>();
            for (ColumnInfo c : columnInfos) {
                columnInfoMap.put(c.getColumnName(), c);
            }
            tableInfo.setColumnInfoMap(columnInfoMap);
            tableInfoMap.put(tableInfo.getTableName(), tableInfo);
        }

        return tableInfoMap;
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TableInfo {
        String tableName;
        Map<String, ColumnInfo> columnInfoMap = new HashMap<>();

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ColumnInfo {
        String columnName;
        String columnDefault;
        String hasNullable;
        String columnType;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class DBInfo {
        String url;
        String username;
        String password;
        String dbName;
        String env;
        BasicDataSource basicDataSource = null;

        Map<String, TableInfo> tableInfoMap;


        public DataSource getDS() {
            if (null == basicDataSource) {
                if (null == url || null == username || password == null || dbName == null) {
                    throw new RuntimeException("数据库参数异常");
                }
                basicDataSource = new BasicDataSource();
                basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
                basicDataSource.setUsername(username);
                basicDataSource.setPassword(password);
                basicDataSource.setUrl(url);
            }
            return basicDataSource;
        }


    }
}
