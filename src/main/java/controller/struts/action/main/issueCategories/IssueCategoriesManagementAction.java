package controller.struts.action.main.issueCategories;

import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.CustomResults;
import controller.struts.action.common.PrevActionMsgHandlingExceptionsAction;
import domain.Issue;
import domain.IssueCategory;
import domain.common.SimpleFinder;
import domain.security.SecuritySubjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

public class IssueCategoriesManagementAction extends PrevActionMsgHandlingExceptionsAction {

    protected static final Logger LOG = LogManager.getLogger(IssueCategoriesManagementAction.class);

    private Integer entitiesPerPage = 30;
    private Integer pageNumber = 1;

    private Page<IssueCategory> issueCategoriesPage;

    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission("issuecategory:add,edit,remove,view:*:*");
        addPrevActionMsgAndErrorToAction();
        loadIssueCategories();
        return CustomResults.SUCCESS;
    }

    private void loadIssueCategories() {
        SimpleFinder<IssueCategory,Long> finder = (SimpleFinder<IssueCategory,Long>) BeanFactoryProvider.getBeanFactory().getBean("issueCategoriesFinder");
        issueCategoriesPage = finder.findAll(new PageRequest(pageNumber - 1, entitiesPerPage,new Sort("name")));
    }

    public Page<IssueCategory> getIssueCategoriesPage() {
        return issueCategoriesPage;
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
        return (this.issueCategoriesPage!=null)?this.issueCategoriesPage.getTotalPages():0;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
