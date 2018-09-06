package javaConfig.spring.common;

import controller.struts.action.main.IndexAction;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.SharedEntityManagerBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.aspectj.AnnotationTransactionAspect;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

@Configuration
public class PersistenceConfig {

    protected static final Logger LOG = LogManager.getLogger(PersistenceConfig.class);

    @Autowired
    Environment env;

    @Bean
    public DataSource dataSource(){

//        Properties persistenceProperties = new Properties();
//        InputStream is=null;
//        try {
//            is = getClass().getClassLoader().getResourceAsStream("persistence.properties");
//            persistenceProperties.load(is);
//        } catch (Exception e){
//            LOG.error("error - ",e);
//        }
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(env.getProperty("dataSource.url"));
        ds.setDriverClassName(env.getProperty("dataSource.driverClassName"));
        ds.setUsername(env.getProperty("dataSource.username"));
        ds.setPassword(env.getProperty("dataSource.password"));
        ds.setInitialSize(Integer.parseInt(env.getProperty("dataSource.initialSize")));
        ds.setMaxIdle(Integer.parseInt(env.getProperty("dataSource.maxIdle")));
        ds.setMinIdle(Integer.parseInt(env.getProperty("dataSource.minIdle")));
        ds.setMaxTotal(Integer.parseInt(env.getProperty("dataSource.maxTotal")));
        return ds;
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        HibernateJpaVendorAdapter va = new HibernateJpaVendorAdapter();
        va.setShowSql(Boolean.parseBoolean(env.getProperty("hibernate.showSql")));
        va.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        va.setGenerateDdl(false);

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPersistenceUnitName("medical-hotline-unit");
        emf.setDataSource(dataSource());
        emf.setPackagesToScan("domain");
        emf.setJpaVendorAdapter(va);


        return emf;
    }

    @Bean
    public SharedEntityManagerBean entityManager(EntityManagerFactory emf) {
        SharedEntityManagerBean em = new SharedEntityManagerBean();
        em.setEntityManagerFactory(emf);
        return em;
    }

    @Bean()
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager jtm= new JpaTransactionManager(emf);
        jtm.setGlobalRollbackOnParticipationFailure(false);
        return jtm;
        //return AnnotationTransactionAspect.aspectOf();
    }

    @Bean
    public AnnotationTransactionAspect transactionAspect() {
        return AnnotationTransactionAspect.aspectOf();
    }


}
