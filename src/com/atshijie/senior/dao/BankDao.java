package com.atshijie.senior.dao;

public interface BankDao {

    int addMoney(Integer id, Integer money);

    int subMoney(Integer id, Integer money);
}
