package javaConfig.spring.common;

import dataAccess.common.Node;
import dataAccess.common.SimpleJpaRepository;
import dataAccess.common.SoftDeleteModedJpaRepository;
import domain.ResponsiblePerson;
import domain.ResponsiblePerson_;
import domain.common.SimpleFinder;
import domain.validation.ResponsiblePersonValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import smartvalidation.validator.entityValidator.EntityValidatorFactory;
import smartvalidation.validator.entityValidator.ValidationContext;

import javax.persistence.EntityManager;


@Configuration
public class ResponsiblePersonsConfig {

    @Autowired
    EntityManager entityManager;


    @Bean
    public SimpleJpaRepository responsiblePersonsRepository() {
        return new SoftDeleteModedJpaRepository<ResponsiblePerson,Long>(entityManager,ResponsiblePerson.class);
    }

    @Bean
    public SimpleJpaRepository responsiblePersonsWithoutSoftDeletedRepository() {
        return new SoftDeleteModedJpaRepository<ResponsiblePerson,Long>(entityManager, ResponsiblePerson.class, SoftDeleteModedJpaRepository.FindMode.WithoutSoftDeleted);
    }

    @Bean
    public SimpleFinder<ResponsiblePerson, Long> responsiblePersonsFinder(SimpleJpaRepository responsiblePersonsRepository) {
        return new SimpleFinder<ResponsiblePerson, Long>(responsiblePersonsRepository);
    }

    @Bean
    public SimpleFinder<ResponsiblePerson, Long> responsiblePersonsWithoutSoftDeletedFinder(SimpleJpaRepository responsiblePersonsWithoutSoftDeletedRepository) {
        return new SimpleFinder<ResponsiblePerson, Long>(responsiblePersonsWithoutSoftDeletedRepository);
    }

    @Bean
    public EntityValidatorFactory<ResponsiblePerson> responsiblePersonOnCreateValidatorFactory() {
        return new ResponsiblePersonValidatorFactory.OnCreate(new ValidationContext("responsiblePerson"));
    }

    @Bean
    public EntityValidatorFactory<ResponsiblePerson> responsiblePersonOnUpdateValidatorFactory() {
        return new ResponsiblePersonValidatorFactory.OnUpdate(new ValidationContext("responsiblePerson"));
    }

}
