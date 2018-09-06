package controller.struts.action.main.helpInfosByOrganization;

import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.CustomResults;
import controller.struts.action.common.PrevActionMsgHandlingExceptionsAction;
import dataAccess.common.Node;
import domain.HelpInfoByOrganization;
import domain.HelpInfoByOrganization_;
import domain.HelpInfo_;
import domain.IssueType_;
import domain.common.SimpleFinder;
import domain.security.SecuritySubjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Артем on 25.03.2018.
 */
public class HelpInfoByOrganizationManagementAction extends PrevActionMsgHandlingExceptionsAction {

    protected static final Logger LOG = LogManager.getLogger(HelpInfoByOrganizationManagementAction.class);

    protected HelpInfoByOrganization helpInfoByOrganization=new HelpInfoByOrganization();
    protected Long issueTypeId;
    protected Long organizationId;

    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission("helpinfo-by-organization:edit,view:*:"+ issueTypeId);
        addPrevActionMsgAndErrorToAction();
        SimpleFinder<HelpInfoByOrganization,HelpInfoByOrganization.HelpInfoByOrganizationId> helpInfosByOrganizationFinder =
                (SimpleFinder<HelpInfoByOrganization,HelpInfoByOrganization.HelpInfoByOrganizationId>)BeanFactoryProvider.getBeanFactory().getBean("helpInfoByOrganizationsFinder");
        helpInfoByOrganization=helpInfosByOrganizationFinder.findOne(new HelpInfoByOrganization.HelpInfoByOrganizationId(issueTypeId,organizationId),null,
                Node.rootNode().addChild(new Node<>(HelpInfoByOrganization_.issueType.getName()).addChild(IssueType_.issueCategory.getName())));
        return CustomResults.SUCCESS;
    }

    public HelpInfoByOrganization getHelpInfoByOrganization() {
        return helpInfoByOrganization;
    }

    public Long getIssueTypeId() {
        return issueTypeId;
    }

    public void setIssueTypeId(Long issueTypeId) {
        this.issueTypeId = issueTypeId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }





    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
