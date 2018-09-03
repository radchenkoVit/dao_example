package com.education;

import com.education.dao.ImageDaoImpl;
import com.education.dao.RoleDaoImpl;
import com.education.dao.TeamDaoImpl;
import com.education.dao.UserDaoImpl;
import com.education.entity.Role;
import com.education.utils.JdbcUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.sql.DataSource;
import java.sql.SQLException;

import static com.education.utils.JdbcUtils.initDB;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class DaoTest {
    private static ImageDaoImpl imageDao;
    private static RoleDaoImpl roleDao;
    private static TeamDaoImpl teamDao;
    private static UserDaoImpl userDao;

    @BeforeClass
    public static void init() throws SQLException {
        DataSource postgresDataSource = JdbcUtils.createDefaultPostgresDataSource();
        initDB(postgresDataSource);
        initDao(postgresDataSource);
    }


    @Test
    public void validateSaveRoleDao() {
        Role role = generateTestRole();
        roleDao.save(role);
        assertThat("Role is should be 1", role.getId(), equalTo(1L));
    }

    private static void initDao(DataSource dataSource) {
        imageDao = new ImageDaoImpl(dataSource);
        roleDao = new RoleDaoImpl(dataSource);
        teamDao = new TeamDaoImpl(dataSource);
        userDao = new UserDaoImpl(dataSource);
    }

    private static Role generateTestRole() {
        return Role.builder()
                .name("test name")
                .build();
    }

}
