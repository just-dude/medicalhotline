package controller.struts.action.main.helpInfos;

import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.CustomResults;
import controller.struts.action.common.FormProviderHandlingExceptionsBaseAction;
import dataAccess.common.Node;
import domain.HelpInfo;
import domain.HelpInfo_;
import domain.IssueType;
import domain.IssueType_;
import domain.common.Finder;
import domain.security.SecuritySubjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by SuslovAI on 06.10.2017.
 */
public class SaveHelpInfoAction extends FormProviderHandlingExceptionsBaseAction {

    protected static final Logger LOG = LogManager.getLogger(SaveHelpInfoAction.class);

    private Long issueTypeId;
    private HelpInfo helpInfo = new HelpInfo();
    private IssueType issueType;

    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission("helpinfo:add,edit:*:*");
        loadIssueType();
        helpInfo = helpInfo.save();
        return CustomResults.SUCCESS;
    }

    @Override
    protected void doInput() throws Exception {
        SecuritySubjectUtils.checkPermission("helpinfo:add,edit:*:*");
        Finder<HelpInfo, Long> finder = (Finder<HelpInfo, Long>) BeanFactoryProvider.getBeanFactory().getBean("helpInfosFinder");
        if(finder.exists(issueTypeId)) {
            helpInfo = finder.findOne(issueTypeId);
        } else{
            helpInfo=new HelpInfo(issueTypeId);
        }
        loadIssueType();
    }

    private void loadIssueType(){
        Finder<IssueType, Long> issueTypesFinder = (Finder<IssueType, Long>) BeanFactoryProvider.getBeanFactory().getBean("issueTypesFinder");
        issueType = issueTypesFinder.findOne(issueTypeId,null,
                Node.rootNode().addChild(IssueType_.issueCategory.getName()));
    }

    public HelpInfo getHelpInfo() {
        return helpInfo;
    }

    public void setHelpInfo(HelpInfo helpInfo) {
        this.helpInfo = helpInfo;
    }

    public Long getIssueTypeId() {
        return issueTypeId;
    }

    public void setIssueTypeId(Long issueTypeId) {
        this.issueTypeId = issueTypeId;
    }

    public IssueType getIssueType() {
        return issueType;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
