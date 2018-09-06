package domain.common.notifications;

import common.DateTimeUtils;
import domain.common.HavingLongIdDomainObject;
import domain.security.SecuritySubjectUtils;
import domain.userAccounts.UserAccount;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Артем on 18.03.2018.
 */
@Entity
@Table(name = "NotificationContents")
public class NotificationContent extends HavingLongIdDomainObject<NotificationContent> {

    @Lob
    @Column
    private String text;

    @Lob
    @Column
    private String link;

    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = FetchType.EAGER,optional = false,targetEntity = UserAccount.class)
    @JoinColumn(name = "creatorId", referencedColumnName = "id" ,nullable = false)
    @Fetch(FetchMode.JOIN)
    private UserAccount creator;

    public NotificationContent() {
    }

    public NotificationContent(Long id) {
        super(id);
    }

    public NotificationContent(String text, String link) {
        this.text = text;
        this.link = link;
    }

    public String getText() {
        return text;
    }

    public String getLink() {
        return link;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public UserAccount getCreator() {
        return creator;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    protected void beforeInsert() {
        super.beforeInsert();
        createdDateTime=DateTimeUtils.now();
        creator=SecuritySubjectUtils.getCurrentUserAccount();
    }
}
