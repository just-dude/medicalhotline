package domain.common;

import common.argumentAssert.Assert;
import common.beanFactory.BeanFactoryProvider;
import dataAccess.common.Node;
import dataAccess.common.Repository;
import dataAccess.common.exception.EntityWithSuchIdDoesNotExistsDataAccessException;
import domain.common.exception.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.transaction.annotation.Transactional;
import smartvalidation.constraintViolation.ConstraintViolation;
import smartvalidation.exception.EntityValidationException;
import smartvalidation.validator.entityValidator.EntityValidator;
import smartvalidation.validator.entityValidator.EntityValidatorFactory;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
public abstract class DomainObject<T extends DomainObject, ID extends Serializable> implements Serializable, Validatable, SavableAndRemovable<T> {

    @Transient
    protected EntityValidatorFactory evf;
    @Transient
    protected List<ConstraintViolation> constraintsViolations;

    @Transient
    protected Repository<T, ID> repository;
    @Transient
    protected Finder<T, ID> finder;

    private Boolean isSoftDeleted=false;

    @Override
    public boolean isValid() throws BusinessException {
        try {
            EntityValidator validator = getEntityValidatorFactory().getValidator((T) this);
            Validatable[] entitiesForValidation=getEntitiesForValidation();
                 boolean isValid=validator.isValid();
            if(!isValid || !isValidEntitiesForValidation(entitiesForValidation)){
                if(!isValid) {
                    constraintsViolations = validator.getConstraintViolations();
                } else{
                    constraintsViolations=new ArrayList<>();
                }
                for(Validatable entity:entitiesForValidation){
                    List<ConstraintViolation> entitiesForValidationConstraintViolations =entity.getConstraintsViolations();
                    if(entitiesForValidationConstraintViolations!=null) {
                        constraintsViolations.addAll(entitiesForValidationConstraintViolations);
                    }
                }
                return false;
            } else {
                constraintsViolations=null;
                return true;
            }
        } catch (EntityValidationException e) {
            throw new BusinessException("On entity validation error has occured", e, getClass().getSimpleName() + ".isValid", this);
        }
    }

    private boolean isValidEntitiesForValidation(Validatable[] entitiesForValidation){
        boolean isValid=true;
        for(Validatable entity:entitiesForValidation){
            if(!entity.isValid()){
                isValid=false;
            }
        }
        return isValid;
    }

    // Override method if have got to validate sub entities
    protected Validatable[] getEntitiesForValidation(){
        return new Validatable[]{};
    }

    @Override
    public List<ConstraintViolation> getConstraintsViolations() throws BusinessException {
        return constraintsViolations;
    }

    protected void ifInvalidThrowValidationFailedException() throws ValidationFailedException, BusinessException {
        try {
            if (!isValid()) {
                throw new ValidationFailedException(getConstraintsViolations());
            }
        } catch (ValidationFailedException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("An error has occured on validation entity", e, this.getClass().getName() + ".ifInvalidThrowValidationFailedException", this);
        }
    }

    @Transactional
    @Override
    public <S extends T> S save() throws DataAccessFailedBuisnessException,ValidationFailedException {
        S result=null;
        ifInvalidThrowValidationFailedException();
        if(isNew()) {
            beforeInsert();
            result=saveInRepository();
            afterInsert(result);
        } else{
            T self=findNotSoftDeletedSelf();
            beforeUpdate(self);
            fillSelfForUpdate(self);
            result=(S) self.doSave();
            afterUpdate(self);
        }
        return result;
    }

    @Transactional
    protected  <S extends T> S doSave() throws DataAccessFailedBuisnessException,ValidationFailedException {
        ifInvalidThrowValidationFailedException();
        return saveInRepository();
    }

    protected void beforeInsert(){}
    protected void afterInsert(T self){}
    protected void beforeUpdate(T self){}
    protected void afterUpdate(T self){}

    // Fill self fields that must be updated in database
    protected void fillSelfForUpdate(T self){    }

    @Override
    public void remove() throws DataAccessFailedBuisnessException, EntityWithSuchIdDoesNotExistsBusinessException, DataIntegrityViolationBusinessException {
        Assert.notNull(getId(),getClass().getSimpleName()+".id");
        try {
            hardRemove();
        } catch (EntityWithSuchIdIsSoftDeletedBusinessException e) {
            softRemove();
        } catch (DataIntegrityViolationBusinessException e) {
            softRemove();
        }
    }


    protected <S extends T> S saveInRepository() throws DataAccessFailedBuisnessException {
        try {
            return (S) getRepository().saveAndFlush((T) this);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new DataAccessFailedBuisnessException("An error has occured on save entity", e, this.getClass().getName() + ".save", this);
        }
    }


    protected String getEntityValidatorFactoryName() {
        String validatorFactorySufix = (isNew()) ? "OnCreateValidatorFactory" : "OnUpdateValidatorFactory";
        return StringUtils.uncapitalize(this.getClass().getSimpleName()) + validatorFactorySufix;
    }

    protected String getRepositoryName() {
        return StringUtils.uncapitalize(translateIntoThePluralForm(this.getClass().getSimpleName())) + "Repository";
    }

    protected String getFinderName() {
        return StringUtils.uncapitalize(translateIntoThePluralForm(this.getClass().getSimpleName())) + "Finder";
    }

    protected String getWithoutSoftDeletedFinderName() {
        return StringUtils.uncapitalize(translateIntoThePluralForm(this.getClass().getSimpleName())) + "WithoutSoftDeletedFinder";
    }

    protected String translateIntoThePluralForm(String source){
        if(source.charAt(source.length()-1)=='y'){
            return source.substring(0,source.length()-1).concat("ies");
        } else{
            return source.concat("s");
        }
    }

    public abstract ID getId();

    protected abstract boolean isNew();

    public final void initializeProperties(Node<String> initTree){
        if(initTree==null || !initTree.hasChildren()){
            return;
        }
        doInitializeProperties(initTree);
    }

    protected void doInitializeProperties(Node<String> initTree){
    }


    protected EntityValidatorFactory<T> getEntityValidatorFactory() {
        if (evf == null) {
            evf = (EntityValidatorFactory<T>) BeanFactoryProvider.getBeanFactory().getBean(getEntityValidatorFactoryName());
            if (evf == null) {
                throw new BusinessException("EntityValidatorFactory with name [" + getEntityValidatorFactoryName() + "] not define in spring context");
            }
        }
        return evf;
    }

    protected Repository<T, ID> getRepository() {
        if (repository == null) {
            repository = (Repository<T, ID>) BeanFactoryProvider.getBeanFactory().getBean(getRepositoryName());
            if (repository == null) {
                throw new BusinessException("DAO for [" + this.getClass().getSimpleName() + "] entity with name [" + getRepositoryName() + "] not define in spring context");
            }
        }
        return repository;
    }

    protected Finder<T, ID> getFinder() {
        return getFinder(getFinderName());
    }

    protected Finder<T, ID> getWithoutSoftDeletedFinder() {
        return getFinder(getWithoutSoftDeletedFinderName());
    }


    private Finder<T, ID> getFinder(String finderName) {
        if (finder == null) {
            finder = (Finder<T, ID>) BeanFactoryProvider.getBeanFactory().getBean(finderName);
            if (finder == null) {
                throw new BusinessException("Finder for [" + this.getClass().getSimpleName() + "] entity with name [" + getRepositoryName() + "] not define in spring context");
            }
        }
        return finder;
    }


    public Boolean isSoftDeleted() {
        return (isSoftDeleted==null)?false:isSoftDeleted;
    }

    @Transactional
    public void hardRemove() throws DataAccessFailedBuisnessException, EntityWithSuchIdDoesNotExistsBusinessException, DataIntegrityViolationBusinessException {
        Assert.notNull(getId(),getClass().getSimpleName()+".id");
        try {
            T self=findSelf();
            self.doHardRemove();
        } catch (EntityWithSuchIdDoesNotExistsDataAccessException e) {
            throw new EntityWithSuchIdDoesNotExistsBusinessException(e.getId());
        } catch (EntityWithSuchIdIsSoftDeletedBusinessException e) {
            throw e;
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationBusinessException(e);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new DataAccessFailedBuisnessException("An error has occured on remove entity", e, this.getClass().getName() + ".remove", this);
        }
    }


    @Transactional
    public void softRemove() throws DataAccessFailedBuisnessException, EntityWithSuchIdDoesNotExistsBusinessException, DataIntegrityViolationBusinessException {
        Assert.notNull(getId(),getClass().getSimpleName()+".id");
        try {
            T self=findSelf();
            if(!self.isSoftDeleted()){
                self.doSoftRemove();
            }
        } catch (EntityWithSuchIdDoesNotExistsDataAccessException e) {
            throw new EntityWithSuchIdDoesNotExistsBusinessException(e.getId());
        } catch (EntityWithSuchIdIsSoftDeletedBusinessException e) {
            throw e;
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationBusinessException(e);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new DataAccessFailedBuisnessException("An error has occured on remove entity", e, this.getClass().getName() + ".remove", this);
        }
    }

    protected void doHardRemove() throws JpaSystemException {
        beforeHardRemove();
        getRepository().deleteAndFlush((T)this);
        afterHardRemove();
    }


    protected void doSoftRemove(){
        beforeSoftRemove();
        setSoftDeleted();
        saveInRepository();
        afterSoftRemove();
    }

    protected void beforeHardRemove(){} // Override method if have got to remove sub entities
    protected void afterHardRemove(){}
    protected void beforeSoftRemove(){} // Override method if have got to remove sub entities
    protected void afterSoftRemove(){}

    private void setSoftDeleted() {
        isSoftDeleted = true;
    }

    private void unsetSoftDeleted() {
        isSoftDeleted = false;
    }

    protected T findNotSoftDeletedSelf() throws EntityWithSuchIdDoesNotExistsBusinessException,EntityWithSuchIdIsSoftDeletedBusinessException, BusinessException{
        Assert.notNull(getId(),getClass().getSimpleName()+".id");
        return getWithoutSoftDeletedFinder().findOne(getId());
    }

    protected T findSelf() throws EntityWithSuchIdDoesNotExistsBusinessException,EntityWithSuchIdIsSoftDeletedBusinessException, BusinessException{
        Assert.notNull(getId(),getClass().getSimpleName()+".id");
        return getFinder().findOne(getId());
    }

}
