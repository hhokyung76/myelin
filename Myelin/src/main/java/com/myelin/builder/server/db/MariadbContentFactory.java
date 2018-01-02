package com.myelin.builder.server.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.myelin.builder.app.entity.MyelinContentPlan;
import com.myelin.builder.core.config.DatabaseMariadbConfig;
import com.myelin.builder.server.main.Application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Component
public class MariadbContentFactory {
	
	private static final Logger log = LogManager.getLogger(Application.class);


	DataSource dbMariadbDataSource;

	@Autowired
	DatabaseMariadbConfig databaseMariadbConfig;
	
	@PostConstruct
	public void init() {
		dbMariadbDataSource = databaseMariadbConfig.dbMariadbDataSource();
	}

	public MyelinContentPlan getContent(Integer number) {
		System.out.println("@@@@@@@@@@@@@ LtmContentPlan getContent");
		if (number == null) 
			return null;
		

		MyelinContentPlan contentVal = new MyelinContentPlan();
		
		Statement st = null;
		try {
			
			JdbcTemplate mariaTemplate = new JdbcTemplate(dbMariadbDataSource); //dbHiveDataSource.getHiveConnection();
			Object[] parameters={number};
			int[] types={Types.INTEGER};
			List<Map<String, Object>> listengkor = mariaTemplate.queryForList("select number, word, description from engkor where number = ? ", parameters, types);
			

//			String sql;
//			sql = "select * FROM engkor where number = "+number;
//
//			log.info("query: "+sql);
//			ResultSet rs = st.executeQuery(sql);
//			log.info("query ResultSet: "+rs);

			if (listengkor.size() > 0) {
				Map<String, Object> tempVal = listengkor.get(0);
				Long numberVal = (Long) tempVal.get("number");
				String word = (String) tempVal.get("word");
				String description = (String) tempVal.get("description");
				log.info("query result : "+numberVal+" "+word+" "+description);
				contentVal.setMyelinContent(description);
				contentVal.setMyelinSubject(word);
				//contentVal.setLtmhFlag("10");
			}
			log.info("query result end....: ");

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
		}
		return contentVal;
	}

	public List<MyelinContentPlan> getContentsList(Integer number) {
		System.out.println("@@@@@@@@@@@@@ LtmContentPlan getContent");
		if (number == null) 
			return null;
		

		List<MyelinContentPlan> contentVals = new ArrayList<MyelinContentPlan>();
		
		Statement st = null;
		try {
			
			JdbcTemplate mariaTemplate = new JdbcTemplate(dbMariadbDataSource); //dbHiveDataSource.getHiveConnection();
			Object[] parameters={number};
			int[] types={Types.INTEGER};
			List<Map<String, Object>> listengkor = mariaTemplate.queryForList(""
					+ "select \r\n" + 
					"	c.number\r\n" + 
					"	, c.word\r\n" + 
					"	, c.description \r\n" + 
					"	, u.user_id\r\n" + 
					"	, u.user_email\r\n" + 
					"from engkor as c\r\n" + 
					"inner join myelin_user u\r\n" + 
					"where c.number = ?"
					, parameters, types);
			

//			String sql;
//			sql = "select * FROM engkor where number = "+number;
//
//			log.info("query: "+sql);
//			ResultSet rs = st.executeQuery(sql);
//			log.info("query ResultSet: "+rs);

			if (listengkor.size() > 0) {
				for (int ii = 0; ii < listengkor.size(); ii++) {
					MyelinContentPlan contentVal = new MyelinContentPlan();
					Map<String, Object> tempVal = listengkor.get(0);
					Long numberVal = (Long) tempVal.get("number");
					String word = (String) tempVal.get("word");
					String description = (String) tempVal.get("description");

					String userId = (String) tempVal.get("user_id");
					String userEmail = (String) tempVal.get("user_email");
					
					log.info("query result : "+numberVal+" "+word+" "+description);
					contentVal.setMyelinContent(description);
					contentVal.setMyelinSubject(word);
					contentVal.setCustId(userId);
					contentVal.setCustEmail(userEmail);
//					contentVal.setCreateTime(new Date());
					contentVals.add(contentVal);
				}
				//contentVal.setLtmhFlag("10");
			}
			log.info("query result end....: ");

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
		}
		return contentVals;
	}

}
