package domain.helpInfos;

import common.DBTestCase;
import common.beanFactory.BeanFactoryProvider;
import domain.HelpInfo;
import domain.IssueType;
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
public class HelpInfoTest extends DBTestCase {

    public HelpInfoTest() {
        super("HelpInfoTest");
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(getDataSetAsInputStream("testDataSet/helpInfos/HelpInfosSetupDataset.xml"));
    }

    @Test
    public void saveNew() throws Exception{
        HelpInfo helpInfo =new HelpInfo(new IssueType(1L),"text");
        helpInfo=helpInfo.save();

        IDataSet databaseDataSet = getConnection().createDataSet(new String[]{"test_medical_hotline.HelpInfos"});
        ITable actualTable = databaseDataSet.getTable("test_medical_hotline.HelpInfos");
        ITable filteredActualTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{});
        InputStream expectedDataSetInputStream = getDataSetAsInputStream("testDataSet/helpInfos/AfterInsertHelpInfoExpectedDataset.xml");
        ReplacementDataSet expectedDataSet = new ReplacementDataSet(new FlatXmlDataSetBuilder().build(expectedDataSetInputStream));
        expectedDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = expectedDataSet.getTable("test_medical_hotline.HelpInfos");

        Assertion.assertEquals(expectedTable, filteredActualTable);
    }

    @Test
    public void saveExists() throws Exception{
        SimpleFinder<HelpInfo,Long> finder = (SimpleFinder<HelpInfo,Long>) BeanFactoryProvider.getBeanFactory().getBean("helpInfosFinder");
        HelpInfo helpInfo=finder.findOne(2L);
        helpInfo.setText("new text");
        helpInfo=helpInfo.save();

        IDataSet databaseDataSet = getConnection().createDataSet(new String[]{"test_medical_hotline.HelpInfos"});
        ITable actualTable = databaseDataSet.getTable("test_medical_hotline.HelpInfos");
        ITable filteredActualTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{});
        InputStream expectedDataSetInputStream = getDataSetAsInputStream("testDataSet/helpInfos/AfterUpdateHelpInfoExpectedDataset.xml");
        ReplacementDataSet expectedDataSet = new ReplacementDataSet(new FlatXmlDataSetBuilder().build(expectedDataSetInputStream));
        expectedDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = expectedDataSet.getTable("test_medical_hotline.HelpInfos");

        Assertion.assertEquals(expectedTable, filteredActualTable);
    }

    @Test
    public void findOne() throws Exception{

        SimpleFinder<HelpInfo,Long> finder = (SimpleFinder<HelpInfo,Long>) BeanFactoryProvider.getBeanFactory().getBean("helpInfosFinder");
        HelpInfo helpInfo=finder.findOne(2L);

        assertEquals((long)helpInfo.getIssueType().getId(),2L);
        assertEquals(helpInfo.getText(),"help text");
    }
}