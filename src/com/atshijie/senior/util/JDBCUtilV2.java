package com.atshijie.senior.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class JDBCUtilV2 {
    private static DataSource dataSource;
    private static ThreadLocal<Connection> threadLocal=new ThreadLocal<>();

    //create pool object and assign to the datasource
    //maintain a threadlocal object
    static {
        try {
            Properties properties = new Properties();
            InputStream inputStream = JDBCUtil.class.getClassLoader().getResourceAsStream("db.properties");
            properties.load(inputStream);

            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        try {
            Connection connection = threadLocal.get();

            if(connection == null) {
                connection = dataSource.getConnection();
                threadLocal.set(connection);
            }
            return connection;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void release() {
        try {
            Connection connection = threadLocal.get();

            if(connection != null) {
                threadLocal.remove();
                connection.setAutoCommit(true);
                connection.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
