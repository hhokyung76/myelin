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
@MapperScan(value="com.myelin.builder.dao.mariadb", sqlSessionFactoryRef="dbMariadbSqlSessionFactory")
@EnableTransactionManagement
public class DatabaseMariadbConfig {
	private static final Logger log = LogManager.getLogger(DatabaseMariadbConfig.class);

	@Autowired
	Environment env;
	
	@Bean(name = "dbMariadbDataSource")
    @ConfigurationProperties(prefix = "spring.mariadb.datasource")
    public DataSource dbMariadbDataSource() {

		log.info("env mariadb: "+env.getProperty("spring.mariadb.datasource.driverClassName"));
		log.info("env mariadb: "+env.getProperty("spring.mariadb.datasource.url"));
		log.info("env mariadb: "+env.getProperty("spring.mariadb.datasource.username"));
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource(); 
        dataSource.setDriverClassName(env.getProperty("spring.mariadb.datasource.driverClassName")); 
        dataSource.setUrl(env.getProperty("spring.mariadb.datasource.url")); 
        dataSource.setUsername(env.getProperty("spring.mariadb.datasource.username")); 
        dataSource.setPassword(env.getProperty("spring.mariadb.datasource.password")); 
        
        return dataSource;
    }
 
    @Bean(name = "dbMariadbSqlSessionFactory")
    public SqlSessionFactory dbMariadbSqlSessionFactory(@Qualifier("dbMariadbDataSource") DataSource dbMariadbDataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dbMariadbDataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:com/myelin/builder/dao/mariadb/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }
 
    @Bean(name = "dbMariadbSqlSessionTemplate")
    public SqlSessionTemplate dbMariadbSqlSessionTemplate(SqlSessionFactory dbMariadbSqlSessionFactory) throws Exception {
 
        return new SqlSessionTemplate(dbMariadbSqlSessionFactory);
    }
}
