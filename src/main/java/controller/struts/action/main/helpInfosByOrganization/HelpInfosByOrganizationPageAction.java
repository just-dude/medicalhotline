package controller.struts.action.main.helpInfosByOrganization;

import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.PageableAction;
import domain.*;
import domain.common.SimpleFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HelpInfosByOrganizationPageAction extends PageableAction<HelpInfoByOrganization> {

    protected static final Logger LOG = LogManager.getLogger(HelpInfosByOrganizationPageAction.class);

    private Long issueCategoryId;
    private Long issueTypeId;
    private Long organizationId;

    public HelpInfosByOrganizationPageAction() {
        entitiesPerPage=10;
    }

    @Override
    protected Page<HelpInfoByOrganization> loadPage() {
        HelpInfosByOrganizationFinder helpInfosByOrganizationFinder = (HelpInfosByOrganizationFinder) BeanFactoryProvider.getBeanFactory().getBean("helpInfoByOrganizationsFinder");
        return helpInfosByOrganizationFinder.findAllForAllIssueTypesAndOrganizations(new PageRequest(pageNumber - 1, entitiesPerPage),issueCategoryId,issueTypeId,organizationId);
    }

    @Override
    protected String getPermissionsStringForCheck() {
        return "helpinfo-by-organization:view:*:*";
    }

    public Long getIssueCategoryId() {
        return issueCategoryId;
    }

    public void setIssueCategoryId(Long issueCategoryId) {
        this.issueCategoryId = issueCategoryId;
    }

    public Long getIssueTypeId() {
        return issueTypeId;
    }

    public void setIssueTypeId(Long issueTypeId) {
        this.issueTypeId = issueTypeId;
    }

    public Map<String, String> findAllIssueCategories(){
        SimpleFinder<IssueCategory,Long> finder = (SimpleFinder<IssueCategory,Long>)BeanFactoryProvider.getBeanFactory().getBean("issueCategoriesWithoutSoftDeletedFinder");
        List<IssueCategory> list =finder.findAll(new Sort("name"));
        HashMap<String, String> result = new LinkedHashMap();
        result.put("All",getText("All"));
        for (IssueCategory issueCategory :  list) {
            result.put(issueCategory.getId().toString(), issueCategory.getName());
        }
        return result;
    }

    public Map<String, String> findAllIssueTypes(){
        SimpleFinder<IssueType,Long> finder = (SimpleFinder<IssueType,Long>)BeanFactoryProvider.getBeanFactory().getBean("issueTypesWithoutSoftDeletedFinder");
        List<IssueType> list =finder.findAll(new Sort("name"));
        HashMap<String, String> result = new LinkedHashMap();
        result.put("All",getText("All"));
        for (IssueType issueType :  list) {
            result.put(issueType.getId().toString(), issueType.getName());
        }
        return result;
    }

    public Map<String, String> findAllOrganizations(){
        SimpleFinder<Organization,Long> finder = (SimpleFinder<Organization,Long>)BeanFactoryProvider.getBeanFactory().getBean("organizationsWithoutSoftDeletedFinder");
        List<Organization> list =finder.findAll(new Sort("name"));
        HashMap<String, String> result = new LinkedHashMap();
        result.put("All",getText("All"));
        for (Organization organization :  list) {
            result.put(organization.getId().toString(), organization.getName());
        }
        return result;
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
