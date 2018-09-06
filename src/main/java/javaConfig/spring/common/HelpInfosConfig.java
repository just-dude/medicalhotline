package javaConfig.spring.common;

import dataAccess.HelpInfosJpaRepository;
import dataAccess.common.SoftDeleteModedJpaRepository;
import domain.HelpInfosFinder;
import domain.validation.HelpInfoValidatorFactory;
import domain.HelpInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import smartvalidation.validator.entityValidator.EntityValidatorFactory;
import smartvalidation.validator.entityValidator.ValidationContext;

import javax.persistence.EntityManager;


@Configuration
public class HelpInfosConfig {

    @Autowired
    EntityManager entityManager;


    @Bean
    public HelpInfosJpaRepository helpInfosRepository() {
        return new HelpInfosJpaRepository(entityManager);
    }

    @Bean
    public HelpInfosJpaRepository helpInfosWithoutSoftDeletedRepository() {
        return new HelpInfosJpaRepository(entityManager, SoftDeleteModedJpaRepository.FindMode.WithoutSoftDeleted);
    }

    @Bean
    public HelpInfosFinder helpInfosFinder(HelpInfosJpaRepository helpInfosRepository) {
        return new HelpInfosFinder(helpInfosRepository);
    }

    @Bean
    public HelpInfosFinder helpInfosWithoutSoftDeletedFinder(HelpInfosJpaRepository helpInfosWithoutSoftDeletedRepository) {
        return new HelpInfosFinder(helpInfosWithoutSoftDeletedRepository);
    }

    @Bean
    public EntityValidatorFactory<HelpInfo> helpInfoOnCreateValidatorFactory() {
        return new HelpInfoValidatorFactory.OnCreate(new ValidationContext("helpInfo"));
    }

    @Bean
    public EntityValidatorFactory<HelpInfo> helpInfoOnUpdateValidatorFactory() {
        return new HelpInfoValidatorFactory.OnUpdate(new ValidationContext("helpInfo"));
    }

}
