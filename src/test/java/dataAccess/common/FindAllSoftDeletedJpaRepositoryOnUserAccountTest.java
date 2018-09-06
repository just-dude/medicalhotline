package dataAccess.common;

import common.DBTestCase;
import common.beanFactory.BeanFactoryProvider;
import dataAccess.common.exception.EntityWithSuchIdIsSoftDeletedDataAccessException;
import domain.userAccounts.UserAccount;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;

import java.util.List;

/**
 * Created by Администратор on 30.10.2017.
 */
public class FindAllSoftDeletedJpaRepositoryOnUserAccountTest extends DBTestCase{

    public FindAllSoftDeletedJpaRepositoryOnUserAccountTest() {
        super("FindAllSoftDeletedJpaRepositoryOnUserAccountTest");
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(getDataSetAsInputStream("testDataSet/findAllSoftDeletedJpaRepositoryOnUserAccounts/UserAccountsSetupDataset.xml"));
    }

    protected Repository<UserAccount, Long> getFindAllWithoutSoftDeletedDAO() {
        EntityManager em = (EntityManager) BeanFactoryProvider.getBeanFactory().getBean("entityManager");
        return (Repository<UserAccount, Long>) new SoftDeleteModedJpaRepository(em,UserAccount.class, SoftDeleteModedJpaRepository.FindMode.WithoutSoftDeleted);
    }

    protected Repository<UserAccount, Long> getFindAllOnlySoftDeletedDAO() {
        EntityManager em = (EntityManager) BeanFactoryProvider.getBeanFactory().getBean("entityManager");
        return (Repository<UserAccount, Long>) new SoftDeleteModedJpaRepository(em,UserAccount.class, SoftDeleteModedJpaRepository.FindMode.OnlySoftDeleted);
    }

    @Test
    public void testGetOneWithoutSoftDeleted() throws Exception {
        try {
            UserAccount account = getFindAllWithoutSoftDeletedDAO().findOne(1L);
            fail("Soft deleted entity must not be find. Exception was expected");
        } catch (EntityWithSuchIdIsSoftDeletedDataAccessException e){ }
    }

    @Test
    public void testGetOneOnlySoftDeleted() throws Exception {
        UserAccount account = getFindAllOnlySoftDeletedDAO().findOne(1L);
        assertEquals(1L, (long) account.getId());
        assertEquals("name", account.getProfile().getName());
        assertEquals("login1", account.getAccountInfo().getLogin());
    }

    @Test
    public void testCountWithoutSoftDeleted() throws Exception {
        long countOfAccounts = getFindAllWithoutSoftDeletedDAO().count();
        assertEquals(2L, countOfAccounts);
    }

    @Test
    public void testCountOnlySoftDeleted() throws Exception {
        long countOfAccounts = getFindAllOnlySoftDeletedDAO().count();
        assertEquals(1L, countOfAccounts);
    }

    @Test
    public void testFindAllWithoutSoftDeleted() throws Exception {
        List<UserAccount> accountsList = getFindAllWithoutSoftDeletedDAO().findAll();
        assertEquals(2L, accountsList.size());
        assertEquals(2L, (long) accountsList.get(0).getId());
        assertEquals(3L, (long) accountsList.get(1).getId());
    }

    @Test
    public void testFindAllOnlySoftDeleted() throws Exception {
        List<UserAccount> accountsList = getFindAllOnlySoftDeletedDAO().findAll();
        assertEquals(1, accountsList.size());
        assertEquals(1L, (long) accountsList.get(0).getId());
    }

    @Test
    public void testFindAllWithPageRequestWithoutSoftDeleted() throws Exception {
        Page<UserAccount> accountsPage = getFindAllWithoutSoftDeletedDAO().findAll(
                new PageRequest(0, 2, Sort.Direction.DESC, "id")
        );
        List<UserAccount> userAccountList = accountsPage.getContent();
        assertEquals(2L, accountsPage.getTotalElements());
        assertEquals(1L, accountsPage.getTotalPages());
        assertEquals(2L, accountsPage.getSize());
        assertEquals(3L, (long) userAccountList.get(0).getId());
        assertEquals(2L, (long) userAccountList.get(1).getId());
    }

    @Test
    public void testFindAllWithPageRequestOnlySoftDeleted() throws Exception {
        Page<UserAccount> accountsPage = getFindAllOnlySoftDeletedDAO().findAll(
                new PageRequest(0, 2, Sort.Direction.DESC, "id")
        );
        List<UserAccount> userAccountList = accountsPage.getContent();
        assertEquals(1L, accountsPage.getTotalElements());
        assertEquals(1L, accountsPage.getTotalPages());
        assertEquals(1L, (long) userAccountList.get(0).getId());
    }
}