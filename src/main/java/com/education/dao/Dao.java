package com.education.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> findOne(long id);
    List<T> findAll();
    T save(T entity);
    void update(T entity);
    void delete(T entity);
}
