package com.ltmh.server.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ltmh.app.entity.LtmContentPlan;
import com.ltmh.core.config.DatabaseMariadbConfig;
import com.ltmh.server.main.Application;

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

	public LtmContentPlan getContent(Integer number) {
		System.out.println("@@@@@@@@@@@@@ LtmContentPlan getContent");
		if (number == null) 
			return null;
		

		LtmContentPlan contentVal = new LtmContentPlan();
		
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
				contentVal.setLtmhContent(description);
				contentVal.setLtmhSubject(word);
				//contentVal.setLtmhFlag("10");
			}
			log.info("query result end....: ");

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
		}
		return contentVal;
	}

}
