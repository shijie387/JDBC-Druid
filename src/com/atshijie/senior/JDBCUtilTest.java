package com.atshijie.senior;

import com.atshijie.senior.dao.BankDao;
import com.atshijie.senior.dao.EmployeeDao;
import com.atshijie.senior.dao.impl.BankDaoImpl;
import com.atshijie.senior.dao.impl.EmployeeDaoImpl;
import com.atshijie.senior.util.JDBCUtil;
import com.atshijie.senior.util.JDBCUtilV2;
import org.junit.Test;

import java.sql.Connection;

public class JDBCUtilTest {
    @Test
    public void testGetConn() throws Exception{
        Connection connection = JDBCUtil.getConnection();
        System.out.println(connection);

        JDBCUtil.release(connection);
    }

    @Test
    public void testJDBCUtil() throws Exception{
        Connection connection1 = JDBCUtil.getConnection();
        Connection connection2 = JDBCUtil.getConnection();
        Connection connection3 = JDBCUtil.getConnection();

        System.out.println(connection1);
        System.out.println(connection2);
        System.out.println(connection3);
    }
    @Test
    public void testJDBCUtilV2() throws Exception{
        Connection connection1 = JDBCUtilV2.getConnection();
        Connection connection2 = JDBCUtilV2.getConnection();
        Connection connection3 = JDBCUtilV2.getConnection();

        System.out.println(connection1);
        System.out.println(connection2);
        System.out.println(connection3);

    }

    @Test
    public void testEmployeeDao() throws Exception{
        EmployeeDao employeeDao = new EmployeeDaoImpl();
        //querying all data
        /*List<Employee> employees = employeeDao.selectAll();

        for (Employee employee : employees) {
            System.out.println(employee);
        }*/

        //querying a specific employee's info
        /*Employee employee = employeeDao.selectById(2);
        System.out.println(employee);*/

        //insert
        /*Employee employee = new Employee(null,"tom",300.65,38);
        int insert = employeeDao.insert(employee);
        System.out.println(insert);*/

        //update
        /*Employee employee = new Employee(310,"tom",656.65,38);
        int update = employeeDao.update(employee);
        System.out.println(update);*/

        int delete = employeeDao.delete(310);
        System.out.println(delete);
    }

    @Test
    public void testTransaction(){
        BankDao bankDao = new BankDaoImpl();

        Connection connection = null;
        try {
            connection = JDBCUtilV2.getConnection();
            connection.setAutoCommit(false);

            bankDao.subMoney(1,100);
            bankDao.addMoney(2,100);
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }finally {
            JDBCUtilV2.release();
        }

    }
}
