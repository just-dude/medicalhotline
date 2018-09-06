package dataAccess;

import domain.IssueType;
import domain.IssueType_;
import domain.Organization;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class IssueTypesSpecifications {

    public static Specification<IssueType> issueTypesBelongsToCategory(Long issueCategoryId) {
        return new Specification<IssueType> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.equal(root.get(IssueType_.issueCategory), issueCategoryId);
            }
        };
    }
}
