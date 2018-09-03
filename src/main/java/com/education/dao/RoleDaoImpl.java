package com.education.dao;

import com.education.entity.Role;
import com.education.exception.DaoOperationException;
import com.education.exception.NoResultFoundException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.lang.String.format;

public class RoleDaoImpl implements RoleDao {
    private DataSource dataSource;

    private static final String SELECT_BY_ID_QUERY = "select * from roles where id = ?";
    private static final String SELECT_ALL_QUERY = "select * from roles";
    private static final String UPDATE_QUERY = "insert into roles (name) values (?) where id = ?";
    private static final String INSERT_QUERY = "insert into roles (name) values (?)";
    private static final String REMOVE_QUERY = "delete from roles where id = ?";

    public RoleDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Role> findOne(long id) {
        try(Connection connection = dataSource.getConnection()) {
            return findOne(connection, id);
        } catch (SQLException e) {
            throw new RuntimeException(format("No role find with id: %s", id), e);
        }
    }

    private Optional<Role> findOne(Connection connection, long id) throws SQLException {
        ResultSet resultSet = executeFindOneStatement(connection, id);
        return validateResult(resultSet);
    }

    private ResultSet executeFindOneStatement(Connection connection, long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY);
        statement.setLong(1, id);
        return statement.executeQuery();
    }

    private Optional<Role> validateResult(ResultSet resultSet) throws SQLException {
        boolean isResultExist = resultSet.next();
        if (isResultExist) {
            return Optional.ofNullable(mapToRole(resultSet));
        }

        throw new NoResultFoundException("No Role found");
    }

    private Role mapToRole(ResultSet resultSet) throws SQLException {
        return Role.builder()
                .id(resultSet.getLong(1))
                .name(resultSet.getString(2))
                .build();
    }

    @Override
    public List<Role> findAll() {
        try(Connection connection = dataSource.getConnection()) {
            return findAll(connection);
        } catch (SQLException e) {
            throw new DaoOperationException("Failed to find all roles", e);
        }
    }

    private List<Role> findAll(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY);
        ResultSet resultSet = statement.executeQuery();
        return mapResults(resultSet);
    }

    private List<Role> mapResults(ResultSet resultSet) throws SQLException {
        List<Role> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(mapToRole(resultSet));
        }
        return list;
    }

    @Override
    public Role save(Role role) {
        try(Connection connection = dataSource.getConnection()) {
            save(connection, role);
        } catch (SQLException e) {
            throw new DaoOperationException(format("Failed to save role: %s", role), e);
        }

        return role;
    }

    private void save(Connection connection, Role role) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setString(1, role.getName());
        int rowAffected = statement.executeUpdate();
        if (rowAffected == 0) {
            throw new DaoOperationException("Account was not created");
        }
        fetchIds(statement, role);
    }

    private void fetchIds(PreparedStatement statement, Role role) throws SQLException {
        ResultSet resultSet = statement.getGeneratedKeys();
        if (resultSet.next()) {
            Long id = resultSet.getLong(1);
            role.setId(id);
        } else {
            throw new DaoOperationException("Can not obtain an account ID");
        }
    }

    @Override
    public void update(Role role) {
        Objects.requireNonNull(role);
        try(Connection connection = dataSource.getConnection()) {
            update(connection, role);
        } catch (SQLException e) {
            throw new DaoOperationException(format("Failed to update role with id: %s", role), e);
        }
    }

    private void update(Connection connection, Role role) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
        statement.setString(1, role.getName());
        statement.executeUpdate();
        validateUpdateQuery(statement, format("No roles found with id: %s", role.getId()));
    }

    @Override
    public void delete(Role role) {
        Objects.requireNonNull(role);
        try(Connection connection = dataSource.getConnection()) {
            delete(connection, role);
        } catch (SQLException e) {
            throw new DaoOperationException(format("No roles found with id: %s", role.getId()), e);
        }
    }

    private void delete(Connection connection, Role role) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(REMOVE_QUERY);
        statement.setLong(1, role.getId());
        validateUpdateQuery(statement, format("No roles found with id: %s", role.getId()));
    }

    private void validateUpdateQuery(PreparedStatement statement, String errorMessage) throws SQLException {
        int rowAffected = statement.executeUpdate();
        if (rowAffected == 0) {
            throw new DaoOperationException(errorMessage);
        }
    }
}
