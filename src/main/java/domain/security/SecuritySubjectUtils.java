package domain.security;

import common.beanFactory.BeanFactoryProvider;
import domain.common.SimpleFinder;
import domain.common.exception.AuthorizationFailedException;
import domain.common.exception.BusinessException;
import domain.common.exception.ValidationFailedException;
import domain.userAccounts.UserAccount;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import smartvalidation.constraintDescription.ConstraintDescription;
import smartvalidation.constraintViolation.EntityConstraintViolation;

import java.util.Collections;

/**
 * Created by SuslovAI on 23.10.2017.
 */
public class SecuritySubjectUtils {

    private static String CURRENT_USER_ACCOUNT_SESSION_KEY = "currentUserAccount";

    public static void login(String login, String password) throws BusinessException {
        UsernamePasswordToken token = new UsernamePasswordToken(login, password);
        try {
            SecurityUtils.getSubject().login(token);
            SimpleFinder<UserAccount,Long> finder = (SimpleFinder<UserAccount,Long>) BeanFactoryProvider.getBeanFactory().getBean("userAccountsFinder");
            Long currentUserId = SecurityUtils.getSubject().getPrincipals().byType(UserPrincipal.class).iterator().next().getUserId();
            UserAccount currentUserAccount = finder.findOne(currentUserId);
            SecurityUtils.getSubject().getSession().setAttribute(CURRENT_USER_ACCOUNT_SESSION_KEY, currentUserAccount);
        } catch (AuthenticationException e) {
            throw new ValidationFailedException(Collections.singletonList(
                    new EntityConstraintViolation(new ConstraintDescription("invalid.login.or.password"), "userLoginAndPassword")
            ));
        } catch (Exception e) {
            throw new BusinessException("An error occured on login", e);
        }
    }

    public static void logout() {
        try {
            SecurityUtils.getSubject().getSession().removeAttribute(CURRENT_USER_ACCOUNT_SESSION_KEY);
            SecurityUtils.getSubject().logout();
        } catch (Exception e) {
            throw new BusinessException("An error occured on logout", e);
        }
    }

    public static UserAccount getCurrentUserAccount() {
        return (UserAccount) SecurityUtils.getSubject().getSession().getAttribute(CURRENT_USER_ACCOUNT_SESSION_KEY);
    }

    public static boolean isPermitted(String permission) throws BusinessException {
        try {
            return SecurityUtils.getSubject().isPermitted(permission);
        } catch (Exception e) {
            throw new BusinessException("An error has occured on isPermitted method", e);
        }
    }

    public static boolean[] isPermitted(String... permissions) throws BusinessException {
        try {
            return SecurityUtils.getSubject().isPermitted(permissions);
        } catch (Exception e) {
            throw new BusinessException("An error has occured on isPermitted method", e);
        }
    }

    public static boolean isPermittedAll(String... permissions) throws BusinessException {
        try {
            return SecurityUtils.getSubject().isPermittedAll(permissions);
        } catch (Exception e) {
            throw new BusinessException("An error has occured on isPermittedAll method", e);
        }
    }

    public static void checkPermission(String permission) throws AuthorizationFailedException, BusinessException {
        try {
            SecurityUtils.getSubject().checkPermission(permission);
        } catch (AuthorizationException e) {
            throw new AuthorizationFailedException(permission, e);
        } catch (Exception e) {
            throw new BusinessException("An error has occured on checkPermission method", e);
        }
    }

    public static void checkAtLeastOnePermitted(String... permissions) throws AuthorizationFailedException, BusinessException {
        try {
            boolean isPermitted=false;
            for(String permission:permissions){
                if(SecurityUtils.getSubject().isPermitted(permission)){
                    isPermitted=true;
                }
            }
            if(!isPermitted) {
                throw new AuthorizationFailedException(String.join(";",permissions));
            }
        } catch (AuthorizationFailedException e) {
            throw e;
        } catch (AuthorizationException e) {
            throw new AuthorizationFailedException(e, permissions);
        } catch (Exception e) {
            throw new BusinessException("An error has occured on checkPermissions method", e);
        }
    }

    public static void checkPermissions(String... permissions) throws AuthorizationFailedException, BusinessException {
        try {
            SecurityUtils.getSubject().checkPermissions(permissions);
        } catch (AuthorizationException e) {
            throw new AuthorizationFailedException(e, permissions);
        } catch (Exception e) {
            throw new BusinessException("An error has occured on checkPermissions method", e);
        }
    }

    public static void checkAuthenticated() throws AuthorizationFailedException, BusinessException {
        try {
            if(!SecurityUtils.getSubject().isAuthenticated()){
                throw new AuthorizationFailedException("not authenticated");
            }
        } catch (Exception e) {
            throw new BusinessException("An error has occured on checkPermission method", e);
        }
    }

    public static boolean isAuthenticated(){
        try {
            return SecurityUtils.getSubject().isAuthenticated();
        } catch (Exception e) {
            throw new BusinessException("An error has occured on isAuthenticated method", e);
        }
    }
}
