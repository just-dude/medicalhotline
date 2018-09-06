package domain.common.notifications;

import domain.common.HavingLongIdDomainObject;
import domain.userAccounts.UserAccount;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Table(name = "Notifications")
public class Notification extends HavingLongIdDomainObject<Notification> {

    @ManyToOne(fetch = FetchType.EAGER,optional = false,targetEntity = NotificationContent.class)
    @JoinColumn(name = "notificationContentId", referencedColumnName = "id" ,nullable = false)
    @Fetch(FetchMode.JOIN)
    private NotificationContent notificationContent;


    @ManyToOne(fetch = FetchType.EAGER,optional = false,targetEntity = UserAccount.class)
    @JoinColumn(name = "targetUserAccountId", referencedColumnName = "id" ,nullable = false)
    @Fetch(FetchMode.JOIN)
    private UserAccount targetUserAccount;

    private boolean hasBeenRead=false;


    public Notification() {
    }

    public Notification(NotificationContent notificationContent, UserAccount targetUserAccount) {
        this.notificationContent = notificationContent;
        this.targetUserAccount = targetUserAccount;
    }

    public NotificationContent getNotificationContent() {
        return notificationContent;
    }

    public UserAccount getTargetUserAccount() {
        return targetUserAccount;
    }

    public boolean hasBeenRead() {
        return hasBeenRead;
    }

    public void setHasBeenRead(){
        hasBeenRead=true;
    }



    @Transactional
    public void setHasBeenReadAndSave(){
        Notification self=findNotSoftDeletedSelf();
        self.setHasBeenRead();
        self.doSave();
    }




}
