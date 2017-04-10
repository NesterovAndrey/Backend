package com.backend.config;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableJpaRepositories(basePackages = "com.backend.repository")
@EnableTransactionManagement
public class PersistenceConfig {

    @Autowired
    @Bean(name = "dataSource")
    public DataSource getDataSource(Environment environment) {
       DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("dbDriver"));
        Logger log= org.slf4j.LoggerFactory.getLogger(this.getClass());
        log.info("DATABASE URL "+environment.getProperty("dbUrl"));
        dataSource.setUrl(environment.getProperty("dbUrl"));
        dataSource.setUsername(environment.getProperty("dbUsername"));
        dataSource.setPassword(environment.getProperty("dbPassword"));
        return dataSource;
    }

    @Autowired
    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                Environment environment) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("com.backend");

        Properties jpaProperties = new Properties();

        jpaProperties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));

        jpaProperties.put("hibernate.hbm2ddl.auto",
                environment.getRequiredProperty("hibernate.hbm2ddl.auto")
        );

        jpaProperties.put("hibernate.ejb.naming_strategy",
                environment.getRequiredProperty("hibernate.ejb.naming_strategy")
        );

        jpaProperties.put("hibernate.show_sql",
                environment.getRequiredProperty("hibernate.show_sql")
        );

        jpaProperties.put("hibernate.format_sql",
                environment.getRequiredProperty("hibernate.format_sql")
        );

        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }
    @Autowired
    @Bean(name = "transactionManager")
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
