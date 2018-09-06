package controller.struts.action.main.notifications;

import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.PageableAction;
import domain.Organization;
import domain.common.SimpleFinder;
import domain.common.notifications.Notification;
import domain.common.notifications.NotificationContent;
import domain.security.SecuritySubjectUtils;
import domain.userAccounts.UserAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static dataAccess.NotificationsSpecifications.notificationHasTargetUserAccount;

public class NotificationsManagementAction extends PageableAction<Notification> {

    protected static final Logger LOG = LogManager.getLogger(NotificationsManagementAction.class);

    @Override
    protected Page<Notification> loadPage() {
        SecuritySubjectUtils.checkAuthenticated();
        UserAccount userAccount=SecuritySubjectUtils.getCurrentUserAccount();
        SimpleFinder<Notification, Long> notificationsFinder = (SimpleFinder<Notification, Long>) BeanFactoryProvider.getBeanFactory().getBean("notificationsWithoutSoftDeletedFinder");
        Page<Notification> notificationPage=notificationsFinder.findAll(notificationHasTargetUserAccount(userAccount.getId()),
                new PageRequest(pageNumber - 1, entitiesPerPage,
                new Sort(new Sort.Order(Sort.Direction.ASC,"hasBeenRead"),new Sort.Order(Sort.Direction.DESC,"id"))
        ));
        for(Notification notification:notificationPage.getContent()){
            notification.setHasBeenReadAndSave();
        }
        for(Notification notification:notificationPage.getContent()){
            NotificationContent content=notification.getNotificationContent();
            String[] textParts=content.getText().split("[|]");
            String[] linkParts=content.getLink().split("[|]");
            content.setText(getText(textParts[0], Arrays.copyOfRange(textParts,1,textParts.length)));
            content.setLink(getText(linkParts[0], Arrays.copyOfRange(linkParts,1,linkParts.length)));
        }
        return notificationPage;
    }

    @Override
    protected String getPermissionsStringForCheck() {
        return null;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
