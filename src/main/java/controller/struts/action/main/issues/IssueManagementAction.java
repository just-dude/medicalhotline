package controller.struts.action.main.issues;

import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.CustomResults;
import controller.struts.action.common.PrevActionMsgHandlingExceptionsAction;
import dataAccess.common.Node;
import domain.*;
import domain.common.SimpleFinder;
import domain.common.stateChangesAccounting.EntityFieldPrevState;
import domain.security.SecuritySubjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Артем on 25.03.2018.
 */
public class IssueManagementAction extends PrevActionMsgHandlingExceptionsAction {

    protected static final Logger LOG = LogManager.getLogger(IssueManagementAction.class);

    protected Issue issue;
    protected long issueId;

    protected List<Organization> organizations;
    protected List<HelpInfo> helpInfos;
    protected List<HelpInfoByOrganization> helpInfosByOrganization;




    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission("issue:viewOne:body:"+issueId);
        addPrevActionMsgAndErrorToAction();
        loadIssueWithActivity();
        loadOrganizations();
        loadHelpInfos();
        loadHelpInfosByOrganization();
        return CustomResults.SUCCESS;
    }

    protected void loadIssueWithActivity(){
        SimpleFinder<Issue,Long> issuesFinder = (SimpleFinder<Issue,Long>) BeanFactoryProvider.getBeanFactory().getBean("issuesFinder");
        issue=issuesFinder.findOne(issueId,Node.rootNode().addChildrenByData(Issue_.takenActions.getName(),Issue_.organizations.getName(),Issue_.issueTypes.getName()));
    }

    protected void loadOrganizations(){
        SimpleFinder<Organization,Long> organizationsFinder = (SimpleFinder<Organization,Long>) BeanFactoryProvider.getBeanFactory().getBean("organizationsFinder");
        List<Long> issueOrganizationIds=issue.getOrganizations().stream().map(Organization::getId).collect(Collectors.toList());
        if(issueOrganizationIds.size()>0) {
            organizations = organizationsFinder.findAll(issueOrganizationIds, Node.rootNode().addChild(Organization_.responsiblePeople.getName()));
        }
    }

    protected void loadHelpInfos(){
        SimpleFinder<HelpInfo,Long> finder = (SimpleFinder<HelpInfo,Long>) BeanFactoryProvider.getBeanFactory().getBean("helpInfosFinder");
        List<Long> issueTypeIds=issue.getIssueTypes().stream().map(IssueType::getId).collect(Collectors.toList());
        helpInfos=finder.findAll(issueTypeIds);
    }

    protected void loadHelpInfosByOrganization(){
        SimpleFinder<HelpInfoByOrganization,HelpInfoByOrganization.HelpInfoByOrganizationId> finder =
                (SimpleFinder<HelpInfoByOrganization,HelpInfoByOrganization.HelpInfoByOrganizationId>) BeanFactoryProvider.getBeanFactory().getBean("helpInfoByOrganizationsFinder");
        List<Long> issueTypeIds=issue.getIssueTypes().stream().map(IssueType::getId).collect(Collectors.toList());
        List<Long> issueOrganizationIds=issue.getOrganizations().stream().map(Organization::getId).collect(Collectors.toList());
        List<HelpInfoByOrganization.HelpInfoByOrganizationId> helpInfoByOrganizationIds=new ArrayList<>();
        for(Long issueTypeId:issueTypeIds){
            for(Long organizationId:issueOrganizationIds){
                helpInfoByOrganizationIds.add(new HelpInfoByOrganization.HelpInfoByOrganizationId(issueTypeId,organizationId));
            }
        }
        if(helpInfoByOrganizationIds.size()>0) {
            helpInfosByOrganization = finder.findAll(helpInfoByOrganizationIds);
        }
    }

    public boolean hasAtLeastOneFieldPrevState(){
        return issue.hasAtLeastOneFieldPrevState();
    }

    public Map<EntityFieldPrevState,String> findFieldPrevStatesWithNextFieldValue(){
        if(SecuritySubjectUtils.isPermitted("issue:viewOne:changesHistory:*")) {
            return issue.findFieldPrevStatesWithNextFieldValue();
        } else{
            return Collections.EMPTY_MAP;
        }
    }

    public long getIssueId() {
        return issueId;
    }

    public void setIssueId(long issueId) {
        this.issueId = issueId;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public List<HelpInfo> getHelpInfos() {
        return helpInfos;
    }

    public List<HelpInfoByOrganization> getHelpInfosByOrganization() {
        return helpInfosByOrganization;
    }

    public Map<String, String> findAllIssueTypes(){
        SimpleFinder<IssueType,Long> finder = (SimpleFinder<IssueType,Long>)BeanFactoryProvider.getBeanFactory().getBean("issueTypesFinder");
        List<IssueType> issueTypesList=finder.findAll();
        HashMap<String, String> result = new HashMap<>();
        for (IssueType issueType : issueTypesList) {
            result.put(issueType.getId().toString(), issueType.getName());
        }
        return result;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
