package com.myelin.builder.core.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
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
@MapperScan(value="com.myelin.builder.dao.hive", sqlSessionFactoryRef="dbHiveSqlSessionFactory")
@EnableTransactionManagement
public class DatabaseHiveConfig {

	@Autowired
	Environment env;
	
	@Bean(name = "dbHiveDataSource")
    @ConfigurationProperties(prefix = "spring.hive.datasource")
    public DataSource dbHiveDataSource() {		
		DriverManagerDataSource dataSource = new DriverManagerDataSource(); 
        dataSource.setDriverClassName(env.getProperty("spring.hive.datasource.driverClassName")); 
        dataSource.setUrl(env.getProperty("spring.hive.datasource.url")); 
        dataSource.setUsername(env.getProperty("spring.hive.datasource.username")); 
        dataSource.setPassword(env.getProperty("spring.hive.datasource.password")); 

        return dataSource;
    }
 
    @Bean(name = "dbHiveSqlSessionFactory")
    public SqlSessionFactory dbHiveSqlSessionFactory(@Qualifier("dbHiveDataSource") DataSource dbHiveDataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dbHiveDataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:com/myelin/builder/dao/hive/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }
 
    @Bean(name = "dbHiveSqlSessionTemplate")
    public SqlSessionTemplate dbHiveSqlSessionTemplate(SqlSessionFactory dbHiveSqlSessionFactory) throws Exception {
 
        return new SqlSessionTemplate(dbHiveSqlSessionFactory);
    }
}
