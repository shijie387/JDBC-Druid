package com.atshijie.base;

import org.junit.Test;

import java.sql.*;

public class JDBCOperation {
    //CRUD: create retrive update delete
    @Test
    public void testQuerySingleRowAndCol() throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "12345678");

        PreparedStatement preparedStatement = connection.prepareStatement("select count(*) count from t_emp");

        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            System.out.println(resultSet.getInt("count"));
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testQuerySingleRow() throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "12345678");

        PreparedStatement preparedStatement = connection.prepareStatement("select emp_id,emp_name,emp_salary,emp_age from t_emp where emp_id=?");
        preparedStatement.setInt(1, 5);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            double empSalary = resultSet.getDouble("emp_salary");
            int empAge = resultSet.getInt("emp_age");
            System.out.println(empId + "\t" + empName + "\t" + empSalary + "\t" + empAge);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testQueryMoreRow() throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "12345678");

        PreparedStatement preparedStatement = connection.prepareStatement("select emp_id,emp_name,emp_salary,emp_age from t_emp where emp_age > ?");
        preparedStatement.setInt(1, 25);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            double empSalary = resultSet.getDouble("emp_salary");
            int empAge = resultSet.getInt("emp_age");
            System.out.println(empId + "\t" + empName + "\t" + empSalary + "\t" + empAge);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testInsert() throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "12345678");

        Statement statement = connection.createStatement();

        PreparedStatement preparedStatement = connection.prepareStatement("insert into t_emp(emp_name,emp_salary,emp_age) values(?,?,?)");
        preparedStatement.setString(1, "rose");
        preparedStatement.setDouble(2, 345.67);
        preparedStatement.setInt(3, 28);

        int result = preparedStatement.executeUpdate();
        if (result > 0) {
            System.out.println("success");
        }else{
            System.out.println("failed");
        }

        preparedStatement.close();
    }

    @Test
    public void testUpdate() throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "12345678");

        Statement statement = connection.createStatement();

        PreparedStatement preparedStatement = connection.prepareStatement("update t_emp set emp_salary = ? where emp_id = ?");
        preparedStatement.setDouble(1, 888.88);
        preparedStatement.setInt(2, 6);

        int result = preparedStatement.executeUpdate();
        if (result > 0) {
            System.out.println("success");
        }else{
            System.out.println("failed");
        }

        preparedStatement.close();
    }

    @Test
    public void testDelete() throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "12345678");

        Statement statement = connection.createStatement();

        PreparedStatement preparedStatement = connection.prepareStatement("delete from t_emp where emp_id = ?");
        preparedStatement.setInt(1, 6);

        int result = preparedStatement.executeUpdate();
        if (result > 0) {
            System.out.println("success");
        }else{
            System.out.println("failed");
        }

        preparedStatement.close();
    }
}
