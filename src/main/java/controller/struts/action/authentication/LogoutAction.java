package controller.struts.action.authentication;

import com.opensymphony.xwork2.ActionContext;
import controller.struts.action.common.CustomResults;
import controller.struts.action.common.FormProviderHandlingExceptionsBaseAction;
import domain.security.SecuritySubjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.CookiesAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by SuslovAI on 18.10.2017.
 */
public class LogoutAction extends FormProviderHandlingExceptionsBaseAction{

    protected static final Logger LOG = LogManager.getLogger(LogoutAction.class);

    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.logout();
        return CustomResults.SUCCESS;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
