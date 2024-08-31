package com.atshijie.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCQuick {
    public static void main(String[] args) throws Exception {
//        DriverManager.registerDriver(new Driver());
//        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/atguigu";
        String user = "root";
        String password = "12345678";
        Connection connection = DriverManager.getConnection(url, user, password);
//        System.out.println(connection);

        Statement statement = connection.createStatement();
        String sql = "select emp_id,emp_name,emp_salary,emp_age from t_emp";
        ResultSet resultSet = statement.executeQuery(sql);

        while(resultSet.next()) {
            int empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            double empSalary = resultSet.getDouble("emp_salary");
            int empAge = resultSet.getInt("emp_age");
            System.out.println(empId + "\t" + empName + "\t" + empSalary + "\t" + empAge);
        }

        resultSet.close();
        statement.close();
        connection.close();
    }
}
