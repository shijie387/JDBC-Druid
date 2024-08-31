package com.atshijie.senior.dao.impl;

import com.atshijie.senior.dao.BaseDao;
import com.atshijie.senior.dao.EmployeeDao;
import com.atshijie.senior.pojo.Employee;

import java.util.List;

public class EmployeeDaoImpl extends BaseDao implements EmployeeDao {
    @Override
    public List<Employee> selectAll() {
        try {
            String sql = "select emp_id empId,emp_name empName,emp_salary empSalary,emp_age empAge from t_emp";
            return executeQuery(Employee.class,sql,null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Employee selectById(Integer id){
        try {
            String sql = "select emp_id empId,emp_name empName,emp_salary empSalary,emp_age empAge from t_emp where emp_id = ?";
            return executeQueryBean(Employee.class,sql,id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int insert(Employee employee) {
        try {
            String sql = "insert into t_emp(emp_name,emp_salary,emp_age) values(?,?,?)";
            return executeUpdate(sql,employee.getEmpName(),employee.getEmpSalary(),employee.getEmpAge());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Employee employee) {
        try {
            String sql = "update t_emp set emp_salary = ? where emp_id = ?";
            return executeUpdate(sql,employee.getEmpSalary(),employee.getEmpId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(Integer empId) {
        try {
            String sql = "delete from t_emp where emp_id = ?";
            return executeUpdate(sql,empId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
