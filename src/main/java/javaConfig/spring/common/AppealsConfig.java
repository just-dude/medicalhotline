package javaConfig.spring.common;

import dataAccess.common.Node;
import dataAccess.common.SimpleJpaRepository;
import dataAccess.common.SoftDeleteModedJpaRepository;
import domain.Appeal;
import domain.Appeal_;
import domain.validation.AppealValidatorFactory;
import domain.common.SimpleFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import smartvalidation.validator.entityValidator.EntityValidatorFactory;
import smartvalidation.validator.entityValidator.ValidationContext;

import javax.persistence.EntityManager;


@Configuration
public class AppealsConfig {

    @Autowired
    EntityManager entityManager;


    @Bean
    public SimpleJpaRepository appealsRepository() {
        return new SoftDeleteModedJpaRepository<Appeal,Long>(entityManager,Appeal.class);
    }

    @Bean
    public SimpleJpaRepository appealsWithoutSoftDeletedRepository() {
        return new SoftDeleteModedJpaRepository<Appeal,Long>(entityManager, Appeal.class, SoftDeleteModedJpaRepository.FindMode.WithoutSoftDeleted);
    }

    @Bean
    public SimpleFinder<Appeal, Long> appealsFinder(SimpleJpaRepository appealsRepository) {
        return new SimpleFinder<Appeal, Long>(appealsRepository,
                Node.rootNode().addChildren(new Node(Appeal_.creator.getName())));
    }

    @Bean
    public SimpleFinder<Appeal, Long> appealsWithoutSoftDeletedFinder(SimpleJpaRepository appealsWithoutSoftDeletedRepository) {
        return new SimpleFinder<Appeal, Long>(appealsWithoutSoftDeletedRepository,Node.rootNode().addChildren(new Node(Appeal_.creator.getName())));
    }

    @Bean
    public EntityValidatorFactory<Appeal> appealOnCreateValidatorFactory() {
        return new AppealValidatorFactory.OnCreate(new ValidationContext("appeal"));
    }

    @Bean
    public EntityValidatorFactory<Appeal> appealOnUpdateValidatorFactory() {
        return new AppealValidatorFactory.OnUpdate(new ValidationContext("appeal"));
    }


}
