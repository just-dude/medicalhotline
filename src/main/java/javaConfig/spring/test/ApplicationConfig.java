package javaConfig.spring.test;

import common.beanFactory.BeanFactoryProvider;
import javaConfig.spring.common.*;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@Configuration
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@EnableAspectJAutoProxy
@Import({javaConfig.spring.common.UserAccountsConfig.class, PersistenceConfig.class, AccountingConfig.class,
        EntitiesFieldsPrevStatesConfig.class, SecurityConfig.class,AppealsConfig.class, IssuesConfig.class, IssueActionsConfig.class,
        IssueCategoriesConfig.class,HelpInfosConfig.class, HelpInfosByOrganizationConfig.class,OrganizationsConfig.class,ResponsiblePersonsConfig.class,
        ResponsiblePersonUserAccountsConfig.class, NotificationContentsConfig.class, NotificationsConfig.class})
@PropertySource(value = "classpath:persistence-test.properties")
public class ApplicationConfig {

    private static final long fixedTimeForTestsInEpochMilliseconds = 1451606400; // Fri, 01 Jan 2016 00:00:00 GMT

    @Bean
    public BeanFactoryProvider beanFactoryProvider() {
        return new BeanFactoryProvider();
    }

    @Bean
    public Clock clock() {
        return Clock.fixed(Instant.ofEpochSecond(fixedTimeForTestsInEpochMilliseconds), ZoneId.of("UTC"));
    }

    @Bean
    public Clock userPreferencesClock() {
        return Clock.fixed(Instant.ofEpochSecond(fixedTimeForTestsInEpochMilliseconds), ZoneId.of("UTC"));
    }
}
