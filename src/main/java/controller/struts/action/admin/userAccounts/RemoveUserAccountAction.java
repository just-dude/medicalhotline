package controller.struts.action.admin.userAccounts;

import controller.struts.action.common.CustomResults;
import controller.struts.action.common.PrevActionMsgHandlingExceptionsAction;
import domain.security.SecuritySubjectUtils;
import domain.userAccounts.UserAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by SuslovAI on 06.10.2017.
 */
public class RemoveUserAccountAction extends PrevActionMsgHandlingExceptionsAction {

    protected static final Logger LOG = LogManager.getLogger(RemoveUserAccountAction.class);

    private Long userAccountId;

    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission("userAccount:remove:*:*");
        new UserAccount(userAccountId).remove();
        return CustomResults.SUCCESS;
    }

    public Long getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(Long userAccountId) {
        this.userAccountId = userAccountId;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
