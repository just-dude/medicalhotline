package javaConfig.spring.common;

import dataAccess.common.SimpleJpaRepository;
import dataAccess.common.SoftDeleteModedJpaRepository;
import domain.ResponsiblePersonUserAccount;
import domain.common.SimpleFinder;
import domain.userAccounts.UserAccount;
import domain.userAccounts.validation.UserAccountValidatorFactory;
import domain.validation.ResponsiblePersonUserAccountValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import smartvalidation.validator.entityValidator.EntityValidatorFactory;
import smartvalidation.validator.entityValidator.ValidationContext;

import javax.persistence.EntityManager;


@Configuration
public class ResponsiblePersonUserAccountsConfig {

    @Autowired
    EntityManager entityManager;


    @Bean
    public SimpleJpaRepository responsiblePersonUserAccountsRepository() {
        return new SoftDeleteModedJpaRepository<ResponsiblePersonUserAccount,Long>(entityManager,ResponsiblePersonUserAccount.class);
    }

    @Bean
    public SimpleJpaRepository responsiblePersonUserAccountsWithoutSoftDeletedRepository() {
        return new SoftDeleteModedJpaRepository<ResponsiblePersonUserAccount,Long>(entityManager, ResponsiblePersonUserAccount.class, SoftDeleteModedJpaRepository.FindMode.WithoutSoftDeleted);
    }

    @Bean
    public SimpleFinder<ResponsiblePersonUserAccount, Long> responsiblePersonUserAccountsFinder(SimpleJpaRepository responsiblePersonUserAccountsRepository) {
        return new SimpleFinder<ResponsiblePersonUserAccount, Long>(responsiblePersonUserAccountsRepository);
    }

    @Bean
    public SimpleFinder<ResponsiblePersonUserAccount, Long> responsiblePersonUserAccountsWithoutSoftDeletedFinder(SimpleJpaRepository responsiblePersonUserAccountsWithoutSoftDeletedRepository) {
        return new SimpleFinder<ResponsiblePersonUserAccount, Long>(responsiblePersonUserAccountsWithoutSoftDeletedRepository);
    }

    @Bean
    public EntityValidatorFactory<ResponsiblePersonUserAccount> responsiblePersonUserAccountOnCreateValidatorFactory() {
        return new ResponsiblePersonUserAccountValidatorFactory.OnCreate(new ValidationContext("userAccount"));
    }

    @Bean
    public EntityValidatorFactory<ResponsiblePersonUserAccount> responsiblePersonUserAccountOnUpdateValidatorFactory() {
        return new ResponsiblePersonUserAccountValidatorFactory.OnUpdate(new ValidationContext("userAccount"));
    }

}
