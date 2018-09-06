/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.authorization;

import common.beanFactory.BeanFactoryProvider;
import domain.Issue;
import domain.IssueAction;
import domain.ResponsiblePersonUserAccount;
import domain.common.SimpleFinder;
import domain.security.SecuritySubjectUtils;
import domain.security.UserPrincipal;
import domain.security.authorization.AbstractAuthorizingRealm;
import domain.userAccounts.UserAccount;
import domain.userAccounts.UserGroup;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleRole;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.data.jpa.domain.Specifications;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static dataAccess.IssueActionsSpecifications.issueActionAssociatedWithOrganization;
import static dataAccess.IssueActionsSpecifications.issueActionHasCreatorWithId;
import static dataAccess.IssueActionsSpecifications.issueActionHasId;
import static dataAccess.IssuesSpecifications.issueBelongsToOrganization;
import static dataAccess.IssuesSpecifications.issueHasCreatorWithId;
import static dataAccess.IssuesSpecifications.issueHasId;

/**
 * @author SuslovAI
 */

public class IssueActionsAuthorizingRealm extends AbstractAuthorizingRealm {

    protected String domainObjectName;

    public IssueActionsAuthorizingRealm() {
        this.domainObjectName = "issueaction";
    }

    @Override
    protected Map<UserGroup, SimpleRole> getUserGroupToRoleMap(UserPrincipal userPrincipal) {
        Map<UserGroup, SimpleRole> userGroupToRoleMap = new HashMap<>();
        SimpleRole allAllowedRole=new SimpleRole("allAllowedRole", new HashSet( Arrays.asList(
                new WildcardPermission(this.domainObjectName+":*:*:*"))));
        SimpleRole crudAllowedRole=new SimpleRole("crudAllowedRole", new HashSet( Arrays.asList(
                new WildcardPermission(this.domainObjectName+":view:body:*"),
                new WildcardPermission(this.domainObjectName+":add:*:*"),
                new CrudRoleForIssueActionPermission()
        )));
        SimpleRole responsiblePersonRole=new SimpleRole("responsiblePersonRole", new HashSet( Arrays.asList(
                new ResponsiblePersonForIssueActionPermission()
        )));
        userGroupToRoleMap.put(UserGroup.Admin,allAllowedRole);
        userGroupToRoleMap.put(UserGroup.ShiftSupervisor,allAllowedRole);
        userGroupToRoleMap.put(UserGroup.Operator,crudAllowedRole);
        userGroupToRoleMap.put(UserGroup.ResponsiblePerson,responsiblePersonRole);
        return userGroupToRoleMap;
    }

    private static class ResponsiblePersonForIssueActionPermission implements Permission {

        @Override
        public boolean implies(Permission permission) {
            if(!(permission instanceof WildcardPermission)){
                throw new IllegalArgumentException("Only WildcardPermission supported by ResponsiblePersonForIssueActionPermission");
            }
            WildcardPermission wp = (WildcardPermission) permission;
            // issueaction:view:body:1 - template for check permission
            if(WildcardPermissionsUtils.isWildcardStartsWith(wp,"issueaction:view:body:")){
                String issueActionIdStr=WildcardPermissionsUtils.getWildcardPartValue(wp,3);
                if(issueActionIdStr==null){
                    return false;
                }
                try{
                    Long issueActionId = Long.parseLong(issueActionIdStr);
                    Long organizationId = ((ResponsiblePersonUserAccount)SecuritySubjectUtils.getCurrentUserAccount()).getOrganization().getId();
                    SimpleFinder<IssueAction,Long> finder = (SimpleFinder<IssueAction,Long>) BeanFactoryProvider.getBeanFactory().getBean("issueActionsFinder");
                    boolean isPermitted=finder.hasAtLeastOne(
                            Specifications.where(issueActionAssociatedWithOrganization(organizationId)).and(issueActionHasId(issueActionId)));
                    return isPermitted;
                } catch (NumberFormatException e){
                    return false;
                }
            }
            // issueaction:add:text:1 - template for check permission
            if(WildcardPermissionsUtils.isWildcardStartsWith(wp,"issueaction:add:text:")){
                String issueIdStr=WildcardPermissionsUtils.getWildcardPartValue(wp,3);
                if(issueIdStr==null){
                    return false;
                }
                try{
                    Long issueId = Long.parseLong(issueIdStr);
                    UserAccount userAccount = SecuritySubjectUtils.getCurrentUserAccount();
                    if(!(userAccount instanceof ResponsiblePersonUserAccount)){
                        return false;
                    }
                    Long organizationId = ((ResponsiblePersonUserAccount)userAccount).getOrganization().getId();
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

    private static class CrudRoleForIssueActionPermission implements Permission {

        @Override
        public boolean implies(Permission permission) {
            if(!(permission instanceof WildcardPermission)){
                throw new IllegalArgumentException("Only WildcardPermission supported by ResponsiblePersonForIssueActionPermission");
            }
            WildcardPermission wp = (WildcardPermission) permission;
            // issueaction:edit:*:1 - template for check permission
            if(WildcardPermissionsUtils.isWildcardStartsWith(wp,"issueaction:edit:*:")){
                String issueActionIdStr=WildcardPermissionsUtils.getWildcardPartValue(wp,3);
                if(issueActionIdStr==null){
                    return false;
                }
                try{
                    Long issueActionId = Long.parseLong(issueActionIdStr);
                    Long currentUserAccountId = SecuritySubjectUtils.getCurrentUserAccount().getId();
                    SimpleFinder<IssueAction,Long> finder = (SimpleFinder<IssueAction,Long>) BeanFactoryProvider.getBeanFactory().getBean("issueActionsFinder");
                    boolean isPermitted=finder.hasAtLeastOne(
                            Specifications.where(issueActionHasCreatorWithId(currentUserAccountId)).and(issueActionHasId(issueActionId)));
                    return isPermitted;
                } catch (NumberFormatException e){
                    return false;
                }
            }
            return false;
        }
    }



}
