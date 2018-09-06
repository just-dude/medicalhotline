package javaConfig.spring.common;

import dataAccess.common.SimpleJpaRepository;
import dataAccess.common.SoftDeleteModedJpaRepository;
import domain.common.SimpleFinder;
import domain.common.notifications.NotificationContent;
import domain.common.notifications.validation.NotificationContentValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import smartvalidation.validator.entityValidator.EntityValidatorFactory;
import smartvalidation.validator.entityValidator.ValidationContext;

import javax.persistence.EntityManager;


@Configuration
public class NotificationContentsConfig {

    @Autowired
    EntityManager entityManager;


    @Bean
    public SimpleJpaRepository notificationContentsRepository() {
        return new SoftDeleteModedJpaRepository<NotificationContent,Long>(entityManager,NotificationContent.class);
    }

    @Bean
    public SimpleJpaRepository notificationContentsWithoutSoftDeletedRepository() {
        return new SoftDeleteModedJpaRepository<NotificationContent,Long>(entityManager, NotificationContent.class, SoftDeleteModedJpaRepository.FindMode.WithoutSoftDeleted);
    }

    @Bean
    public SimpleFinder<NotificationContent, Long> notificationContentsFinder(SimpleJpaRepository notificationContentsRepository) {
        return new SimpleFinder<NotificationContent, Long>(notificationContentsRepository);
    }

    @Bean
    public SimpleFinder<NotificationContent, Long> notificationContentsWithoutSoftDeletedFinder(SimpleJpaRepository notificationContentsWithoutSoftDeletedRepository) {
        return new SimpleFinder<NotificationContent, Long>(notificationContentsWithoutSoftDeletedRepository);
    }

    @Bean
    public EntityValidatorFactory<NotificationContent> notificationContentOnCreateValidatorFactory() {
        return new NotificationContentValidatorFactory.OnCreate(new ValidationContext("notificationContent"));
    }

    @Bean
    public EntityValidatorFactory<NotificationContent> notificationContentOnUpdateValidatorFactory() {
        return new NotificationContentValidatorFactory.OnUpdate(new ValidationContext("notificationContent"));
    }

}
