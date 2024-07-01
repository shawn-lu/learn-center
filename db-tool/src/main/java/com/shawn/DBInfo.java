package com.shawn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DBInfo {
    public String url;
    public String username;
    public String password;
    public String dbName;
    public String env;
    public BasicDataSource basicDataSource = null;

    public Map<String, DDLValidateUtil.TableInfo> tableInfoMap;


    public DataSource getDS() throws SQLException {
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
        QueryRunner qr = new QueryRunner(basicDataSource);
        System.out.println("exam env:" + env + ",url:" + url);
        qr.query("select 1", resultSet -> null);
        return basicDataSource;
    }

}
//10.247.0.76:3306
//mmp_ops
//'USER': 'yh_mmpops',
//'PASSWORD': 'YH#MMPops123',