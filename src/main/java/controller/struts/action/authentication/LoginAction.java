package controller.struts.action.authentication;

import controller.struts.action.common.CustomResults;
import controller.struts.action.common.FormProviderHandlingExceptionsBaseAction;
import domain.security.SecuritySubjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by SuslovAI on 18.10.2017.
 */
public class LoginAction extends FormProviderHandlingExceptionsBaseAction {

    protected static final Logger LOG = LogManager.getLogger(LoginAction.class);

    private String login;
    private String password;

    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.login(login,password);
        return CustomResults.SUCCESS;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
