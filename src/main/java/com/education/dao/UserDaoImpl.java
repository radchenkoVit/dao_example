package com.education.dao;

import javax.sql.DataSource;

public class UserDaoImpl implements UserDao {
    private DataSource dataSource;

    public UserDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
