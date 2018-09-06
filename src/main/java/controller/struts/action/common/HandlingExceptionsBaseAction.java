
package controller.struts.action.common;


import common.DateTimeUtils;
import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.util.ActionUtils;
import domain.common.SimpleFinder;
import domain.common.exception.*;
import domain.common.notifications.Notification;
import domain.common.notifications.NotificationContent;
import domain.common.util.DomainObjectUtils;
import domain.security.SecuritySubjectUtils;
import domain.userAccounts.UserAccount;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.domain.Specifications;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static dataAccess.NotificationsSpecifications.notificationCreatedAfter;
import static dataAccess.NotificationsSpecifications.notificationHasTargetUserAccount;
import static dataAccess.NotificationsSpecifications.notificationNotHasBeenRead;

public abstract class HandlingExceptionsBaseAction extends BaseAction {

    @Override
    public String execute() throws Exception {
        try {
            clearMessages();
            getLogger().debug("HandlingExceptionsBaseAction start execute");
            String result=doExecute();
            getLogger().debug("HandlingExceptionsBaseAction executed success " + ActionUtils.getDecoratedByViewSettingsResult(result));
            return ActionUtils.getDecoratedByViewSettingsResult(result);
        } catch (ValidationFailedException e) {
            getLogger().debug("INVALID_USER_INPUT result", e);
            fillActionErrors(e.getConstraintsViolations());
            addActionError(getText(CustomResults.INVALID_USER_INPUT));
            return ActionUtils.getDecoratedByViewSettingsResult(CustomResults.INVALID_USER_INPUT);
        } catch (EntityWithSuchIdDoesNotExistsBusinessException e) {
            getLogger().debug("RESOURCE_WITH_SUCH_ID_NOT_EXISTS result", e);
            addActionError(getText(CustomResults.RESOURCE_WITH_SUCH_ID_NOT_EXISTS));
            return ActionUtils.getDecoratedByViewSettingsResult(CustomResults.ERROR);
        } catch (EntityWithSuchIdIsSoftDeletedBusinessException e) {
            getLogger().debug("RESOURCE_WITH_SUCH_ID_SOFT_DELETED result", e);
            addActionError(getText(CustomResults.RESOURCE_WITH_SUCH_ID_SOFT_DELETED));
            return ActionUtils.getDecoratedByViewSettingsResult(CustomResults.ERROR);
        } catch (AuthorizationFailedException e) {
            getLogger().debug("AUTHORIZATION_ERROR result", e);
            addActionError(getText(CustomResults.AUTHORIZATION_ERROR));
            return ActionUtils.getDecoratedByViewSettingsResult(CustomResults.AUTHORIZATION_ERROR);
        } catch (UnchangingContentConstraintViolationBusinessException e) {
            getLogger().debug("UNCHANGING_CONTENT_CONSTRAINT_VIOLATION result", e);
            addActionError(getText(CustomResults.UNCHANGING_CONTENT_CONSTRAINT_VIOLATION));
            return ActionUtils.getDecoratedByViewSettingsResult(CustomResults.UNCHANGING_CONTENT_CONSTRAINT_VIOLATION);
        } catch (RuleViolationBusinessException e) {
            getLogger().debug("BUSINESS_RULE_VIOLATION result", e);
            addActionError(getText(e.getUserMessage()));
            return ActionUtils.getDecoratedByViewSettingsResult(CustomResults.BUSINESS_RULE_VIOLATION);
        } catch (Exception e) {
            getLogger().debug("ERROR result");
            getLogger().error("Unknown exception is occured", e);
            addActionError(getText(CustomResults.ERROR));
            return ActionUtils.getDecoratedByViewSettingsResult(CustomResults.ERROR);
        }
    }

    protected abstract String doExecute() throws Exception;

    protected abstract Logger getLogger();

    public long getNotificationsCount(){
        try {
            UserAccount userAccount=SecuritySubjectUtils.getCurrentUserAccount();
            if(userAccount==null){
                return 0;
            }
            SimpleFinder<Notification, Long> notificationsFinder = (SimpleFinder<Notification, Long>) BeanFactoryProvider.getBeanFactory().getBean("notificationsWithoutSoftDeletedFinder");
            return notificationsFinder.count(Specifications.where(notificationHasTargetUserAccount(userAccount.getId())).
                    and(notificationCreatedAfter(DateTimeUtils.now().minus(1L, ChronoUnit.DAYS))).and(notificationNotHasBeenRead()));
        }catch (Exception e){
            getLogger().error(e);
            return 0;
        }
    }

}
