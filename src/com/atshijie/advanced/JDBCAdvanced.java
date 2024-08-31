package com.atshijie.advanced;

import com.atshijie.advanced.pojo.Employee;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;

public class JDBCAdvanced {
    @Test
    public void testORM() throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/atguigu", "root", "12345678");

        PreparedStatement preparedStatement = connection.prepareStatement("select emp_id,emp_name,emp_salary,emp_age from t_emp where emp_id=?");
        preparedStatement.setInt(1, 1);
        ResultSet resultSet = preparedStatement.executeQuery();

        Employee employee = null;
        while (resultSet.next()){
            employee = new Employee();
            int empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            double empSalary = resultSet.getDouble("emp_salary");
            int empAge = resultSet.getInt("emp_age");
            employee .setEmpId(empId);
            employee.setEmpName(empName);
            employee.setEmpSalary(empSalary);
            employee.setEmpAge(empAge);
        }

        System.out.println(employee);

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testORMList() throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/atguigu", "root", "12345678");

        PreparedStatement preparedStatement = connection.prepareStatement("select emp_id,emp_name,emp_salary,emp_age from t_emp");
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<Employee> employees = new ArrayList();
        while (resultSet.next()){
            int empId = resultSet.getInt("emp_id");
            String empName = resultSet.getString("emp_name");
            double empSalary = resultSet.getDouble("emp_salary");
            int empAge = resultSet.getInt("emp_age");

            employees.add(new Employee(empId,empName,empSalary,empAge));
        }
        employees.forEach(System.out::println);

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testReturnPK() throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "12345678");

        Statement statement = connection.createStatement();

        String sql = "insert into t_emp(emp_name,emp_salary,emp_age) values(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        // return the constant of the new primary key

        Employee employee = new Employee(null,"jack",123.45,29);
        preparedStatement.setString(1,employee.getEmpName());
        preparedStatement.setDouble(2,employee.getEmpSalary());
        preparedStatement.setInt(3,employee.getEmpAge());

        int result = preparedStatement.executeUpdate();
        ResultSet resultSet = null;
        if (result > 0) {
            System.out.println("success");

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            while (generatedKeys.next()) {
                int empId = generatedKeys.getInt(1);
                employee.setEmpId(empId);
            }
            System.out.println(employee);
        }else{
            System.out.println("failed");
        }

        if(resultSet != null){
            resultSet.close();
        }
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testMoreInsert() throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "12345678");

        Statement statement = connection.createStatement();

        String sql = "insert into t_emp(emp_name,emp_salary,emp_age) values(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            preparedStatement.setString(1, "marry" + i);
            preparedStatement.setDouble(2, 100 + i);
            preparedStatement.setInt(3, 20 + i);

            preparedStatement.executeUpdate();
        }
        long end = System.currentTimeMillis();

        System.out.println("time consumed: "+(end-start));

        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testBatch() throws Exception{
        //allowed batch manipulation
        //sql: values
        //addBatch
        //executeBatch()

        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu?rewriteBatchedStatements=true", "root", "12345678");

        Statement statement = connection.createStatement();

        String sql = "insert into t_emp(emp_name,emp_salary,emp_age) values(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            preparedStatement.setString(1, "marry" + i);
            preparedStatement.setDouble(2, 100 + i);
            preparedStatement.setInt(3, 20 + i);

            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();

        long end = System.currentTimeMillis();

        System.out.println("time consumed: "+(end-start));

        preparedStatement.close();
        connection.close();
    }
}
