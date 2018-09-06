package domain.helpInfos;

import common.DBTestCase;
import common.beanFactory.BeanFactoryProvider;
import domain.HelpInfoByOrganization;
import domain.IssueType;
import domain.Organization;
import domain.common.SimpleFinder;
import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;

import java.io.InputStream;

/**
 * Created by Артем on 21.04.2018.
 */
public class HelpInfoByOrganizationTest extends DBTestCase {

    public HelpInfoByOrganizationTest() {
        super("HelpInfoByOrganizationTest");
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(getDataSetAsInputStream("testDataSet/helpinfoByOrganization/SetupDataset.xml"));
    }

    @Test
    public void saveNew() throws Exception{
        HelpInfoByOrganization helpInfo =new HelpInfoByOrganization(new IssueType(2L),new Organization(1L),"help text");
        helpInfo=helpInfo.save();

        IDataSet databaseDataSet = getConnection().createDataSet(new String[]{"test_medical_hotline.HelpInfosByOrganization"});
        ITable actualTable = databaseDataSet.getTable("test_medical_hotline.HelpInfosByOrganization");
        ITable filteredActualTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{});
        InputStream expectedDataSetInputStream = getDataSetAsInputStream("testDataSet/helpinfoByOrganization/AfterInsertExpectedDataset.xml");
        ReplacementDataSet expectedDataSet = new ReplacementDataSet(new FlatXmlDataSetBuilder().build(expectedDataSetInputStream));
        expectedDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = expectedDataSet.getTable("test_medical_hotline.HelpInfosByOrganization");

        Assertion.assertEquals(expectedTable, filteredActualTable);
    }

    @Test
    public void saveExists() throws Exception{
        SimpleFinder<HelpInfoByOrganization,HelpInfoByOrganization.HelpInfoByOrganizationId> finder = (SimpleFinder<HelpInfoByOrganization,HelpInfoByOrganization.HelpInfoByOrganizationId>) BeanFactoryProvider.getBeanFactory().getBean("helpInfoByOrganizationsFinder");
        HelpInfoByOrganization helpInfoByOrganization=finder.findOne(new HelpInfoByOrganization.HelpInfoByOrganizationId(1L,1L));
        helpInfoByOrganization.setText("new text");
        helpInfoByOrganization=helpInfoByOrganization.save();

        IDataSet databaseDataSet = getConnection().createDataSet(new String[]{"test_medical_hotline.HelpInfosByOrganization"});
        ITable actualTable = databaseDataSet.getTable("test_medical_hotline.HelpInfosByOrganization");
        ITable filteredActualTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{});
        InputStream expectedDataSetInputStream = getDataSetAsInputStream("testDataSet/helpinfoByOrganization/AfterUpdateExpectedDataset.xml");
        ReplacementDataSet expectedDataSet = new ReplacementDataSet(new FlatXmlDataSetBuilder().build(expectedDataSetInputStream));
        expectedDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = expectedDataSet.getTable("test_medical_hotline.HelpInfosByOrganization");

        Assertion.assertEquals(expectedTable, filteredActualTable);
    }

    @Test
    public void findOne() throws Exception{
        SimpleFinder<HelpInfoByOrganization,HelpInfoByOrganization.HelpInfoByOrganizationId> finder = (SimpleFinder<HelpInfoByOrganization,HelpInfoByOrganization.HelpInfoByOrganizationId>) BeanFactoryProvider.getBeanFactory().getBean("helpInfoByOrganizationsFinder");
        HelpInfoByOrganization helpInfoByOrganization=finder.findOne(new HelpInfoByOrganization.HelpInfoByOrganizationId(1L,1L));

        assertEquals((long)helpInfoByOrganization.getIssueType().getId(),1L);
        assertEquals(helpInfoByOrganization.getText(),"text");
    }
}