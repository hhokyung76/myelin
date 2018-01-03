package com.myelin.builder.core.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@MapperScan(value="com.myelin.builder.dao.presto", sqlSessionFactoryRef="dbPrestoSqlSessionFactory")
@EnableTransactionManagement
public class DatabasePrestoConfig {
	private static final Logger log = LogManager.getLogger(DatabasePrestoConfig.class);

	@Autowired
	Environment env;
	
	@Bean(name = "dbPrestoDataSource")
    @ConfigurationProperties(prefix = "spring.presto.datasource")
    public DataSource dbPrestoDataSource() {

		log.info("env h2: "+env.getProperty("spring.h2.datasource.driverClassName"));
		log.info("env h2: "+env.getProperty("spring.h2.datasource.url"));
		log.info("env h2: "+env.getProperty("spring.h2.datasource.username"));
		log.info("env hive: "+env.getProperty("spring.hive.datasource.driverClassName"));
		log.info("env hive: "+env.getProperty("spring.hive.datasource.url"));
		log.info("env presto: "+env.getProperty("spring.presto.datasource.driverClassName"));
		log.info("env presto: "+env.getProperty("spring.presto.datasource.url"));
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource(); 
        dataSource.setDriverClassName(env.getProperty("spring.presto.datasource.driverClassName")); 
        dataSource.setUrl(env.getProperty("spring.presto.datasource.url")); 
        dataSource.setUsername(env.getProperty("spring.presto.datasource.username")); 
        dataSource.setPassword(env.getProperty("spring.presto.datasource.password")); 
        
        return dataSource;
    }
 
    @Bean(name = "dbPrestoSqlSessionFactory")
    public SqlSessionFactory dbPrestoSqlSessionFactory(@Qualifier("dbPrestoDataSource") DataSource dbPrestoDataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dbPrestoDataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:com/myelin/builder/dao/presto/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }
 
    @Bean(name = "dbPrestoSqlSessionTemplate")
    public SqlSessionTemplate dbPrestoSqlSessionTemplate(SqlSessionFactory dbPrestoSqlSessionFactory) throws Exception {
 
        return new SqlSessionTemplate(dbPrestoSqlSessionFactory);
    }
}
