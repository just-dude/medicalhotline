package controller.struts.action.common;

import common.beanFactory.BeanFactoryProvider;
import domain.IssueCategory;
import domain.common.SimpleFinder;
import domain.security.SecuritySubjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public abstract class PageableAction<T> extends PrevActionMsgHandlingExceptionsAction {

    protected Integer entitiesPerPage = 30;
    protected Integer pageNumber = 1;

    protected Page<T> page;

    @Override
    protected String doExecute() throws Exception {
        if(getPermissionsStringForCheck()!=null) {
            SecuritySubjectUtils.checkPermission(getPermissionsStringForCheck());
        }
        addPrevActionMsgAndErrorToAction();
        page=loadPage();
        return CustomResults.SUCCESS;
    }

    protected abstract Page<T> loadPage();

    protected abstract String getPermissionsStringForCheck();

    public Page<T> getPage() {
        return page;
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
        return (this.page !=null)?this.page.getTotalPages():0;
    }

}
