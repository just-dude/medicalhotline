/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.security.authorization;

import domain.security.UserPrincipal;
import domain.userAccounts.UserGroup;
import org.apache.shiro.authz.SimpleRole;
import org.apache.shiro.authz.permission.WildcardPermission;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author SuslovAI
 */

public class AllUserGroupsHasPermissionsAuthorizingRealm extends AbstractAuthorizingRealm {

    protected String domainObjectName;

    public AllUserGroupsHasPermissionsAuthorizingRealm(String domainObjectName) {
        this.domainObjectName = domainObjectName;
    }

    @Override
    protected Map<UserGroup, SimpleRole> getUserGroupToRoleMap(UserPrincipal userPrincipal) {
        Map<UserGroup, SimpleRole> userGroupToRoleMap = new HashMap<>();
        SimpleRole allAllowedRole=new SimpleRole("allAllowedRole", new HashSet( Arrays.asList( new WildcardPermission(this.domainObjectName+":*:*:*"))));
        for(UserGroup userGroup: UserGroup.values()){
            userGroupToRoleMap.put(userGroup,allAllowedRole);
        }
        return userGroupToRoleMap;
    }

}
