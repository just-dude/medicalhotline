package controller.struts.action.admin.userAccounts;

import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.CustomResults;
import controller.struts.action.common.PrevActionMsgHandlingExceptionsAction;
import domain.common.Finder;
import domain.security.SecuritySubjectUtils;
import domain.userAccounts.UserAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * Created by SuslovAI on 06.10.2017.
 */
public class UserAccountsManagementAction extends PrevActionMsgHandlingExceptionsAction {

    protected static final Logger LOG = LogManager.getLogger(UserAccountsManagementAction.class);

    private static final Integer entitiesPerPage = 30;

    private Integer pageNumber = 1;

    private Page<UserAccount> userAccountsPage;

    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission("userAccount:view:*:*");
        addPrevActionMsgAndErrorToAction();
        loadUserAccountsPage();
        return CustomResults.SUCCESS;
    }

    private void loadUserAccountsPage() {
        Finder<UserAccount, Long> userAccountsFinder = (Finder<UserAccount, Long>) BeanFactoryProvider.getBeanFactory().getBean("userAccountsFinder");
        userAccountsPage = userAccountsFinder.findAll(new PageRequest(pageNumber - 1, entitiesPerPage));
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Integer getTotalPagesCount() {
        return (this.userAccountsPage!=null)?this.userAccountsPage.getTotalPages():0;
    }

    public void setPageNumber(Integer pageNumber) {
        if(pageNumber==null || pageNumber<1){
            pageNumber=1;
        }
        this.pageNumber = pageNumber;
    }

    public Page<UserAccount> getUserAccountsPage() {
        return userAccountsPage;
    }



    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
