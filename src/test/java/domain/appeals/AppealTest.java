package domain.appeals;

import common.DBTestCase;
import common.argumentAssert.Assert;
import common.beanFactory.BeanFactoryProvider;
import dataAccess.common.Node;
import domain.*;
import domain.common.SimpleFinder;
import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.InputStream;
import java.util.Arrays;

public class AppealTest extends DBTestCase {

    public AppealTest() {
        super("AppealTest");
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(getDataSetAsInputStream("testDataSet/AppealsSetupDataset.xml"));
    }

    @Test
    public void addIssue() throws Exception{
        Appeal appeal =new Appeal(2L);
        Issue issue=new Issue("Very seriosly issue 1");
        issue.setIssueCategory(new IssueCategory(1L));
        issue.getIssueTypes().add(new IssueType(1L));
        issue=appeal.addIssue(issue);
        issue=new Issue("Very seriosly issue 2");
        issue.getIssueTypes().add(new IssueType(2L));
        issue.setIssueCategory(new IssueCategory(1L));
        appeal.addIssue(issue);

        IDataSet databaseDataSet = getConnection().createDataSet(new String[]{"test_medical_hotline.Issues"});
        ITable actualTable = databaseDataSet.getTable("test_medical_hotline.Issues");
        ITable filteredActualTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"id"});
        InputStream expectedDataSetInputStream = getDataSetAsInputStream("testDataSet/appeals/AfterAddIssuesToAppealExpectedDataset.xml");
        ReplacementDataSet expectedDataSet = new ReplacementDataSet(new FlatXmlDataSetBuilder().build(expectedDataSetInputStream));
        expectedDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = expectedDataSet.getTable("test_medical_hotline.Issues");

        Assertion.assertEquals(expectedTable, filteredActualTable);
    }



    @Test
    public void save() throws Exception{
        Appeal savedAppeal=firstStageSave();
        secondStageSave(savedAppeal);
    }

    private Appeal firstStageSave() throws Exception{
        Appeal appeal =new Appeal();
        appeal.setContactPhoneNumber("+7(999)999-99-99");
        Appeal savedAppeal=appeal.save();

        IDataSet databaseDataSet = getConnection().createDataSet(new String[]{"test_medical_hotline.Appeals"});
        ITable actualTable = databaseDataSet.getTable("test_medical_hotline.Appeals");
        ITable filteredActualTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"id"});
        InputStream expectedDataSetInputStream = getDataSetAsInputStream("testDataSet/appeals/AfterFirstStageOfSaveAppealExpectedDataset.xml");
        ReplacementDataSet expectedDataSet = new ReplacementDataSet(new FlatXmlDataSetBuilder().build(expectedDataSetInputStream));
        expectedDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = expectedDataSet.getTable("test_medical_hotline.Appeals");

        Assertion.assertEquals(expectedTable, filteredActualTable);
        return savedAppeal;
    }

    private Appeal secondStageSave(Appeal savedAppeal) throws Exception{
        Appeal appeal=new Appeal(savedAppeal.getId());
        appeal.setContactPhoneNumber("+7(999)999-99-99");
        appeal.setCitizen(new Citizen("c-name","c-surname",null,null,null,null));
        savedAppeal=appeal.save();

        IDataSet databaseDataSet = getConnection().createDataSet(new String[]{"test_medical_hotline.Appeals","test_medical_hotline.Citizens"});
        ITable actualTable = databaseDataSet.getTable("test_medical_hotline.Appeals");
        ITable filteredActualTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"id","citizenId"});
        InputStream expectedDataSetInputStream = getDataSetAsInputStream("testDataSet/appeals/AfterSecondStageOfSaveAppealExpectedDataset.xml");
        ReplacementDataSet expectedDataSet = new ReplacementDataSet(new FlatXmlDataSetBuilder().build(expectedDataSetInputStream));
        expectedDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = expectedDataSet.getTable("test_medical_hotline.Appeals");

        Assertion.assertEquals(expectedTable, filteredActualTable);
        return savedAppeal;
    }

    @Test
    public void  goToCanceledState() throws Exception{
        SimpleFinder<Appeal,Long> appealsFinder = (SimpleFinder<Appeal,Long>) BeanFactoryProvider.getBeanFactory().getBean("appealsFinder");
        Appeal appeal=new Appeal(2L);
        appeal.goToCanceledStateAndSave("some cause of transition");

        IDataSet databaseDataSet = getConnection().createDataSet(new String[]{"test_medical_hotline.Appeals","test_medical_hotline.Citizens"});
        ITable actualTable = databaseDataSet.getTable("test_medical_hotline.Appeals");
        ITable filteredActualTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"id","citizenId"});
        InputStream expectedDataSetInputStream = getDataSetAsInputStream("testDataSet/appeals/AfterGoAppealToCanceledStateExpectedDataset.xml");
        ReplacementDataSet expectedDataSet = new ReplacementDataSet(new FlatXmlDataSetBuilder().build(expectedDataSetInputStream));
        expectedDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = expectedDataSet.getTable("test_medical_hotline.Appeals");

        Assertion.assertEquals(expectedTable, filteredActualTable);


    }

    @Test
    public void  findOne() throws Exception{
        SimpleFinder<Appeal,Long> finder = (SimpleFinder<Appeal,Long>) BeanFactoryProvider.getBeanFactory().getBean("appealsFinder");
        Appeal issue=finder.findOne(1L,Node.rootNode().addChild(Appeal_.issues.getName()));
        assertEquals((long)issue.getId(),1L);
    }

    @Test
    public void  findAllAppeals() throws Exception{
        SimpleFinder<Appeal,Long> finder = (SimpleFinder<Appeal,Long>) BeanFactoryProvider.getBeanFactory().getBean("appealsFinder");
        Page<Appeal> appealsPage=finder.findAll(new PageRequest(0,1),Node.rootNode().addChild(new Node<>(Appeal_.issues.getName()).addChild(
                new Node<>(Issue_.organizations.getName()))));
        Assert.notEmpty(appealsPage.getContent().get(0).getIssues().get(0).getOrganizations());
    }

}