package javaConfig.spring.common;

import dataAccess.common.SimpleJpaRepository;
import dataAccess.common.SoftDeleteModedJpaRepository;
import domain.ResponsiblePerson;
import domain.common.SimpleFinder;
import domain.userAccounts.UserAccount;
import domain.userAccounts.validation.UserAccountValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import smartvalidation.validator.entityValidator.EntityValidatorFactory;
import smartvalidation.validator.entityValidator.ValidationContext;

import javax.persistence.EntityManager;


@Configuration
public class UserAccountsConfig {

    @Autowired
    EntityManager entityManager;


    @Bean
    public SimpleJpaRepository userAccountsRepository() {
        return new SoftDeleteModedJpaRepository<UserAccount,Long>(entityManager,UserAccount.class);
    }

    @Bean
    public SimpleJpaRepository userAccountsWithoutSoftDeletedRepository() {
        return new SoftDeleteModedJpaRepository<UserAccount,Long>(entityManager, UserAccount.class, SoftDeleteModedJpaRepository.FindMode.WithoutSoftDeleted);
    }

    @Bean
    public SimpleFinder<UserAccount, Long> userAccountsFinder(SimpleJpaRepository userAccountsRepository) {
        return new SimpleFinder<UserAccount, Long>(userAccountsRepository);
    }

    @Bean
    public SimpleFinder<UserAccount, Long> userAccountsWithoutSoftDeletedFinder(SimpleJpaRepository userAccountsWithoutSoftDeletedRepository) {
        return new SimpleFinder<UserAccount, Long>(userAccountsWithoutSoftDeletedRepository);
    }

    @Bean
    public EntityValidatorFactory<UserAccount> userAccountOnCreateValidatorFactory() {
        return new UserAccountValidatorFactory.OnCreate(new ValidationContext("userAccount"));
    }

    @Bean
    public EntityValidatorFactory<UserAccount> userAccountOnUpdateValidatorFactory() {
        return new UserAccountValidatorFactory.OnUpdate(new ValidationContext("userAccount"));
    }

}
