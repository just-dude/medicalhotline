package dataAccess;

import common.argumentAssert.Assert;
import domain.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

public class IssueActionsSpecifications {

    public static Specification<IssueAction> issueActionHasId(Long issueActionId) {
        return new Specification<IssueAction> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.equal(root.get(IssueAction_.id), issueActionId);
            }
        };
    }

    public static Specification<IssueAction> issueActionHasCreatorWithId(long creatorId) {
        return new Specification<IssueAction> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.equal(root.get(IssueAction_.creator), creatorId);
            }
        };
    }

    public static Specification<IssueAction> issueActionAssociatedWithOrganization(Long organizationId) {
        return new Specification<IssueAction> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                Assert.notNull(organizationId,"organizationId");
                return cb.equal(root.join(IssueAction_.issue).join(Issue_.organizations).get(Organization_.id),organizationId);
            }
        };
    }

}
