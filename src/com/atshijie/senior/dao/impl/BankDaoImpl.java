package com.atshijie.senior.dao.impl;

import com.atshijie.senior.dao.BankDao;
import com.atshijie.senior.dao.BaseDao;

public class BankDaoImpl extends BaseDao implements BankDao {

    @Override
    public int addMoney(Integer id, Integer money) {
        try {
            String sql = "Update t_bank set money=money+? where id=?";
            return executeUpdate(sql, money, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int subMoney(Integer id, Integer money) {
        try {
            String sql = "Update t_bank set money=money-? where id=?";
            return executeUpdate(sql,money,id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
