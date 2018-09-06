package common;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import common.beanFactory.BeanFactoryProvider;
import domain.security.SecuritySubjectUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import javax.sql.DataSource;
import java.io.InputStream;

//@Ignore
/*@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
       DirtiesContextTestExecutionListener.class,
      TransactionalTestExecutionListener.class,
       DbUnitTestExecutionListener.class })*/
@RunWith(BlockJUnit4ClassRunner.class)
@ContextConfiguration(classes = {javaConfig.spring.test.ApplicationConfig.class})
public abstract class DBTestCase extends DataSourceBasedDBTestCase {

    @ClassRule
    public static final SpringClassRule SCR = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();


    public DBTestCase(String name) {
        super(name);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp(); //To change body of generated methods, choose Tools | Templates.
        SecurityUtils.setSecurityManager((SecurityManager) BeanFactoryProvider.getBeanFactory().getBean("securityManager"));
        SecuritySubjectUtils.login("admin", "123");
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void setUpDatabaseConfig(DatabaseConfig config) {
        config.setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
        config.setFeature(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, true);
        config.setFeature(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, false);
    }

    @Override
    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.CLEAN_INSERT;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.DELETE_ALL;
    }

    @Override
    protected DataSource getDataSource() {
        return (DataSource) BeanFactoryProvider.getBeanFactory().getBean("dataSource");
    }

    protected InputStream getDataSetAsInputStream(String classPathRelativeDataSetPath) {
        return (InputStream) this.getClass().getClassLoader().getResourceAsStream(classPathRelativeDataSetPath);
    }
}
