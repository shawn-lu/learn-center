package com.shawn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Util {

    public static void main(String[] args) throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String url = "jdbc:mysql://mycat-warehouse-sit.mid.io:8066/warehouse_task_center?characterEncoding=utf-8&useSSL=false";
            String username = "yh_dev";
            String password = "X520inzWrG7skGTa";
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
            String sql = " /**mycat:sql=select * from t_fulfil_task where id =11396*/\n" +
                    "SELECT\n" +
                    "\t*\n" +
                    "FROM\n" +
                    "\tt_fulfil_task_group \n" +
                    "WHERE\n" +
                    "\ttenant = 'yonghui' \n" +
                    "\tAND warehouse_id = '9171' \n" +
                    "\tAND work_node_id IN ( '9171' ) \n" +
                    "\tAND group_status = 'wait_dispatch' \n" +
                    "\tAND group_type IN ( 'after_sales_picking', 'exchange_picking' ) \n" +
                    "\tLIMIT 0,30";
            System.out.println(sql);
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println(resultSet.getObject(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != connection) {
                connection.close();

            }
            if (null != statement) {
                statement.close();

            }
            if (null != resultSet) {
                resultSet.close();

            }
        }
    }
}
