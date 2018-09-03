package com.education.dao;

import com.education.entity.Team;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class TeamDaoImpl implements TeamDao {
    private DataSource dataSource;

    public TeamDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Team> findOne(long id) {
        return Optional.empty();
    }

    @Override
    public List<Team> findAll() {
        return null;
    }

    @Override
    public Team save(Team entity) {
        return null;
    }

    @Override
    public void update(Team entity) {

    }

    @Override
    public void delete(Team entity) {

    }
}
