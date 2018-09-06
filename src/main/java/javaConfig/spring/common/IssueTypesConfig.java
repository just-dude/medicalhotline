package javaConfig.spring.common;

import dataAccess.common.Node;
import dataAccess.common.SimpleJpaRepository;
import dataAccess.common.SoftDeleteModedJpaRepository;
import domain.IssueType;
import domain.validation.IssueTypeValidatorFactory;
import domain.common.SimpleFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import smartvalidation.validator.entityValidator.EntityValidatorFactory;
import smartvalidation.validator.entityValidator.ValidationContext;

import javax.persistence.EntityManager;


@Configuration
public class IssueTypesConfig {

    @Autowired
    EntityManager entityManager;


    @Bean
    public SimpleJpaRepository issueTypesRepository() {
        return new SoftDeleteModedJpaRepository<IssueType,Long>(entityManager,IssueType.class);
    }

    @Bean
    public SimpleJpaRepository issueTypesWithoutSoftDeletedRepository() {
        return new SoftDeleteModedJpaRepository<IssueType,Long>(entityManager, IssueType.class, SoftDeleteModedJpaRepository.FindMode.WithoutSoftDeleted);
    }

    @Bean
    public SimpleFinder<IssueType, Long> issueTypesFinder(SimpleJpaRepository issueTypesRepository) {
        return new SimpleFinder<IssueType, Long>(issueTypesRepository);
    }

    @Bean
    public SimpleFinder<IssueType, Long> issueTypesWithoutSoftDeletedFinder(SimpleJpaRepository issueTypesWithoutSoftDeletedRepository) {
        return new SimpleFinder<IssueType, Long>(issueTypesWithoutSoftDeletedRepository);
    }

    @Bean
    public EntityValidatorFactory<IssueType> issueTypeOnCreateValidatorFactory() {
        return new IssueTypeValidatorFactory.OnCreate(new ValidationContext("issueType"));
    }

    @Bean
    public EntityValidatorFactory<IssueType> issueTypeOnUpdateValidatorFactory() {
        return new IssueTypeValidatorFactory.OnUpdate(new ValidationContext("issueType"));
    }

}
