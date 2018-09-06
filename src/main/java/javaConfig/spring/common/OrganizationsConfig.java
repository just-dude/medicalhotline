package javaConfig.spring.common;

import dataAccess.common.Node;
import dataAccess.common.SimpleJpaRepository;
import dataAccess.common.SoftDeleteModedJpaRepository;
import domain.Organization;
import domain.ResponsiblePerson_;
import domain.validation.OrganizationValidatorFactory;
import domain.common.SimpleFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import smartvalidation.validator.entityValidator.EntityValidatorFactory;
import smartvalidation.validator.entityValidator.ValidationContext;

import javax.persistence.EntityManager;


@Configuration
public class OrganizationsConfig {

    @Autowired
    EntityManager entityManager;


    @Bean
    public SimpleJpaRepository organizationsRepository() {
        return new SoftDeleteModedJpaRepository<Organization,Long>(entityManager,Organization.class);
    }

    @Bean
    public SimpleJpaRepository organizationsWithoutSoftDeletedRepository() {
        return new SoftDeleteModedJpaRepository<Organization,Long>(entityManager, Organization.class, SoftDeleteModedJpaRepository.FindMode.WithoutSoftDeleted);
    }

    @Bean
    public SimpleFinder<Organization, Long> organizationsFinder(SimpleJpaRepository organizationsRepository) {
        return new SimpleFinder<Organization, Long>(organizationsRepository);
    }

    @Bean
    public SimpleFinder<Organization, Long> organizationsWithoutSoftDeletedFinder(SimpleJpaRepository organizationsWithoutSoftDeletedRepository) {
        return new SimpleFinder<Organization, Long>(organizationsWithoutSoftDeletedRepository);
    }

    @Bean
    public EntityValidatorFactory<Organization> organizationOnCreateValidatorFactory() {
        return new OrganizationValidatorFactory.OnCreate(new ValidationContext("organization"));
    }

    @Bean
    public EntityValidatorFactory<Organization> organizationOnUpdateValidatorFactory() {
        return new OrganizationValidatorFactory.OnUpdate(new ValidationContext("organization"));
    }

}
