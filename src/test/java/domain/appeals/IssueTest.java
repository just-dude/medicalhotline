package domain.appeals;

import common.DBTestCase;
import common.argumentAssert.Assert;
import common.beanFactory.BeanFactoryProvider;
import dataAccess.common.Node;
import domain.Appeal;
import domain.Issue;
import domain.IssueAction;
import domain.Issue_;
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
import org.springframework.data.domain.Sort;

import java.io.InputStream;
import java.util.Arrays;

/**
 * Created by Артем on 01.04.2018.
 */
public class IssueTest extends DBTestCase {

    public IssueTest() {
        super("IssueTest");
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
    public void addActions() throws Exception {
        Issue issue=new Issue(1L);
        IssueAction action=new IssueAction("Some action 1",false);
        issue.addAction(action);
        //issue=new Issue(1L);
        action=new IssueAction("Some action 2",true);
        issue.addAction(action);

        IDataSet databaseDataSet = getConnection().createDataSet(new String[]{"test_medical_hotline.IssueActions","test_medical_hotline.Issues","test_medical_hotline.Appeals"});
        InputStream expectedDataSetInputStream = getDataSetAsInputStream("testDataSet/issues/AfterAddActionsToIssueExpectedDataset.xml");
        ReplacementDataSet expectedDataSet = new ReplacementDataSet(new FlatXmlDataSetBuilder().build(expectedDataSetInputStream));
        expectedDataSet.addReplacementObject("[null]", null);
        ITable actualTable = databaseDataSet.getTable("test_medical_hotline.IssueActions");
        ITable filteredActualTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"id"});
        ITable expectedTable = expectedDataSet.getTable("test_medical_hotline.IssueActions");

        Assertion.assertEquals(expectedTable, filteredActualTable);

        actualTable = databaseDataSet.getTable("test_medical_hotline.Issues");
        filteredActualTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"id"});
        expectedTable = expectedDataSet.getTable("test_medical_hotline.Issues");

        Assertion.assertEquals(expectedTable, filteredActualTable);

        actualTable = databaseDataSet.getTable("test_medical_hotline.Appeals");
        filteredActualTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"id"});
        expectedTable = expectedDataSet.getTable("test_medical_hotline.Appeals");

        Assertion.assertEquals(expectedTable, filteredActualTable);
    }

    @Test
    public void  goToImpossibleToSolveState() throws Exception{
        SimpleFinder<Issue,Long> finder = (SimpleFinder<Issue,Long>) BeanFactoryProvider.getBeanFactory().getBean("issuesFinder");
        Issue issue=finder.findOne(1L, Node.rootNode().addChild(Issue_.takenActions.getName()));
        issue.goToImpossibleToSolveStateAndSave("some cause of transition");


        IDataSet databaseDataSet = getConnection().createDataSet(new String[]{"test_medical_hotline.Issues","test_medical_hotline.Appeals"});
        InputStream expectedDataSetInputStream = getDataSetAsInputStream("testDataSet/issues/AfterGoIssueToImpossibleToSolveStateExpectedDataset.xml");
        ReplacementDataSet expectedDataSet = new ReplacementDataSet(new FlatXmlDataSetBuilder().build(expectedDataSetInputStream));
        expectedDataSet.addReplacementObject("[null]", null);
        ITable actualTable = databaseDataSet.getTable("test_medical_hotline.Issues");
        ITable filteredActualTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"id"});
        ITable expectedTable = expectedDataSet.getTable("test_medical_hotline.Issues");

        Assertion.assertEquals(expectedTable, filteredActualTable);


        actualTable = databaseDataSet.getTable("test_medical_hotline.Appeals");
        filteredActualTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"id"});
        expectedTable = expectedDataSet.getTable("test_medical_hotline.Appeals");

        Assertion.assertEquals(expectedTable, filteredActualTable);
    }

    @Test
    public void  goToNotRelevantState() throws Exception{
        SimpleFinder<Issue,Long> finder = (SimpleFinder<Issue,Long>) BeanFactoryProvider.getBeanFactory().getBean("issuesFinder");
        Issue issue=new Issue(1L);
        issue.goToNotRelevantStateAndSave("some cause of transition");

        IDataSet databaseDataSet = getConnection().createDataSet(new String[]{"test_medical_hotline.Issues","test_medical_hotline.Appeals"});
        InputStream expectedDataSetInputStream = getDataSetAsInputStream("testDataSet/issues/AfterGoIssueToNotRelevantStateExpectedDataset.xml");
        ReplacementDataSet expectedDataSet = new ReplacementDataSet(new FlatXmlDataSetBuilder().build(expectedDataSetInputStream));
        expectedDataSet.addReplacementObject("[null]", null);
        ITable actualTable = databaseDataSet.getTable("test_medical_hotline.Issues");
        ITable filteredActualTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"id"});
        ITable expectedTable = expectedDataSet.getTable("test_medical_hotline.Issues");

        Assertion.assertEquals(expectedTable, filteredActualTable);


        actualTable = databaseDataSet.getTable("test_medical_hotline.Appeals");
        filteredActualTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"id"});
        expectedTable = expectedDataSet.getTable("test_medical_hotline.Appeals");

        Assertion.assertEquals(expectedTable, filteredActualTable);
    }

    @Test
    public void  findOne() throws Exception{
        SimpleFinder<Issue,Long> finder = (SimpleFinder<Issue,Long>) BeanFactoryProvider.getBeanFactory().getBean("issuesFinder");
        Issue issue=finder.findOne(1L,Node.rootNode().addChild(Issue_.takenActions.getName()));
        assertEquals((long)issue.getId(),1L);
    }

    @Test
    public void  findAll() throws Exception{
        Sort sort=new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
        SimpleFinder<Issue,Long> finder = (SimpleFinder<Issue,Long>) BeanFactoryProvider.getBeanFactory().getBean("issuesFinder");
        Page<Issue> issuesPage=finder.findAll(new PageRequest(0,1,sort),Node.rootNode().addChild(Issue_.takenActions.getName()));
        Assert.notEmpty(issuesPage.getContent());
    }


}