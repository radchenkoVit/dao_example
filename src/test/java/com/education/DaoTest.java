package com.education;

import com.education.dao.ImageDaoImpl;
import com.education.dao.RoleDaoImpl;
import com.education.dao.TeamDaoImpl;
import com.education.dao.UserDaoImpl;
import com.education.entity.Role;
import com.education.utils.JdbcUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static com.education.utils.JdbcUtils.initDB;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class DaoTest {
    private static DataSource postgresDataSource;
    private static ImageDaoImpl imageDao;
    private static RoleDaoImpl roleDao;
    private static TeamDaoImpl teamDao;
    private static UserDaoImpl userDao;

    @BeforeClass
    public static void init() throws SQLException {
        postgresDataSource = JdbcUtils.createDefaultPostgresDataSource();

    }

    @Before
    public void beforeEach() throws SQLException {
        initDao(postgresDataSource);
        initDB(postgresDataSource);
    }


    @Test
    public void validateSaveRoleDao() {
        Role role = generateTestRole();
        roleDao.save(role);
        assertThat("Role is should be 1", role.getId(), equalTo(1L));
    }

    @Test
    public void validateRemoveRoleDao() {
        Role role = generateTestRole();
        roleDao.save(role);
        roleDao.delete(role);

        List<Role> resultList = roleDao.findAll();
        assertThat("List should be empty", resultList.isEmpty());
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
