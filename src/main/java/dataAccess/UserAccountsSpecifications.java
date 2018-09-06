package dataAccess;

import domain.IssueType;
import domain.IssueType_;
import domain.userAccounts.AccountInfo_;
import domain.userAccounts.UserAccount;
import domain.userAccounts.UserAccount_;
import domain.userAccounts.UserGroup;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserAccountsSpecifications {

    public static Specification<UserAccount> userAccountHasLogin(String login) {
        return new Specification<UserAccount> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.equal(root.get(UserAccount_.accountInfo).get(AccountInfo_.login), login);
            }
        };
    }

    public static Specification<UserAccount> userAccountBelongToOneOfGroups(UserGroup[] userGroups) {
        return new Specification<UserAccount> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return root.get(UserAccount_.userGroup).in(userGroups);
            }
        };
    }
}
