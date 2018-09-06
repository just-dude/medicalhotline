package dataAccess;

import domain.Organization;
import domain.common.stateChangesAccounting.EntityFieldPrevState;
import domain.common.stateChangesAccounting.EntityFieldPrevState_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class EntitiesFieldsPrevStatesSpecifications {

    public static Specification<EntityFieldPrevState> entityFieldPrevStatesHaveEntityNameAndEntityId(String entityName,String entityId) {
        return new Specification<EntityFieldPrevState> (){
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.and(cb.equal(root.get(EntityFieldPrevState_.id).get("entityName"), entityName),cb.equal(root.get(EntityFieldPrevState_.id).get("entityId"), entityId));
            }
        };
    }
}
