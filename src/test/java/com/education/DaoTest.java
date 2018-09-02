package com.education;

import com.education.dao.ImageDaoImpl;
import com.education.dao.RoleDaoImpl;
import com.education.dao.TeamDaoImpl;
import com.education.dao.UserDaoImpl;
import com.education.utils.JdbcUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.sql.DataSource;
import java.sql.SQLException;

import static com.education.utils.JdbcUtils.initDB;

@RunWith(JUnit4.class)
public class DaoTest {
    private static ImageDaoImpl imageDaoImpl;
    private static RoleDaoImpl roleDaoImpl;
    private static TeamDaoImpl teamDaoImpl;
    private static UserDaoImpl userDaoImpl;

    @BeforeClass
    public static void init() throws SQLException {
        DataSource postgresDataSource = JdbcUtils.createDefaultPostgresDataSource();
        initDB(postgresDataSource);
        initDao(postgresDataSource);
    }


    @Test
    public void test() {

    }

    private static void initDao(DataSource dataSource) {
        imageDaoImpl = new ImageDaoImpl(dataSource);
        roleDaoImpl = new RoleDaoImpl(dataSource);
        teamDaoImpl = new TeamDaoImpl(dataSource);
        userDaoImpl = new UserDaoImpl(dataSource);
    }

}
