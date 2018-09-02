package com.education.dao;

import javax.sql.DataSource;

public class TeamDaoImpl implements TeamDao {
    private DataSource dataSource;

    public TeamDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
