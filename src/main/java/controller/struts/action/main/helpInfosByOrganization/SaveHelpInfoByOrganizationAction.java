package controller.struts.action.main.helpInfosByOrganization;

import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.CustomResults;
import controller.struts.action.common.FormProviderHandlingExceptionsBaseAction;
import dataAccess.common.Node;
import domain.*;
import domain.common.Finder;
import domain.security.SecuritySubjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by SuslovAI on 06.10.2017.
 */
public class SaveHelpInfoByOrganizationAction extends FormProviderHandlingExceptionsBaseAction {

    protected static final Logger LOG = LogManager.getLogger(SaveHelpInfoByOrganizationAction.class);

    private Long issueTypeId;
    private Long organizationId;
    private HelpInfoByOrganization helpInfoByOrganization = new HelpInfoByOrganization();
    private IssueType issueType;
    private Organization organization;

    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission("helpinfo-by-organization:add,edit:*:*");
        loadIssueType();
        loadOrganization();
        helpInfoByOrganization = helpInfoByOrganization.save();
        return CustomResults.SUCCESS;
    }

    @Override
    protected void doInput() throws Exception {
        SecuritySubjectUtils.checkPermission("helpinfo-by-organization:add,edit:*:*");
        Finder<HelpInfoByOrganization,  HelpInfoByOrganization.HelpInfoByOrganizationId> finder = (Finder<HelpInfoByOrganization,  HelpInfoByOrganization.HelpInfoByOrganizationId>) BeanFactoryProvider.getBeanFactory().getBean("helpInfoByOrganizationsFinder");
        if(finder.exists(new HelpInfoByOrganization.HelpInfoByOrganizationId(issueTypeId,organizationId))) {
            helpInfoByOrganization = finder.findOne(new HelpInfoByOrganization.HelpInfoByOrganizationId(issueTypeId,organizationId));
        } else{
            helpInfoByOrganization = new HelpInfoByOrganization(new HelpInfoByOrganization.HelpInfoByOrganizationId(issueTypeId,organizationId));
        }

        loadIssueType();
        loadOrganization();
    }

    private void loadIssueType(){
        Finder<IssueType, Long> issueTypesFinder = (Finder<IssueType, Long>) BeanFactoryProvider.getBeanFactory().getBean("issueTypesFinder");
        issueType = issueTypesFinder.findOne(issueTypeId,null,
                Node.rootNode().addChild(IssueType_.issueCategory.getName()));
    }

    private void loadOrganization(){
        Finder<Organization, Long> organizationsFinder = (Finder<Organization, Long>) BeanFactoryProvider.getBeanFactory().getBean("organizationsFinder");
        organization = organizationsFinder.findOne(organizationId);
    }

    public HelpInfoByOrganization getHelpInfoByOrganization() {
        return helpInfoByOrganization;
    }

    public void setHelpInfoByOrganization(HelpInfoByOrganization helpInfoByOrganization) {
        this.helpInfoByOrganization = helpInfoByOrganization;
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

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Organization getOrganization() {
        return organization;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
