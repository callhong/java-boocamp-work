package com.lh.work.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @Author: nanCheng
 * @Date: 2021/09/06
 */
public class HikariDemo {


    public static void main(String[] args) {
        HikariDataSource dataSource = getDataSource();
        add(dataSource);


        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }

    }

    private static HikariDataSource getDataSource() {

        HikariConfig config = new HikariConfig();

        config.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/mydb?useUnicode=true&characterEncoding=utf8&useSSL=false");
        config.setUsername("root");
        config.setPassword("123456");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        return new HikariDataSource(config);
    }

    /* 简单的新增 */
    public static int add(HikariDataSource dataSource) {
        int result = -1;
        try {
            Connection connection = dataSource.getConnection();

            Statement statement = connection.createStatement();

            statement.execute("insert user values(NULL,'9','9',1);", Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet != null) {
                if (resultSet.next()) {
                    result = resultSet.getInt(1);
                }
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
