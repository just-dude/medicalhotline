package domain.common.notifications;

import common.beanFactory.BeanFactoryProvider;
import domain.common.SimpleFinder;
import domain.userAccounts.UserAccount;
import domain.userAccounts.UserGroup;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static dataAccess.UserAccountsSpecifications.userAccountBelongToOneOfGroups;

public class NotificationsService {

    @Transactional
    public void addForUserAccountsByGroup(NotificationContent notificationContent, UserGroup[] group){
        NotificationContent savedNotificationContent=notificationContent.save();
        SimpleFinder<UserAccount, Long> finder = (SimpleFinder<UserAccount, Long>) BeanFactoryProvider.getBeanFactory().getBean("userAccountsWithoutSoftDeletedFinder");
        List<UserAccount> userAccounts = finder.findAll(userAccountBelongToOneOfGroups(group));
        userAccounts.forEach(userAccount -> new Notification(savedNotificationContent,userAccount).save());
    }




}
