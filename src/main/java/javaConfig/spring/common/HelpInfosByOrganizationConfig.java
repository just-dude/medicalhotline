package javaConfig.spring.common;

import dataAccess.HelpInfosByOrganizationJpaRepository;
import dataAccess.common.SoftDeleteModedJpaRepository;
import domain.HelpInfosByOrganizationFinder;
import domain.validation.HelpInfoByOrganizationValidatorFactory;
import domain.HelpInfoByOrganization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import smartvalidation.validator.entityValidator.EntityValidatorFactory;
import smartvalidation.validator.entityValidator.ValidationContext;

import javax.persistence.EntityManager;


@Configuration
public class HelpInfosByOrganizationConfig {

    @Autowired
    EntityManager entityManager;


    @Bean
    public HelpInfosByOrganizationJpaRepository helpInfoByOrganizationsRepository() {
        return new HelpInfosByOrganizationJpaRepository(entityManager);
    }

    @Bean
    public HelpInfosByOrganizationJpaRepository helpInfoByOrganizationsWithoutSoftDeletedRepository() {
        return new HelpInfosByOrganizationJpaRepository(entityManager, SoftDeleteModedJpaRepository.FindMode.WithoutSoftDeleted);
    }

    @Bean
    public HelpInfosByOrganizationFinder helpInfoByOrganizationsFinder(HelpInfosByOrganizationJpaRepository helpInfoByOrganizationsRepository) {
        return new HelpInfosByOrganizationFinder(helpInfoByOrganizationsRepository);
    }

    @Bean
    public HelpInfosByOrganizationFinder helpInfoByOrganizationsWithoutSoftDeletedFinder(HelpInfosByOrganizationJpaRepository helpInfoByOrganizationsWithoutSoftDeletedRepository) {
        return new HelpInfosByOrganizationFinder(helpInfoByOrganizationsWithoutSoftDeletedRepository);
    }

    @Bean
    public EntityValidatorFactory<HelpInfoByOrganization> helpInfoByOrganizationOnCreateValidatorFactory() {
        return new HelpInfoByOrganizationValidatorFactory.OnCreate(new ValidationContext("helpInfoByOrganization"));
    }

    @Bean
    public EntityValidatorFactory<HelpInfoByOrganization> helpInfoByOrganizationOnUpdateValidatorFactory() {
        return new HelpInfoByOrganizationValidatorFactory.OnUpdate(new ValidationContext("helpInfoByOrganization"));
    }

}
