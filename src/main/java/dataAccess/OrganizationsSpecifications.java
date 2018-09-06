package dataAccess;

import common.argumentAssert.Assert;
import domain.Issue;
import domain.Organization;
import domain.Organization_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

public class OrganizationsSpecifications {

    public static Specification<Organization> organizationsHasType(Organization.Type organizationType) {
        return new Specification<Organization> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.equal(root.get(Organization_.type), organizationType);
            }
        };
    }

    public static Specification<Organization> organizationsHasAtLeastOneResponsiblePerson() {
        return new Specification<Organization> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.isNotEmpty(root.get(Organization_.responsiblePeople));
            }
        };
    }
}
