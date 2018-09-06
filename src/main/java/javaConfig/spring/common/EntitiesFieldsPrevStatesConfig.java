package javaConfig.spring.common;

import dataAccess.common.SimpleJpaRepository;
import dataAccess.common.SoftDeleteModedJpaRepository;
import domain.common.SimpleFinder;
import domain.common.stateChangesAccounting.EntityFieldPrevState;
import domain.common.stateChangesAccounting.EntityFieldPrevStatesFinder;
import domain.common.stateChangesAccounting.validation.EntityFieldPrevStateValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import smartvalidation.validator.entityValidator.EntityValidatorFactory;
import smartvalidation.validator.entityValidator.ValidationContext;

import javax.persistence.EntityManager;

@Configuration
public class EntitiesFieldsPrevStatesConfig {

    @Autowired
    EntityManager entityManager;


    @Bean
    public SimpleJpaRepository<EntityFieldPrevState,EntityFieldPrevState.EntityFieldPrevStateId> entityFieldPrevStatesRepository() {
        return new SoftDeleteModedJpaRepository<EntityFieldPrevState,EntityFieldPrevState.EntityFieldPrevStateId>(entityManager,EntityFieldPrevState.class);
    }

    @Bean
    public SimpleJpaRepository<EntityFieldPrevState,EntityFieldPrevState.EntityFieldPrevStateId> entityFieldPrevStatesWithoutSoftDeletedRepository() {
        return new SoftDeleteModedJpaRepository<EntityFieldPrevState,EntityFieldPrevState.EntityFieldPrevStateId>(entityManager,EntityFieldPrevState.class, SoftDeleteModedJpaRepository.FindMode.WithoutSoftDeleted);
    }

    @Bean
    public SimpleFinder<EntityFieldPrevState,EntityFieldPrevState.EntityFieldPrevStateId> entityFieldPrevStatesFinder(SimpleJpaRepository<EntityFieldPrevState,EntityFieldPrevState.EntityFieldPrevStateId> entityFieldPrevStatesRepository) {
        return new EntityFieldPrevStatesFinder(entityFieldPrevStatesRepository);
    }

    @Bean
    public SimpleFinder<EntityFieldPrevState,EntityFieldPrevState.EntityFieldPrevStateId> entityFieldPrevStatesWithoutSoftDeletedFinder(SimpleJpaRepository<EntityFieldPrevState,EntityFieldPrevState.EntityFieldPrevStateId> entityFieldPrevStatesWithoutSoftDeletedRepository) {
        return new EntityFieldPrevStatesFinder(entityFieldPrevStatesWithoutSoftDeletedRepository);
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
