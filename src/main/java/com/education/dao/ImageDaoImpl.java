package com.education.dao;

import javax.sql.DataSource;

public class ImageDaoImpl implements ImageDao {
    private DataSource dataSource;

    public ImageDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
