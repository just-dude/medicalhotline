package javaConfig.spring.common;

import dataAccess.common.Repository;
import dataAccess.common.SoftDeleteModedJpaRepository;
import domain.IssueCategory;
import domain.common.Finder;
import domain.common.SimpleFinder;
import domain.validation.IssueCategoryValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import smartvalidation.validator.entityValidator.EntityValidatorFactory;
import smartvalidation.validator.entityValidator.ValidationContext;

import javax.persistence.EntityManager;


@Configuration
public class IssueCategoriesConfig {

    @Autowired
    EntityManager entityManager;


    @Bean
    public Repository<IssueCategory,Long> issueCategoriesRepository() {
        return new SoftDeleteModedJpaRepository<IssueCategory,Long>(entityManager,IssueCategory.class);
    }

    @Bean
    public Repository<IssueCategory,Long> issueCategoriesWithoutSoftDeletedRepository() {
        return new SoftDeleteModedJpaRepository<IssueCategory,Long>(entityManager,IssueCategory.class,SoftDeleteModedJpaRepository.FindMode.WithoutSoftDeleted);
    }

    @Bean
    public Finder<IssueCategory,Long> issueCategoriesFinder(Repository<IssueCategory,Long> issueCategoriesRepository) {
        return new SimpleFinder<IssueCategory,Long>(issueCategoriesRepository);
    }

    @Bean
    public Finder<IssueCategory,Long> issueCategoriesWithoutSoftDeletedFinder(Repository<IssueCategory,Long> issueCategoriesWithoutSoftDeletedRepository) {
        return new SimpleFinder<IssueCategory,Long>(issueCategoriesWithoutSoftDeletedRepository);
    }

    @Bean
    public EntityValidatorFactory<IssueCategory> issueCategoryOnCreateValidatorFactory() {
        return new IssueCategoryValidatorFactory.OnCreate(new ValidationContext("issueCategory"));
    }

    @Bean
    public EntityValidatorFactory<IssueCategory> issueCategoryOnUpdateValidatorFactory() {
        return new IssueCategoryValidatorFactory.OnUpdate(new ValidationContext("issueCategory"));
    }

}
