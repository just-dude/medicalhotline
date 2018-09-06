package javaConfig.spring.dev;

import common.beanFactory.BeanFactoryProvider;
import javaConfig.spring.common.*;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@EnableAspectJAutoProxy
@Import({javaConfig.spring.common.UserAccountsConfig.class, PersistenceConfig.class, AccountingConfig.class, SecurityConfig.class, EntitiesFieldsPrevStatesConfig.class,
        AppealsConfig.class, IssuesConfig.class, IssueActionsConfig.class,
        IssueTypesConfig.class,IssueCategoriesConfig.class,HelpInfosConfig.class, HelpInfosByOrganizationConfig.class,
        OrganizationsConfig.class,ResponsiblePersonsConfig.class,ResponsiblePersonUserAccountsConfig.class, NotificationContentsConfig.class,
        NotificationsConfig.class})
@PropertySource(value = "classpath:persistence.properties")
public class ApplicationConfig {
    @Bean
    public BeanFactoryProvider beanFactoryProvider() {
        return new BeanFactoryProvider();
    }

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    public Clock userPreferencesClock() {
        return Clock.system(ZoneId.of("Asia/Chita"));
    }
}
