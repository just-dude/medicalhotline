package controller.struts.action.main.helpInfos;

import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.PageableAction;
import domain.HelpInfo;
import domain.HelpInfosFinder;
import domain.IssueCategory;
import domain.IssueType;
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

public class HelpInfosPageAction extends PageableAction<HelpInfo> {

    protected static final Logger LOG = LogManager.getLogger(HelpInfosPageAction.class);

    private Long issueCategoryId;
    private Long issueTypeId;

    public HelpInfosPageAction() {
        entitiesPerPage=10;
    }

    @Override
    protected Page<HelpInfo> loadPage() {
        HelpInfosFinder helpInfosFinder = (HelpInfosFinder) BeanFactoryProvider.getBeanFactory().getBean("helpInfosFinder");
        return helpInfosFinder.findAllForAllIssueTypes(new PageRequest(pageNumber - 1, entitiesPerPage),issueCategoryId,issueTypeId);
    }

    @Override
    protected String getPermissionsStringForCheck() {
        return "helpinfo:view:*:*";
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

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
