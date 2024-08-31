package com.atshijie.advanced.pool;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class DruidTest {
    @Test
    public void testHardCodeDruid() throws Exception{
        DruidDataSource ds = new DruidDataSource();

        //necessary
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://127.0.0.1:3306/atguigu");
        ds.setUsername("root");
        ds.setPassword("12345678");

        //not necessary
        ds.setInitialSize(10);
        ds.setMaxActive(20);

        DruidPooledConnection connection = ds.getConnection();
        System.out.println(connection);

        //CRUD

        //return connection to Druid
        connection.close();
    }

    @Test
    public void testResourcesDruid() throws Exception{
        Properties properties = new Properties();

        InputStream inputStream = DruidTest.class.getClassLoader().getResourceAsStream("db.properties");
        properties.load(inputStream);

        DataSource ds = DruidDataSourceFactory.createDataSource(properties);

        Connection connection = ds.getConnection();
        System.out.println(connection);

        connection.close();
    }
}
