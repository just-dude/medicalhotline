package javaConfig.spring.common;

import dataAccess.common.SimpleJpaRepository;
import dataAccess.common.SoftDeleteModedJpaRepository;
import domain.common.SimpleFinder;
import domain.common.stateChangesAccounting.EntityFieldPrevState;
import domain.common.stateChangesAccounting.EntityPrevStateDiffFinder;
import domain.common.stateChangesAccounting.validation.EntityFieldPrevStateValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import smartvalidation.validator.entityValidator.EntityValidatorFactory;
import smartvalidation.validator.entityValidator.ValidationContext;

import javax.persistence.EntityManager;


@Configuration
public class AccountingConfig {

    @Autowired
    EntityManager entityManager;


    @Bean
    public SimpleJpaRepository entityFieldPrevStatesRepository() {
        return new SoftDeleteModedJpaRepository<EntityFieldPrevState,EntityFieldPrevState.EntityFieldPrevStateId>(entityManager,EntityFieldPrevState.class);
    }

    @Bean
    public SimpleJpaRepository entityFieldPrevStatesWithoutSoftDeletedRepository() {
        return new SoftDeleteModedJpaRepository<EntityFieldPrevState,EntityFieldPrevState.EntityFieldPrevStateId>(entityManager, EntityFieldPrevState.class, SoftDeleteModedJpaRepository.FindMode.WithoutSoftDeleted);
    }

    @Bean
    public SimpleFinder<EntityFieldPrevState, EntityFieldPrevState.EntityFieldPrevStateId> entityFieldPrevStatesFinder(SimpleJpaRepository entityFieldPrevStatesRepository) {
        return new SimpleFinder<EntityFieldPrevState, EntityFieldPrevState.EntityFieldPrevStateId>(entityFieldPrevStatesRepository);
    }

    @Bean
    public SimpleFinder<EntityFieldPrevState, EntityFieldPrevState.EntityFieldPrevStateId> entityFieldPrevStatesWithoutSoftDeletedFinder(SimpleJpaRepository entityFieldPrevStatesWithoutSoftDeletedRepository) {
        return new SimpleFinder<EntityFieldPrevState, EntityFieldPrevState.EntityFieldPrevStateId>(entityFieldPrevStatesWithoutSoftDeletedRepository);
    }

    @Bean
    public EntityPrevStateDiffFinder entityPrevStateDiffFinder(SimpleFinder<EntityFieldPrevState, EntityFieldPrevState.EntityFieldPrevStateId> entityFieldPrevStatesFinder) {
        return new EntityPrevStateDiffFinder(entityFieldPrevStatesFinder);
    }


    @Bean
    public EntityValidatorFactory<EntityFieldPrevState> entityFieldPrevStateOnCreateValidatorFactory() {
        return new EntityFieldPrevStateValidatorFactory.OnCreate(new ValidationContext("entityFieldPrevState"));
    }

    @Bean
    public EntityValidatorFactory<EntityFieldPrevState> entityFieldPrevStateOnUpdateValidatorFactory() {
        return new EntityFieldPrevStateValidatorFactory.OnUpdate(new ValidationContext("entityFieldPrevState"));
    }

}
