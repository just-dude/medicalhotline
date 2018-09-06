/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.security.authentication;

import common.beanFactory.BeanFactoryProvider;
import domain.common.SimpleFinder;
import domain.common.exception.BusinessException;
import domain.common.exception.RuleViolationBusinessException;
import domain.security.UserPrincipal;
import domain.userAccounts.UserAccount;
import org.apache.shiro.authc.*;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static dataAccess.UserAccountsSpecifications.userAccountHasLogin;


public class UserAccountsBasedAuthenticatingRealm extends AuthenticatingRealm {

    private static final Logger LOG = LoggerFactory.getLogger(UserAccountsBasedAuthenticatingRealm.class);
    private static final String realmName = "UserAccountsBasedRealm";

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();

        // Null username is invalid
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }

        AuthenticationInfo info = null;
        try {

            SimpleFinder<UserAccount,Long> userAccountsFinder = (SimpleFinder<UserAccount,Long>) BeanFactoryProvider.getBeanFactory().getBean("userAccountsWithoutSoftDeletedFinder");
            List<UserAccount> userAccountsList = userAccountsFinder.findAll(userAccountHasLogin(upToken.getUsername()));
            if(userAccountsList.size()>1){
                throw new RuleViolationBusinessException("Login must be unique and belong to only one user account in repository");
            }
            if (userAccountsList.size()==1) {
                SimpleAuthenticationInfo saInfo = new SimpleAuthenticationInfo(
                        new SimplePrincipalCollection(
                                new UserPrincipal(userAccountsList.get(0).getAccountInfo().getLogin(),
                                        userAccountsList.get(0).getId(), userAccountsList.get(0).getUserGroup()), realmName),
                        userAccountsList.get(0).getAccountInfo().getHashedPassword(),
                        ByteSource.Util.bytes(Base64.decode(userAccountsList.get(0).getAccountInfo().getSalt()))
                );
                return saInfo;
            } else {
                return null;
            }

        } catch (BusinessException e) {
            LOG.error("Exception has occured on getting user info", e);
            throw new AuthenticationException("Exception has occured on getting user info", e);
        }
    }

}