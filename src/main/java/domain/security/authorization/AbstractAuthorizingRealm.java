/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.security.authorization;

import domain.security.UserPrincipal;
import domain.userAccounts.UserGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.SimpleRole;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

/**
 * @author SuslovAI
 */
public abstract class AbstractAuthorizingRealm extends AuthorizingRealm {

    private static final Logger LOG = LogManager.getLogger(AbstractAuthorizingRealm.class.getName());

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
        LOG.debug(String.format("doGetAuthorizationInfo principals: %s ", pc.asList()));
        UserPrincipal userPrincipal = null;
        SimpleRole currentUserRole = getUnauthenticatedUserRole();
        if (!pc.isEmpty()) {
            userPrincipal = pc.oneByType(UserPrincipal.class);
            currentUserRole = getRoleByUserGroup(userPrincipal);
        }
        LOG.debug(String.format("Current user role is %s ", currentUserRole.getName()));
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(new HashSet(Arrays.asList(currentUserRole.getName())));
        info.addObjectPermissions(currentUserRole.getPermissions());
        LOG.debug(String.format("SimpleAuthorizationInfo: roles %s, permissionStringConsts %s; by principals: %s ", info.getRoles(), info.getObjectPermissions(), pc.asList()));
        return info;
    }

    protected SimpleRole getUnauthenticatedUserRole() {
        return new SimpleRole("UnauthenticatedUser", new HashSet(Arrays.asList(new NotOnePermission())));
    }

    protected SimpleRole getRoleByUserGroup(UserPrincipal userPrincipal) {
        Map<UserGroup, SimpleRole> userGroupSimpleRoleMap = getUserGroupToRoleMap(userPrincipal);
        if (userGroupSimpleRoleMap.containsKey(userPrincipal.getUserGroup())) {
            return userGroupSimpleRoleMap.get(userPrincipal.getUserGroup());
        } else {
            return new SimpleRole("UnknownUserGroupWithoutPermissions", new HashSet(Arrays.asList(new NotOnePermission())));
        }
    }

    protected abstract Map<UserGroup, SimpleRole> getUserGroupToRoleMap(UserPrincipal userPrincipal);

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken at) throws AuthenticationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean supports(AuthenticationToken at) {
        return false;
    }

}
