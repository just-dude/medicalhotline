package controller.struts.action.main.issues;

import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.CustomResults;
import controller.struts.action.common.FormProviderHandlingExceptionsBaseAction;
import controller.struts.customComponent.util.JsonUtils;
import dataAccess.common.Node;
import domain.*;
import domain.common.Finder;
import domain.common.SimpleFinder;
import domain.security.SecuritySubjectUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Created by Артем on 25.03.2018.
 */
public class EditIssueAction extends FormProviderHandlingExceptionsBaseAction {

    protected static final Logger LOG = LogManager.getLogger(EditIssueAction.class);

    protected Issue issue;
    protected long issueId;
    protected Long appealId;
    protected String[] issueTypes;
    protected String[] organizationsIds;

    private List<IssueCategory> escapedIssueCategoriesList =new ArrayList<>();


    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission("issue:edit:*:*");
        loadEscapedIssueCategoriesWithTypes();
        for(String issueTypeId: getIssueTypes()){
            issue.addLabel(new IssueType(Long.parseLong(issueTypeId)));
        }
        for(String organizationId: getOrganizationsIds()){
            issue.addOrganization(new Organization(Long.parseLong(organizationId)));
        }
        issue.save();
        loadIssue(); // Use this instead of issue=issue.save(); because can't get issueTypes and organizationsIds cause lazy loading exception has occured
        return CustomResults.SUCCESS;
    }

    @Override
    protected void doInput() throws Exception {
        SecuritySubjectUtils.checkPermission("issue:edit:*:*");
        loadIssue();
        loadEscapedIssueCategoriesWithTypes();
    }

    protected void loadIssue(){
        Finder<Issue, Long> issuesFinder = (Finder<Issue, Long>) BeanFactoryProvider.getBeanFactory().getBean("issuesFinder");
        issue = issuesFinder.findOne(issueId,Node.rootNode().addChildrenByData(Issue_.takenActions.getName(),Issue_.organizations.getName(),Issue_.issueTypes.getName()));
        issueTypes=issue.getIssueTypes().stream().map(it->it.getId().toString()).toArray(size -> new String[size]);
        organizationsIds=issue.getOrganizations().stream().map(o->o.getId().toString()).toArray(size -> new String[size]);
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public long getIssueId() {
        return issueId;
    }

    public void setIssueId(long issueId) {
        this.issueId = issueId;
    }

    public Long getAppealId() {
        return appealId;
    }

    public void setAppealId(Long appealId) {
        this.appealId = appealId;
    }

    public Map<String, String> getAllIssueCategories(){
        HashMap<String, String> result = new LinkedHashMap<>();
        result.put("-1",getText("All"));
        for (IssueCategory issueCategory : escapedIssueCategoriesList) {
            result.put(issueCategory.getId().toString(), StringEscapeUtils.unescapeHtml4(issueCategory.getName()));
        }
        return result;
    }

    public Map<String, String> getAllIssueTypes(){
        HashMap<String, String> result = new LinkedHashMap<>();
        for (IssueCategory issueCategory : escapedIssueCategoriesList) {
            for(IssueType issueType : issueCategory.getIssueTypes()) {
                result.put(issueType.getId().toString(), StringEscapeUtils.unescapeHtml4(issueType.getName()));
            }
        }
        return result;
    }

    private void loadEscapedIssueCategoriesWithTypes() {
        Finder<IssueCategory,Long> issueCategoriesFinder = (Finder<IssueCategory,Long>) BeanFactoryProvider.getBeanFactory().getBean("issueCategoriesWithoutSoftDeletedFinder");
        escapedIssueCategoriesList = issueCategoriesFinder.findAll((Node<String>) null,Node.rootNode().addChild(IssueCategory_.issueTypes.getName()));
        for (IssueCategory issueCategory : escapedIssueCategoriesList) {
            issueCategory.setName(StringEscapeUtils.escapeHtml4(issueCategory.getName()));
            for(IssueType issueType : issueCategory.getIssueTypes()) {
                issueType.setName(StringEscapeUtils.escapeHtml4(issueType.getName()));
            }
        }
    }

    public String getIssueTypesAndCategoriesListAsJson(){
        try {
            return JsonUtils.toJson(getEscapedIssueCategoriesList());
        }catch (Exception e){
            LOG.error(e);
            return null;
        }
    }

    public Map<String,String> findAllOrganizations(){
        SimpleFinder<Organization,Long> finder = (SimpleFinder<Organization,Long>) BeanFactoryProvider.getBeanFactory().getBean("organizationsWithoutSoftDeletedFinder");
        List<Organization> organizations = finder.findAll();

        HashMap<String, String> result = new LinkedHashMap<>();
        for (Organization organization : organizations) {
            result.put(organization.getId().toString(),organization.getName());
        }
        return result;
    }

    public List<IssueCategory> getEscapedIssueCategoriesList() {
        return escapedIssueCategoriesList;
    }

    public String[] getIssueTypes() {
        return issueTypes;
    }

    public void setIssueTypes(String[] issueTypes) {
        this.issueTypes = issueTypes;
    }

    public String[] getOrganizationsIds() {
        return organizationsIds;
    }

    public void setOrganizationsIds(String[] organizationsIds) {
        this.organizationsIds = organizationsIds;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
