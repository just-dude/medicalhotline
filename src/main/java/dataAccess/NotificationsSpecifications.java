package dataAccess;

import domain.common.notifications.Notification;
import domain.common.notifications.NotificationContent;
import domain.common.notifications.NotificationContent_;
import domain.common.notifications.Notification_;
import domain.userAccounts.UserAccount_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;

public class NotificationsSpecifications {

   public static Specification<Notification> notificationHasTargetUserAccount(long targetUserAccountId) {
        return new Specification<Notification> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.equal(root.get(Notification_.targetUserAccount).get(UserAccount_.id), targetUserAccountId);
            }
        };
    }

    public static Specification<Notification> notificationNotHasBeenRead() {
        return new Specification<Notification> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.equal(root.get(Notification_.hasBeenRead),false);
            }
        };
    }

    public static Specification<Notification> notificationCreatedAfter(LocalDateTime dateTime) {
        return new Specification<Notification> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.greaterThanOrEqualTo(root.join(Notification_.notificationContent).get(NotificationContent_.createdDateTime), dateTime);
            }
        };
    }
}
