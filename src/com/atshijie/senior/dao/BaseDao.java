package com.atshijie.senior.dao;

import com.atshijie.senior.util.JDBCUtilV2;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class BaseDao {

    //general approach for adding,deleting,updating
    public int executeUpdate(String sql, Object... param) throws Exception {
        Connection connection = JDBCUtilV2.getConnection();

        PreparedStatement ps = connection.prepareStatement(sql);

        if (param != null && param.length > 0) {
            for (int i = 0; i < param.length; i++) {
                ps.setObject(i+1,param[i]);
            }
        }
        int row = ps.executeUpdate();

        ps.close();
        if(connection.getAutoCommit()){
            JDBCUtilV2.release();
        }

        return row;
    }

    //general approach for querying
    public <T> List<T> executeQuery(Class<T> clazz, String sql, Object... param) throws Exception {
        Connection connection = JDBCUtilV2.getConnection();

        PreparedStatement ps = connection.prepareStatement(sql);

        if (param != null && param.length > 0) {
            for (int i = 0; i < param.length; i++) {
                ps.setObject(i+1,param[i]);
            }
        }
        ResultSet resultSet = ps.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();

        int columnCount = metaData.getColumnCount();
        List<T> list = new ArrayList();

        while (resultSet.next()) {
            T t = clazz.newInstance();
            for (int i = 1; i <= columnCount; i++) {
                Object value = resultSet.getObject(i);
                String fieldName = metaData.getColumnLabel(i);

                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(t, value);
            }
            list.add(t);
        }

        ps.close();
        if(connection.getAutoCommit()){
            JDBCUtilV2.release();
        }
        JDBCUtilV2.release();
        return list;
    }

    public <T> T executeQueryBean(Class<T> clazz, String sql, Object... param) throws Exception {
        List<T> list = this.executeQuery(clazz, sql, param);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
