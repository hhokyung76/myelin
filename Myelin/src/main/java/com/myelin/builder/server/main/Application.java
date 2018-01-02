package com.myelin.builder.server.main;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.h2.tools.Server;
//import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.myelin.builder.app.server.hive.HiveDbConfig;
import com.myelin.builder.core.config.DatabaseH2Config;
import com.myelin.builder.core.config.DatabaseHiveConfig;
import com.myelin.builder.core.config.DatabaseMariadbConfig;
import com.myelin.builder.core.config.DatabasePrestoConfig;
import com.myelin.builder.core.vertx.server.VertxServerVerticle;
import com.myelin.builder.dao.mariadb.DbMariaSource;
import com.myelin.builder.server.db.HiveDataSourceNative;
import com.myelin.builder.server.db.PrestoDataSourceNative;
import com.myelin.builder.server.hdfs.HadoopFileSystemUtil;
import com.myelin.builder.server.manager.MyelinQueueManager;
import com.myelin.builder.server.manager.MyelinServerPoolManager;
import com.myelin.builder.server.process.LtmhHiveProcessor;
import com.myelin.builder.server.process.LtmhOrcProcessor;

import io.vertx.core.Vertx;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = { "com.ltmh" }, basePackageClasses = Application.class)
// @MapperScan(value = {"org.tinyfarmer.hub.dao.mapper"})
public class Application implements CommandLineRunner {
	private static final Logger log = LogManager.getLogger(Application.class);

	@Autowired
	private ApplicationContext context;
	
	DataSource dbH2DataSource;

	DataSource dbHiveDataSource;

	DataSource dbPrestoDataSource;
	
	DataSource dbMariadbDataSource;

	@Autowired
	DatabaseH2Config databaseH2Config;
	@Autowired
	DatabaseHiveConfig databaseHiveConfig;
	@Autowired
	DatabasePrestoConfig databasePrestoConfig;
	@Autowired
	DatabaseMariadbConfig databaseMariadbConfig;
	
	@Autowired
	Environment env;

	@Autowired
    private VertxServerVerticle vertxServerVerticle;

	@Autowired
    private MyelinQueueManager myelinQueueManager;
	@Autowired
    private MyelinServerPoolManager myelinServerPoolManager;
	
	@Autowired
	private HadoopFileSystemUtil hadoopFileSystemUtil;

	private LtmhOrcProcessor ltmhOrcProcessor;
	private LtmhHiveProcessor ltmhHiveProcessor;
	
	
	@PostConstruct
	public void deployServerVerticle() {
	    //Vertx.vertx().deployVerticle(vertxServerVerticle);
	}
	// @Autowired HiveDataSourceNative hiveDataSource;
	// @Autowired PrestoDataSourceNative prestoDataSource;

	public static void main(String[] args) throws IOException {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);

		log.info("test log print");
		log.trace("test log print");
		log.debug("test log print");
		log.info("test log print");
		log.warn("test log print");
		log.error("test log print");

		File file = new ClassPathResource("application.properties").getFile();
		log.info(file.getAbsolutePath());
		
		System.out.println("Let's inspect the beans provided by Spring Boot:");
	    String[] beanNames = ctx.getBeanDefinitionNames();
	    Arrays.sort(beanNames);
	    for (String beanName : beanNames) {
	        System.out.print(beanName);
	        System.out.print(" ");
	    }
		// String[] beanNames = ctx.getBeanDefinitionNames();
		// Arrays.sort(beanNames);
		// for (String beanName : beanNames) {
		// System.out.println(beanName);
		// }
	}

	// /**
	// * SqlSessionFactory �꽕�젙
	// */
	// @Bean
	// public SqlSessionFactory sqlSessionFactory(DataSource dataSource)throws
	// Exception{
	// SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
	// sessionFactory.setDataSource(dataSource);
	// return sessionFactory.getObject();
	// }

//	@Autowired
//	JdbcTemplate jdbcTemplate;

	@Autowired
	com.myelin.builder.server.engine.MyelinEngine myelinEngine;

	@Override
	public void run(String... strings) throws Exception {
		hadoopFileSystemUtil.init();
		
		vertxServerVerticle.start();
		log.info("spring.application.name: "+env.getProperty("spring.application.name"));
		log.info("server.vertx.port: "+env.getProperty("server.vertx.port"));
		log.info("");
		log.info("env h2: "+env.getProperty("spring.h2.datasource.driverClassName"));
		log.info("env h2: "+env.getProperty("spring.h2.datasource.url"));
		log.info("env h2: "+env.getProperty("spring.h2.datasource.username"));
		log.info("env hive: "+env.getProperty("spring.hive.datasource.driverClassName"));
		log.info("env hive: "+env.getProperty("spring.hive.datasource.url"));
		log.info("env presto: "+env.getProperty("spring.presto.datasource.driverClassName"));
		log.info("env presto: "+env.getProperty("spring.presto.datasource.url"));
		log.info("env mariadb: "+env.getProperty("spring.mariadb.datasource.driverClassName"));
		log.info("env mariadb: "+env.getProperty("spring.mariadb.datasource.url"));
		log.info("env mariadb: "+env.getProperty("spring.mariadb.datasource.username"));
		log.info("env mariadb: "+env.getProperty("spring.mariadb.datasource.password"));
		
		
		myelinEngine.temp();

		// prestoDataSource.createConnectionForPresto();
		// hiveDataSource.createConnectionForHive();
		// log.info("h2 jdbc: "+dbH2DataSource.toString());
		// log.info("hive jdbc: "+hiveDataSource.toString());
		// log.info("presto jdbc: "+prestoDataSource.toString());
		////
		// prestoDataSource.testQuery();
		//
		// hiveDataSource.testQuery();
		dbH2DataSource = databaseH2Config.dbH2DataSource();
		dbHiveDataSource = databaseHiveConfig.dbHiveDataSource();
		dbPrestoDataSource = databasePrestoConfig.dbPrestoDataSource();
		dbMariadbDataSource = databaseMariadbConfig.dbMariadbDataSource();
		 log.info("h2 jdbc: "+dbH2DataSource.toString());
		 log.info("hive jdbc: "+dbHiveDataSource.toString());
		 log.info("presto jdbc: "+dbPrestoDataSource.toString());

		log.info("h2 jdbc: " + dbH2DataSource.toString());
		log.info("hive jdbc: " + dbHiveDataSource.toString());
		log.info("presto jdbc: " + dbPrestoDataSource.toString());

		
		
		JdbcTemplate template = new JdbcTemplate(dbHiveDataSource); //dbHiveDataSource.getHiveConnection();
		List<Map<String, Object>> list = template.queryForList("show tables");
		for (int ii = 0; ii < list.size(); ii++) {
		   log.info("hive temp map: "+list.get(ii));
		}

		JdbcTemplate prestoTemplate = new JdbcTemplate(dbPrestoDataSource); //dbHiveDataSource.getHiveConnection();
		List<Map<String, Object>> lists = prestoTemplate.queryForList("select cust_id, cust_email, ltmh_flag, ltmh_content from ltmh_contents_plan limit 1");
		for (int ii = 0; ii < lists.size(); ii++) {
		   log.info("ltmh_contents_plan presto map: "+lists.get(ii));
		}
		
		
		JdbcTemplate mariaTemplate2 = new JdbcTemplate(dbMariadbDataSource); //dbHiveDataSource.getHiveConnection();
		List<Map<String, Object>> listengkor = mariaTemplate2.queryForList("select number, word from engkor limit 10");
		for (int ii = 0; ii < listengkor.size(); ii++) {
		   log.info("engkor mariadb map: "+listengkor.get(ii));
		}
		
		
		Connection prestoConn = dbPrestoDataSource.getConnection();
		Statement stmt = prestoConn.createStatement();
		
		String query = "select * from ltmh_contents_plan";
	    ResultSet rs = stmt.executeQuery(query);
	    ResultSetMetaData rmd = rs.getMetaData();
	    System.out.println("meta columnCount: "+rmd.getColumnCount());
	    for (int ii = 1; ii <= rmd.getColumnCount(); ii++) {
	    	System.out.println(rmd.getColumnName(ii)+" => "+rmd.getColumnTypeName(ii));
	    }
	    
	    
	    JdbcTemplate mariaTemplate = new JdbcTemplate(dbMariadbDataSource); //dbHiveDataSource.getHiveConnection();
		Object[] parameters={199};
		int[] types={Types.INTEGER};
		List<Map<String, Object>> listengkor2 = mariaTemplate.queryForList("select number, word, description from engkor where number = ? ", parameters, types);
		

//		String sql;
//		sql = "select * FROM engkor where number = "+number;
//
//		log.info("query: "+sql);
//		ResultSet rs = st.executeQuery(sql);
//		log.info("query ResultSet: "+rs);

		if (listengkor.size() > 0) {
			Map<String, Object> tempVal = listengkor2.get(0);
			Long numberVal = (Long) tempVal.get("number");
			String word = (String) tempVal.get("word");
			String description = (String) tempVal.get("description");
			log.info("query result : "+numberVal+" "+word+" "+description);
			//contentVal.setLtmhFlag("10");
		}
	    
	    

	    ltmhOrcProcessor = (LtmhOrcProcessor) context.getBean("ltmhOrcProcessor");
	    ltmhOrcProcessor.setMyelinQueueManager(myelinQueueManager);
	    ltmhOrcProcessor.setDbHiveDataSource(dbHiveDataSource);
	    ltmhOrcProcessor.setDbPrestoDataSource(dbPrestoDataSource);
	    ltmhOrcProcessor.setMyelinQueueManager(myelinQueueManager);
	    ltmhOrcProcessor.setHadoopFileSystemUtil(hadoopFileSystemUtil);
	    myelinServerPoolManager.execute(ltmhOrcProcessor);
		myelinServerPoolManager.putThreadMap("LtmhOrcProcessor", ltmhOrcProcessor);
		


		ltmhHiveProcessor = (LtmhHiveProcessor) context.getBean("ltmhHiveProcessor");
		ltmhHiveProcessor.setMyelinQueueManager(myelinQueueManager);
		ltmhHiveProcessor.setDbHiveDataSource(dbHiveDataSource);
		ltmhHiveProcessor.setDbPrestoDataSource(dbPrestoDataSource);
		ltmhHiveProcessor.setMyelinQueueManager(myelinQueueManager);
		ltmhHiveProcessor.setHadoopFileSystemUtil(hadoopFileSystemUtil);
	    myelinServerPoolManager.execute(ltmhHiveProcessor);
		myelinServerPoolManager.putThreadMap("ltmhHiveProcessor", ltmhHiveProcessor);
	}

	// @Bean
	// public JdbcTemplate getJdbcTemplate() {
	// return new JdbcTemplate(dbH2DataSource);
	// }

	// @Bean
	// public DataSource dataSource() {
	//
	// // no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
	//// EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
	//// EmbeddedDatabase db = builder
	//// .setType(EmbeddedDatabaseType.DERBY) //.H2 or .DERBY
	//// .addScript("db/sql/create-db.sql")
	//// .addScript("db/sql/insert-data.sql")
	//// .build();
	// DriverManagerDataSource dataSource = new DriverManagerDataSource();
	// //dataSource.setDriverClassName("org.apache.derby.jdbc.EmbeddedDriver");
	// dataSource.setDriverClassName("org.h2.Driver");
	// //dataSource.setUrl("jdbc:derby:d:\\temp\\database\\tinyhub;create=true");
	// dataSource.setUrl("jdbc:h2:d:\\temp\\databaseh2\\ltmh;AUTO_SERVER=TRUE");
	// dataSource.setUsername("sa");
	// dataSource.setPassword("");
	//
	// return dataSource;
	// }

	// @Bean
	// public DataSource hiveDataSource() {
	//
	// DriverManagerDataSource dataSource = new DriverManagerDataSource();
	// //dataSource.setDriverClassName("org.apache.derby.jdbc.EmbeddedDriver");
	// dataSource.setDriverClassName("org.h2.Driver");
	// //dataSource.setUrl("jdbc:derby:d:\\temp\\database\\tinyhub;create=true");
	// dataSource.setUrl("jdbc:h2:d:\\temp\\databaseh2\\ltmh;AUTO_SERVER=TRUE");
	// dataSource.setUsername("sa");
	// dataSource.setPassword("");
	//
	// return dataSource;
	// }
	//
	// @Bean
	// public DataSource prestoDataSource() {
	// DriverManagerDataSource dataSource = new DriverManagerDataSource();
	// //dataSource.setDriverClassName("org.apache.derby.jdbc.EmbeddedDriver");
	// dataSource.setDriverClassName("org.h2.Driver");
	// //dataSource.setUrl("jdbc:derby:d:\\temp\\database\\tinyhub;create=true");
	// dataSource.setUrl("jdbc:h2:d:\\temp\\databaseh2\\ltmh;AUTO_SERVER=TRUE");
	// dataSource.setUsername("sa");
	// dataSource.setPassword("");
	//
	// return dataSource;
	// }

}
