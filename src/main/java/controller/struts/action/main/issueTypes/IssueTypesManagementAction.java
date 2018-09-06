package controller.struts.action.main.issueTypes;

import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.CustomResults;
import controller.struts.action.common.PrevActionMsgHandlingExceptionsAction;
import dataAccess.IssueTypesSpecifications;
import domain.Issue;
import domain.IssueCategory;
import domain.IssueType;
import domain.common.SimpleFinder;
import domain.security.SecuritySubjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static dataAccess.IssueTypesSpecifications.issueTypesBelongsToCategory;

public class IssueTypesManagementAction extends PrevActionMsgHandlingExceptionsAction {

    protected static final Logger LOG = LogManager.getLogger(IssueTypesManagementAction.class);

    private Integer entitiesPerPage = 30;
    private Integer pageNumber = 1;

    private Long issueCategoryId;
    private IssueCategory issueCategory;

    private Page<IssueType> issueTypesPage;

    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission("issuetype:add,edit,remove,view:*:*");
        addPrevActionMsgAndErrorToAction();
        SimpleFinder<IssueType,Long> finder = (SimpleFinder<IssueType,Long>) BeanFactoryProvider.getBeanFactory().getBean("issueTypesFinder");
        issueTypesPage = finder.findAll(issueTypesBelongsToCategory(issueCategoryId),new PageRequest(pageNumber - 1, entitiesPerPage, new Sort("name")));
        SimpleFinder<IssueCategory,Long> issueCategoriesFinder = (SimpleFinder<IssueCategory,Long>) BeanFactoryProvider.getBeanFactory().getBean("issueCategoriesFinder");
        issueCategory=issueCategoriesFinder.findOne(issueCategoryId);
        return CustomResults.SUCCESS;
    }

    public Page<IssueType> getIssueTypesPage() {
        return issueTypesPage;
    }

    public void setEntitiesPerPage(Integer entitiesPerPage) {
        this.entitiesPerPage = entitiesPerPage;
    }

    public void setPageNumber(Integer pageNumber) {
        if(pageNumber==null || pageNumber<1){
            pageNumber=1;
        }
        this.pageNumber = pageNumber;
    }

    public Integer getEntitiesPerPage() {
        return entitiesPerPage;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Integer getTotalPagesCount() {
        return (this.issueTypesPage!=null)?this.issueTypesPage.getTotalPages():0;
    }

    public Long getIssueCategoryId() {
        return issueCategoryId;
    }

    public void setIssueCategoryId(Long issueCategoryId) {
        this.issueCategoryId = issueCategoryId;
    }

    public IssueCategory getIssueCategory() {
        return issueCategory;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
