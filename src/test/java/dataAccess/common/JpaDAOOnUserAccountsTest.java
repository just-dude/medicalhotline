package dataAccess.common;

import common.DBTestCase;
import common.beanFactory.BeanFactoryProvider;
import domain.userAccounts.AccountInfo;
import domain.userAccounts.Profile;
import domain.userAccounts.UserAccount;
import domain.userAccounts.UserGroup;
import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.InputStream;
import java.util.List;

/**
 * Created by suslovai on 11.09.2017.
 */
public class JpaDAOOnUserAccountsTest extends DBTestCase {


    public JpaDAOOnUserAccountsTest() {
        super("JpaDAOOnUserAccountsTest");
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(getDataSetAsInputStream("testDataSet/users/UsersSetupDataset.xml"));
    }

    protected Repository<UserAccount, Long> getDAO() {
        return (Repository<UserAccount, Long>) BeanFactoryProvider.getBeanFactory().getBean("userAccountsRepository");
    }

    protected PlatformTransactionManager getTransactionManager() {
        return (PlatformTransactionManager) BeanFactoryProvider.getBeanFactory().getBean("transactionManager");
    }

    @Test
    public void testInsert() throws Exception {
        Repository<UserAccount, Long> jpaRepository = getDAO();
        PlatformTransactionManager tm = getTransactionManager();
        UserAccount newUserAccount = new UserAccount(new AccountInfo("newUserLogin", "hashedPass", "")
                , new Profile("newUserName", "newUserSurname", null,"email@email.ru"),
                UserGroup.Operator
        );
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus ts = tm.getTransaction(def);
        UserAccount userAccountInPersistenceContext = null;

        userAccountInPersistenceContext = jpaRepository.save(newUserAccount);
        jpaRepository.flush();
        tm.commit(ts);

        assertNull(newUserAccount.getId());
        assertNotNull(userAccountInPersistenceContext.getId());

        IDataSet databaseDataSet = getConnection().createDataSet(new String[]{"test_medical_hotline.UserAccounts"});
        ITable actualTable = databaseDataSet.getTable("test_medical_hotline.UserAccounts");
        ITable filteredActualTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"id", "hashedPassword", "salt","patronymic"});

        InputStream expectedDataSetInputStream = getDataSetAsInputStream("testDataSet/users/AfterInsertUserAccountExpectedDataset.xml");
        ReplacementDataSet expectedDataSet = new ReplacementDataSet(new FlatXmlDataSetBuilder().build(expectedDataSetInputStream));
        expectedDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = expectedDataSet.getTable("test_medical_hotline.UserAccounts");

        Assertion.assertEquals(expectedTable, filteredActualTable);

    }

    @Test
    public void testUpdate() throws Exception {
        Repository<UserAccount, Long> jpaRepository = getDAO();
        PlatformTransactionManager tm = getTransactionManager();
        TransactionDefinition def = new DefaultTransactionDefinition();

        UserAccount userAccount = jpaRepository.findOne(1L);
        userAccount.getProfile().setName("newName");
        userAccount.getAccountInfo().setLogin("login1");
        userAccount.setUserGroup(UserGroup.Operator);
        TransactionStatus ts = tm.getTransaction(def);
        jpaRepository.save(userAccount);
        jpaRepository.flush();
        tm.commit(ts);

        IDataSet databaseDataSet = getConnection().createDataSet(new String[]{"test_medical_hotline.UserAccounts"});
        ITable actualTable = databaseDataSet.getTable("test_medical_hotline.UserAccounts");
        ITable filteredActualTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"id", "hashedPassword", "salt","patronymic"});

        InputStream expectedDataSetInputStream = getDataSetAsInputStream("testDataSet/users/AfterUpdateUserAccountExpectedDataset.xml");
        ReplacementDataSet expectedDataSet = new ReplacementDataSet(new FlatXmlDataSetBuilder().build(expectedDataSetInputStream));
        expectedDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = expectedDataSet.getTable("test_medical_hotline.UserAccounts");

        Assertion.assertEquals(expectedTable, filteredActualTable);
    }

    @Test
    public void testDeleteById() throws Exception {
        Repository<UserAccount, Long> jpaRepository = getDAO();
        PlatformTransactionManager tm = getTransactionManager();
        TransactionDefinition def = new DefaultTransactionDefinition();

        TransactionStatus ts = tm.getTransaction(def);
        jpaRepository.deleteAndFlush(1L);
        jpaRepository.flush();
        tm.commit(ts);

        IDataSet databaseDataSet = getConnection().createDataSet(new String[]{"test_medical_hotline.UserAccounts"});
        ITable actualTable = databaseDataSet.getTable("test_medical_hotline.UserAccounts");
        ITable filteredActualTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"id", "hashedPassword", "salt","patronymic"});

        InputStream expectedDataSetInputStream = getDataSetAsInputStream("testDataSet/users/AfterPhysicalyDeleteUserAccountExpectedDataset.xml");
        ReplacementDataSet expectedDataSet = new ReplacementDataSet(new FlatXmlDataSetBuilder().build(expectedDataSetInputStream));
        expectedDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = expectedDataSet.getTable("test_medical_hotline.UserAccounts");

        Assertion.assertEquals(expectedTable, filteredActualTable);

    }

    @Test
    public void testDelete() throws Exception {
        Repository<UserAccount, Long> jpaRepository = getDAO();
        PlatformTransactionManager tm = getTransactionManager();
        TransactionDefinition def = new DefaultTransactionDefinition();

        TransactionStatus ts = tm.getTransaction(def);
        UserAccount userAccount = jpaRepository.findOne(1L);
        jpaRepository.flush();
        tm.commit(ts);

        ts = tm.getTransaction(def);
        jpaRepository.deleteAndFlush(userAccount);
        jpaRepository.flush();
        tm.commit(ts);

        IDataSet databaseDataSet = getConnection().createDataSet(new String[]{"test_medical_hotline.UserAccounts"});
        ITable actualTable = databaseDataSet.getTable("test_medical_hotline.UserAccounts");
        ITable filteredActualTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"id", "hashedPassword", "salt","patronymic"});

        InputStream expectedDataSetInputStream = getDataSetAsInputStream("testDataSet/users/AfterPhysicalyDeleteUserAccountExpectedDataset.xml");
        ReplacementDataSet expectedDataSet = new ReplacementDataSet(new FlatXmlDataSetBuilder().build(expectedDataSetInputStream));
        expectedDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = expectedDataSet.getTable("test_medical_hotline.UserAccounts");

        Assertion.assertEquals(expectedTable, filteredActualTable);

    }

    @Test
    public void testDeleteAll() throws Exception {
        Repository<UserAccount, Long> jpaRepository = getDAO();
        PlatformTransactionManager tm = getTransactionManager();
        TransactionDefinition def = new DefaultTransactionDefinition();

        TransactionStatus ts = tm.getTransaction(def);
        jpaRepository.deleteAll();
        jpaRepository.flush();
        tm.commit(ts);

        IDataSet databaseDataSet = getConnection().createDataSet(new String[]{"test_medical_hotline.UserAccounts"});
        ITable actualTable = databaseDataSet.getTable("test_medical_hotline.UserAccounts");
        ITable filteredActualTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"id", "hashedPassword", "salt"});
        assertEquals(0, filteredActualTable.getRowCount());
    }

    @Test
    public void testGetOne() throws Exception {
        Repository<UserAccount, Long> jpaRepository = (Repository<UserAccount, Long>) BeanFactoryProvider.getBeanFactory().getBean("userAccountsRepository");
        UserAccount account = jpaRepository.findOne(1L);
        assertEquals(1L, (long) account.getId());
        assertEquals("name", account.getProfile().getName());
        assertEquals("login1", account.getAccountInfo().getLogin());
    }

    @Test
    public void testCount() throws Exception {
        Repository<UserAccount, Long> jpaRepository = (Repository<UserAccount, Long>) BeanFactoryProvider.getBeanFactory().getBean("userAccountsRepository");
        long countOfAccounts = jpaRepository.count();
        assertEquals(3L, countOfAccounts);
    }

    @Test
    public void testFindAll() throws Exception {
        Repository<UserAccount, Long> jpaRepository = (Repository<UserAccount, Long>) BeanFactoryProvider.getBeanFactory().getBean("userAccountsRepository");
        List<UserAccount> accountsList = jpaRepository.findAll();
        assertEquals(3L, accountsList.size());
        assertEquals(1L, (long) accountsList.get(0).getId());
        assertEquals(2L, (long) accountsList.get(1).getId());
        assertEquals(3L, (long) accountsList.get(2).getId());
    }

    @Test
    public void testFindAllWWithPageRequest() throws Exception {
        Repository<UserAccount, Long> jpaRepository = (Repository<UserAccount, Long>) BeanFactoryProvider.getBeanFactory().getBean("userAccountsRepository");
        Page<UserAccount> accountsPage = jpaRepository.findAll(new PageRequest(0, 2, Sort.Direction.DESC, "id"));
        List<UserAccount> userAccountList = accountsPage.getContent();
        assertEquals(3L, accountsPage.getTotalElements());
        assertEquals(2L, accountsPage.getTotalPages());
        assertEquals(2L, accountsPage.getSize());
        assertEquals(3L, (long) userAccountList.get(0).getId());
        assertEquals(2L, (long) userAccountList.get(1).getId());
    }
}