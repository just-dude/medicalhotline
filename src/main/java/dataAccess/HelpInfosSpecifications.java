package dataAccess;

import domain.HelpInfo;
import domain.HelpInfo_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class HelpInfosSpecifications {

    public static Specification<HelpInfo> helpInfosHasIssueType(Long issueTypeId) {
        return new Specification<HelpInfo> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.equal(root.get(HelpInfo_.issueType), issueTypeId);
            }
        };
    }
}
