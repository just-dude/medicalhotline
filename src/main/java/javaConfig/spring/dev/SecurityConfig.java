package javaConfig.spring.dev;


import domain.authorization.AdminAndShiftSupervisorHasPermissionsAuthorizingRealm;
import domain.authorization.IssueActionsAuthorizingRealm;
import domain.authorization.IssuesAuthorizingRealm;
import domain.authorization.StandardAuthorizingRealm;
import domain.security.SecuritySubjectUtils;
import domain.security.authentication.UserAccountsBasedAuthenticatingRealm;
import domain.security.authorization.DomainObjectSpecificWildcardPermissionResolver;
import domain.security.authorization.DomainObjectSpecificatedRealmAuthorizer;
import domain.security.authorization.OnlyAdminHasPermissionsAuthorizingRealm;
import domain.userAccounts.authorization.UserAccountsAuthorizingRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SuslovAI on 19.10.2017.
 */
public class SecurityConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        shiroFilter.setLoginUrl("/login-input.html");

        //shiroFilter.setFilterChainDefinitions("/login.html = anon");
        shiroFilter.setFilterChainDefinitions("/admin/** = authc, perms[\"adminpanel:view:*:*\"]");
        //shiroFilter.setFilterChainDefinitions("* = authc");
        return shiroFilter;
    }

    @Bean
    public SecurityManager securityManager(DomainObjectSpecificatedRealmAuthorizer domainObjectSpecificatedRealmAuthorizer, SessionManager sessionManager,
                                           UserAccountsBasedAuthenticatingRealm userAccountsBasedAuthenticatingRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(
                Arrays.asList(
                        domainObjectSpecificatedRealmAuthorizer,
                        userAccountsBasedAuthenticatingRealm
                        // add new authentication realms here
                )
        );
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setGlobalSessionTimeout(2L*3600000L); // 2 hours
        return sessionManager;
    }

    @Bean
    public DomainObjectSpecificatedRealmAuthorizer domainObjectSpecificatedRealmAuthorizer() {
        Map<String, Realm> realmsMap = new HashMap();
        // write realm keys in lower case!
        realmsMap.put("adminpanel", new OnlyAdminHasPermissionsAuthorizingRealm("adminpanel"));
        realmsMap.put("useraccount", new UserAccountsAuthorizingRealm());
        realmsMap.put("appeal", new StandardAuthorizingRealm("appeal"));
        realmsMap.put("issue", new IssuesAuthorizingRealm());
        realmsMap.put("issueaction", new IssueActionsAuthorizingRealm());
        realmsMap.put("issuecategory", new AdminAndShiftSupervisorHasPermissionsAuthorizingRealm("issuecategory"));
        realmsMap.put("issuetype", new AdminAndShiftSupervisorHasPermissionsAuthorizingRealm("issuetype"));
        realmsMap.put("organization", new AdminAndShiftSupervisorHasPermissionsAuthorizingRealm("organization"));
        realmsMap.put("responsibleperson", new AdminAndShiftSupervisorHasPermissionsAuthorizingRealm("responsibleperson"));
        realmsMap.put("helpinfo", new AdminAndShiftSupervisorHasPermissionsAuthorizingRealm("helpinfo"));
        realmsMap.put("helpinfo-by-organization", new AdminAndShiftSupervisorHasPermissionsAuthorizingRealm("helpinfo-by-organization"));
        // add new authorizing realms here!
        DomainObjectSpecificatedRealmAuthorizer domainObjectSpecificatedRealmAuthorizer = new DomainObjectSpecificatedRealmAuthorizer(realmsMap);
        domainObjectSpecificatedRealmAuthorizer.setPermissionResolver(new DomainObjectSpecificWildcardPermissionResolver());
        return domainObjectSpecificatedRealmAuthorizer;
    }

    @Bean
    public SecuritySubjectUtils securitySubject() {
        return new SecuritySubjectUtils();
    }

    @Bean
    public UserAccountsBasedAuthenticatingRealm userAccountsBasedAuthenticatingRealm() {
        UserAccountsBasedAuthenticatingRealm realm = new UserAccountsBasedAuthenticatingRealm();
        HashedCredentialsMatcher hcm = new HashedCredentialsMatcher();
        hcm.setHashAlgorithmName("SHA-256");
        hcm.setHashIterations(256);
        hcm.setStoredCredentialsHexEncoded(false);
        realm.setCredentialsMatcher(hcm);
        return realm;
    }

}
