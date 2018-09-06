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

public class IssuesSpecifications {

    public static Specification<Issue> issueHasId(Long issueId) {
        return new Specification<Issue> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.equal(root.get(Issue_.id), issueId);
            }
        };
    }

    public static Specification<Issue> issuesBelongsToAppeal(long appealId) {
        return new Specification<Issue> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.equal(root.get(Issue_.appeal), appealId);
            }
        };
    }

    public static Specification<Issue> issueHasStates(List<Issue.State> issueState) {
        return new Specification<Issue> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return root.get(Issue_.state).in(issueState.toArray());
            }
        };
    }

    public static Specification<Issue> issueHasCreatorWithId(long creatorId) {
        return new Specification<Issue> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.equal(root.get(Issue_.appeal).get(Appeal_.creator), creatorId);
            }
        };
    }

    public static Specification<Issue> issueCreatedInPeriodOf(LocalDateTime startPeriodDateTime,LocalDateTime endPeriodDateTime) {
        return new Specification<Issue> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.between(root.get(Issue_.appeal).get(Appeal_.createdDateTime),startPeriodDateTime,endPeriodDateTime);
            }
        };
    }

    public static Specification<Issue> issueHasTypesWithIds(List<Long> issueTypesIds) {
        return new Specification<Issue> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                Assert.notNull(issueTypesIds,"issueTypesIds");
                Assert.notEmpty(issueTypesIds,"issueTypesIds");
                //query.groupBy(root);
                query.distinct(true);
                return root.join(Issue_.issueTypes).in(issueTypesIds);
            }
        };
    }

    public static Specification<Issue> issueHasOrgnizationsWithIds(List<Long> organizationsIds) {
        return new Specification<Issue> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                Assert.notNull(organizationsIds,"organizationsIds");
                Assert.notEmpty(organizationsIds,"organizationsIds");
                //query.groupBy(root);
                query.distinct(true);
                return root.join(Issue_.organizations).in(organizationsIds);
            }
        };
    }

    public static Specification<Issue> issueBelongsToOrganization(Long organizationId) {
        return new Specification<Issue> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                Assert.notNull(organizationId,"organizationId");
                return cb.equal(root.join(Issue_.organizations).get(Organization_.id),organizationId);
            }
        };
    }

    public static Specification<Issue> issueHasCategoriesIds(List<Long> categoriesIds) {
        return new Specification<Issue> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                Assert.notNull(categoriesIds,"categoriesIds");
                Assert.notEmpty(categoriesIds,"categoriesIds");
                //query.distinct(true);
                return root.get(Issue_.issueCategory).in(categoriesIds);
            }
        };
    }

}
