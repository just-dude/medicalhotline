package javaConfig.spring.common;

import dataAccess.common.Node;
import dataAccess.common.SimpleJpaRepository;
import dataAccess.common.SoftDeleteModedJpaRepository;
import domain.Appeal_;
import domain.Issue;
import domain.Issue_;
import domain.validation.IssueValidatorFactory;
import domain.common.SimpleFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import smartvalidation.validator.entityValidator.EntityValidatorFactory;
import smartvalidation.validator.entityValidator.ValidationContext;

import javax.persistence.EntityManager;


@Configuration
public class IssuesConfig {

    @Autowired
    EntityManager entityManager;


    @Bean
    public SimpleJpaRepository issuesRepository() {
        return new SoftDeleteModedJpaRepository<Issue,Long>(entityManager,Issue.class);
    }

    @Bean
    public SimpleJpaRepository issuesWithoutSoftDeletedRepository() {
        return new SoftDeleteModedJpaRepository<Issue,Long>(entityManager, Issue.class, SoftDeleteModedJpaRepository.FindMode.WithoutSoftDeleted);
    }

    @Bean
    public SimpleFinder<Issue, Long> issuesFinder(SimpleJpaRepository issuesRepository) {
        return new SimpleFinder<Issue, Long>(issuesRepository,
                Node.rootNode().addChildren(new Node(Issue_.appeal.getName()).addChild(Appeal_.creator.getName()),new Node(Issue_.issueCategory.getName())));
    }

    @Bean
    public SimpleFinder<Issue, Long> issuesWithoutSoftDeletedFinder(SimpleJpaRepository issuesWithoutSoftDeletedRepository) {
        return new SimpleFinder<Issue, Long>(issuesWithoutSoftDeletedRepository,
                Node.rootNode().addChildren(new Node(Issue_.appeal.getName()).addChild(Appeal_.creator.getName()),new Node(Issue_.issueCategory.getName())));
    }

    @Bean
    public EntityValidatorFactory<Issue> issueOnCreateValidatorFactory() {
        return new IssueValidatorFactory.OnCreate(new ValidationContext("issue"));
    }

    @Bean
    public EntityValidatorFactory<Issue> issueOnUpdateValidatorFactory() {
        return new IssueValidatorFactory.OnUpdate(new ValidationContext("issue"));
    }

}
