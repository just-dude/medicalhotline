package javaConfig.spring.common;

import dataAccess.common.SimpleJpaRepository;
import dataAccess.common.SoftDeleteModedJpaRepository;
import domain.common.SimpleFinder;
import domain.common.notifications.Notification;
import domain.common.notifications.NotificationsService;
import domain.common.notifications.validation.NotificationValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import smartvalidation.validator.entityValidator.EntityValidatorFactory;
import smartvalidation.validator.entityValidator.ValidationContext;

import javax.persistence.EntityManager;


@Configuration
public class NotificationsConfig {

    @Autowired
    EntityManager entityManager;


    @Bean
    public SimpleJpaRepository notificationsRepository() {
        return new SoftDeleteModedJpaRepository<Notification,Long>(entityManager,Notification.class);
    }

    @Bean
    public SimpleJpaRepository notificationsWithoutSoftDeletedRepository() {
        return new SoftDeleteModedJpaRepository<Notification,Long>(entityManager, Notification.class, SoftDeleteModedJpaRepository.FindMode.WithoutSoftDeleted);
    }

    @Bean
    public SimpleFinder<Notification, Long> notificationsFinder(SimpleJpaRepository notificationsRepository) {
        return new SimpleFinder<Notification, Long>(notificationsRepository);
    }

    @Bean
    public SimpleFinder<Notification, Long> notificationsWithoutSoftDeletedFinder(SimpleJpaRepository notificationsWithoutSoftDeletedRepository) {
        return new SimpleFinder<Notification, Long>(notificationsWithoutSoftDeletedRepository);
    }

    @Bean
    public NotificationsService notificationsService() {
        return new NotificationsService();
    }

    @Bean
    public EntityValidatorFactory<Notification> notificationOnCreateValidatorFactory() {
        return new NotificationValidatorFactory.OnCreate(new ValidationContext("notification"));
    }

    @Bean
    public EntityValidatorFactory<Notification> notificationOnUpdateValidatorFactory() {
        return new NotificationValidatorFactory.OnUpdate(new ValidationContext("notification"));
    }

}
