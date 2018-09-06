package dataAccess;

import domain.Organization;
import domain.ResponsiblePerson;
import domain.ResponsiblePerson_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ResponsiblePersonsSpecifications {

    public static Specification<ResponsiblePerson> responsiblePersonsBelongsToCategory(Long organizationId) {
        return new Specification<ResponsiblePerson> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.equal(root.get(ResponsiblePerson_.organization), organizationId);
            }
        };
    }

}
