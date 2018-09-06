package javaConfig.spring.common;

import dataAccess.common.SimpleJpaRepository;
import dataAccess.common.SoftDeleteModedJpaRepository;
import domain.IssueAction;
import domain.validation.IssueActionValidatorFactory;
import domain.common.SimpleFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import smartvalidation.validator.entityValidator.EntityValidatorFactory;
import smartvalidation.validator.entityValidator.ValidationContext;

import javax.persistence.EntityManager;


@Configuration
public class IssueActionsConfig {

    @Autowired
    EntityManager entityManager;


    @Bean
    public SimpleJpaRepository issueActionsRepository() {
        return new SoftDeleteModedJpaRepository<IssueAction,Long>(entityManager,IssueAction.class);
    }

    @Bean
    public SimpleJpaRepository issueActionsWithoutSoftDeletedRepository() {
        return new SoftDeleteModedJpaRepository<IssueAction,Long>(entityManager, IssueAction.class, SoftDeleteModedJpaRepository.FindMode.WithoutSoftDeleted);
    }

    @Bean
    public SimpleFinder<IssueAction, Long> issueActionsFinder(SimpleJpaRepository issueActionsRepository) {
        return new SimpleFinder<IssueAction, Long>(issueActionsRepository);
    }

    @Bean
    public SimpleFinder<IssueAction, Long> issueActionsWithoutSoftDeletedFinder(SimpleJpaRepository issueActionsWithoutSoftDeletedRepository) {
        return new SimpleFinder<IssueAction, Long>(issueActionsWithoutSoftDeletedRepository);
    }

    @Bean
    public EntityValidatorFactory<IssueAction> issueActionOnCreateValidatorFactory() {
        return new IssueActionValidatorFactory.OnCreate(new ValidationContext("issueAction"));
    }

    @Bean
    public EntityValidatorFactory<IssueAction> issueActionOnUpdateValidatorFactory() {
        return new IssueActionValidatorFactory.OnUpdate(new ValidationContext("issueAction"));
    }

}
