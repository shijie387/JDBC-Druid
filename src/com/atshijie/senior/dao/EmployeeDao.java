package com.atshijie.senior.dao;

import com.atshijie.senior.pojo.Employee;

import java.util.List;

public interface EmployeeDao {
    List<Employee> selectAll();

    Employee selectById(Integer id) throws Exception;

    int insert(Employee employee);

    int update(Employee employee);

    int delete(Integer id) throws Exception;
}
