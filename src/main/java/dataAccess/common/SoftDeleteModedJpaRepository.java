package dataAccess.common;


import dataAccess.common.exception.EntityWithSuchIdDoesNotExistsDataAccessException;
import dataAccess.common.exception.EntityWithSuchIdIsSoftDeletedDataAccessException;
import domain.common.DomainObject;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.*;

public class SoftDeleteModedJpaRepository<T extends DomainObject, ID extends Serializable> extends SimpleJpaRepository<T, ID> {

    public enum FindMode {
        WithSoftDeleted, WithoutSoftDeleted, OnlySoftDeleted
    }

    protected static final String IS_SOFT_DELETED_ATTRIBUTE_NAME="isSoftDeleted";
    protected FindMode findMode;

    public SoftDeleteModedJpaRepository(EntityManager em, Class<T> entityClass) {
        super(em, entityClass);
        this.findMode = FindMode.WithSoftDeleted;
    }

    public SoftDeleteModedJpaRepository(EntityManager em, Class<T> entityClass, FindMode findMode) {
        super(em, entityClass);
        this.findMode = findMode;
    }

    @Override
    protected void buildWhereClauseOnCriteriaQuery(Root root, CriteriaBuilder cb, CriteriaQuery cq, Predicate... inputPredicates){
        List<Predicate> predicateList = new ArrayList<>();
        if(inputPredicates!=null && inputPredicates.length>0){
            for(Predicate p: inputPredicates){
                predicateList.add(p);
            }
        }
        if(!findMode.equals(FindMode.WithSoftDeleted)) {
            if(findMode.equals(FindMode.OnlySoftDeleted)) {
                predicateList.add(cb.equal(root.get(IS_SOFT_DELETED_ATTRIBUTE_NAME),true ));
            } else{
                predicateList.add(cb.or(cb.equal(root.get(IS_SOFT_DELETED_ATTRIBUTE_NAME),false ),cb.isNull(root.get(IS_SOFT_DELETED_ATTRIBUTE_NAME))));
            }
        }
        if(predicateList.size()>0){
            cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
        }
    }


    @Override
    public T findOne(ID id,Node<String> fetchTree) throws EntityWithSuchIdDoesNotExistsDataAccessException {
        T result=super.findOne(id,fetchTree);
        if(FindMode.WithoutSoftDeleted.equals(findMode) && result.isSoftDeleted()) {
            throw new EntityWithSuchIdIsSoftDeletedDataAccessException(id);
        }
        return result;
    }
}
