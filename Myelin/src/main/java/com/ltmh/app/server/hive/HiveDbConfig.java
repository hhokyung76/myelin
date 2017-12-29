package com.ltmh.app.server.hive;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@ComponentScan
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class HiveDbConfig {
    private static final Logger logger = LogManager.getLogger(HiveDbConfig.class);

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    Environment environment;

    @PostConstruct
    public void logBeans() {
        logger.info(StringUtils.repeat('*', 78));
        for (String bean : applicationContext.getBeanDefinitionNames()) {
            logger.debug("BEAN '{}'", bean);
        }
        logger.info(StringUtils.repeat('*', 78));
        logger.info("ENV: '{}'", environment.toString());
        logger.info(StringUtils.repeat('*', 78));
    }

    @Value("#{ environment['spring.hive.datasource.url'] }")
    protected String databaseUrl;

    @Value("#{ environment['spring.hive.datasource.username'] }")
    protected String databaseUserName = "";

    @Value("#{ environment['spring.hive.datasource.password'] }")
    protected String databasePassword = "";

    @Value("#{ environment['spring.hive.datasource.driverClassName'] }")
    protected String driverClassName;

  
//    @Bean
//    public CustomerService customerService() {
//        return new CustomerServiceImpl();
//    }

    @Bean(destroyMethod = "close")
    public DataSource hiveDataSource() {
    	logger.info("driverClassName: "+driverClassName);
    	logger.info("databaseUrl: "+databaseUrl);
    	logger.info("databaseUserName: "+databaseUserName);
    	logger.info("databasePassword: "+databasePassword);
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(databaseUrl);
        dataSource.setUsername(databaseUserName);
        dataSource.setPassword(databasePassword);
        //dataSource.setValidationQuery(databaseValidationQuery);
        dataSource.setTestOnBorrow(true);
        dataSource.setTestOnReturn(true);
        dataSource.setTestWhileIdle(true);
        dataSource.setTimeBetweenEvictionRunsMillis(1800000);
        dataSource.setNumTestsPerEvictionRun(3);
        dataSource.setMinEvictableIdleTimeMillis(1800000);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(hiveDataSource());
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(hiveDataSource());
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(hiveDataSource());
    }
}
