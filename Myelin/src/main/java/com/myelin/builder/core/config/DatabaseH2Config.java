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
@MapperScan(value="com.ltmh.dao.h2", sqlSessionFactoryRef="dbH2SqlSessionFactory")
@EnableTransactionManagement
public class DatabaseH2Config {

	@Autowired
	Environment env;
	
	@Bean(name = "dbH2DataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.h2.datasource")
    public DataSource dbH2DataSource() {
//        return DataSourceBuilder.create().build();
        
        DriverManagerDataSource dataSource = new DriverManagerDataSource(); 
        dataSource.setDriverClassName(env.getProperty("spring.h2.datasource.driverClassName")); 
        dataSource.setUrl(env.getProperty("spring.h2.datasource.url")); 
        dataSource.setUsername(env.getProperty("spring.h2.datasource.username")); 
        dataSource.setPassword(env.getProperty("spring.h2.datasource.password")); 

        return dataSource;
    }
 
    @Bean(name = "dbH2SqlSessionFactory")
    @Primary
    public SqlSessionFactory dbH2SqlSessionFactory(@Qualifier("dbH2DataSource") DataSource dbH2DataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dbH2DataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:com/ltmh/dao/h2/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }
 
    @Bean(name = "dbH2SqlSessionTemplate")
    @Primary
    public SqlSessionTemplate dbH2SqlSessionTemplate(SqlSessionFactory dbH2SqlSessionFactory) throws Exception {
 
        return new SqlSessionTemplate(dbH2SqlSessionFactory);
    }
}
