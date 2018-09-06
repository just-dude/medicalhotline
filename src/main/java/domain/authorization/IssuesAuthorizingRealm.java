/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.authorization;

import common.beanFactory.BeanFactoryProvider;
import domain.Issue;
import domain.ResponsiblePerson;
import domain.ResponsiblePersonUserAccount;
import domain.common.SimpleFinder;
import domain.security.SecuritySubjectUtils;
import domain.security.UserPrincipal;
import domain.security.authorization.AbstractAuthorizingRealm;
import domain.userAccounts.UserGroup;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleRole;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.data.jpa.domain.Specifications;

import java.util.*;

import static dataAccess.IssuesSpecifications.issueBelongsToOrganization;
import static dataAccess.IssuesSpecifications.issueHasId;

/**
 * @author SuslovAI
 */

public class IssuesAuthorizingRealm extends AbstractAuthorizingRealm {

    protected String domainObjectName;

    public IssuesAuthorizingRealm() {
        this.domainObjectName = "issue";
    }

    @Override
    protected Map<UserGroup, SimpleRole> getUserGroupToRoleMap(UserPrincipal userPrincipal) {
        Map<UserGroup, SimpleRole> userGroupToRoleMap = new HashMap<>();
        SimpleRole allAllowedRole=new SimpleRole("allAllowedRole", new HashSet( Arrays.asList(
                new WildcardPermission(this.domainObjectName+":viewOne:body,changesHistory,organizationsInfo,helpInfo,helpInfoByOrganization:*"),
                new WildcardPermission(this.domainObjectName+":add,edit,gotostate,viewMany:*:*")
        )));
        SimpleRole crudAllowedRole=new SimpleRole("crudAllowedRole", new HashSet( Arrays.asList(
                new WildcardPermission(this.domainObjectName+":viewOne:body,organizationsInfo,helpInfo,helpInfoByOrganization:*"),
                new WildcardPermission(this.domainObjectName+":add,edit,gotostate,viewMany:*:*")
        )));
        SimpleRole responsiblePersonRole=new SimpleRole("responsiblePersonRole", new HashSet( Arrays.asList(
                new WildcardPermission(this.domainObjectName+":viewMany:byorganization:?"),
                new WildcardPermission(this.domainObjectName+":viewOne:appeal,body:?"),
                new ResponsiblePersonForIssuePermission()
        )));
        userGroupToRoleMap.put(UserGroup.Admin,allAllowedRole);
        userGroupToRoleMap.put(UserGroup.ShiftSupervisor,allAllowedRole);
        userGroupToRoleMap.put(UserGroup.Operator,crudAllowedRole);
        userGroupToRoleMap.put(UserGroup.ResponsiblePerson,responsiblePersonRole);
        return userGroupToRoleMap;
    }

    public static class ResponsiblePersonForIssuePermission implements Permission {

        @Override
        public boolean implies(Permission permission) {
            if(!(permission instanceof WildcardPermission)){
                throw new IllegalArgumentException("Only WildcardPermission supported by ResponsiblePersonForIssuePermission");
            }
            WildcardPermission wp = (WildcardPermission) permission;
            // issue:viewOneid:1 -template for check permission
            if(WildcardPermissionsUtils.isWildcardStartsWith(wp,"issue:viewOne:body:")){
                String issueIdStr=WildcardPermissionsUtils.getWildcardPartValue(wp,3);
                if(issueIdStr==null){
                    return false;
                }
                try{
                    Long issueId = Long.parseLong(issueIdStr);
                    Long organizationId = ((ResponsiblePersonUserAccount)SecuritySubjectUtils.getCurrentUserAccount()).getOrganization().getId();
                    SimpleFinder<Issue,Long> finder = (SimpleFinder<Issue,Long>) BeanFactoryProvider.getBeanFactory().getBean("issuesFinder");
                    boolean isPermitted=finder.hasAtLeastOne(
                            Specifications.where(issueBelongsToOrganization(organizationId)).and(issueHasId(issueId)));
                    return isPermitted;
                } catch (NumberFormatException e){
                    return false;
                }
            }
            return false;
        }
    }



}
