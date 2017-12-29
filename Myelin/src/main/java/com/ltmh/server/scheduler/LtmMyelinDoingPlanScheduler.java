package com.ltmh.server.scheduler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ltmh.app.entity.LtmContentPlan;
import com.ltmh.core.config.DatabaseH2Config;
import com.ltmh.core.config.DatabaseHiveConfig;
import com.ltmh.core.config.DatabasePrestoConfig;
import com.ltmh.framework.mail.MailSender;
import com.ltmh.framework.util.OpenStringUtils;
import com.ltmh.server.manager.MyelinQueueManager;

@Component
public class LtmMyelinDoingPlanScheduler {
	private final Logger log = LogManager.getLogger(this.getClass());

	@Autowired
	private MyelinQueueManager myelinQueueManager;

	@Autowired
	private MailSender mailSender;

	private DataSource dbPrestoDataSource;
	private JdbcTemplate prestoTemplate;

	@Autowired
	private DatabasePrestoConfig databasePrestoConfig;

	private static int number = 13;
	private final static String host = "pop.gmail.com";// change accordingly
	private final static String mailStoreType = "pop3";
	private final static String username = "ltm.myelin@gmail.com";// change accordingly
	private final static String password = "myelin!23";// change accordingly

	@PostConstruct
	public void init() {
		dbPrestoDataSource = databasePrestoConfig.dbPrestoDataSource();
		prestoTemplate = new JdbcTemplate(dbPrestoDataSource);
	}

//	@Scheduled(cron = "10 0/1 * * * ?")
	public void doingPlanProcess()  {
		log.info("doingPlanProcess starting..");
		String currTimeFullMinute = OpenStringUtils.getCurrentTimeFullDisplayHivePartitionMinute();
		
		List<Map<String, Object>> list = prestoTemplate.queryForList(
				"select cust_id, cust_email, ltmh_flag, ltmh_subject, ltmh_content, first_time from ltmh_contents_plan where p_minute='"
						+ currTimeFullMinute + "' ");
		for (int kk = 0; kk < list.size(); kk++) {
			//log.info("hive temp map: " + list.get(kk));
			Map<String, Object> tempMap = list.get(kk);

			LtmContentPlan contentPlan = new LtmContentPlan();
			contentPlan.setCustId((String) tempMap.get("cust_id"));
			contentPlan.setCustEmail((String) tempMap.get("cust_email"));
			contentPlan.setLtmhFlag((String) tempMap.get("ltmh_flag"));
			contentPlan.setLtmhSubject((String) tempMap.get("ltmh_subject"));
			contentPlan.setLtmhContent((String) tempMap.get("ltmh_content"));
			contentPlan.setFirstTime((String) tempMap.get("first_time"));

			try {
				mailSender.sendingMail("hhokyung@gmail.com", contentPlan.getLtmhFlag(), contentPlan);
			} catch (MessagingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			String partitionCurrTime = "";
			String[] parts = null;
			
			
			if (contentPlan.getLtmhFlag().equals("10")) { // 10분뒤 재복습
				partitionCurrTime = OpenStringUtils.get2HourLaterHivePartition(contentPlan.getFirstTime());
				contentPlan.setLtmhFlag("20");
			} else if (contentPlan.getLtmhFlag().equals("20")) { //2시간 뒤 재복습.
				partitionCurrTime = OpenStringUtils.get1DayLaterHivePartition(contentPlan.getFirstTime());
				contentPlan.setLtmhFlag("24");
			} else if (contentPlan.getLtmhFlag().equals("24")) { // 1일 후 재복습
				partitionCurrTime = OpenStringUtils.get7DayLaterHivePartition(contentPlan.getFirstTime());
				contentPlan.setLtmhFlag("70");
			} else if (contentPlan.getLtmhFlag().equals("70")) { // 7주일 후 재복습
				partitionCurrTime = OpenStringUtils.get30DayLaterHivePartition(contentPlan.getFirstTime());
				contentPlan.setLtmhFlag("300");
			} else if (contentPlan.getLtmhFlag().equals("300")) { // 30일 후 재복습
				partitionCurrTime = OpenStringUtils.get6MonthLaterHivePartition(contentPlan.getFirstTime());
				contentPlan.setLtmhFlag("1800");
			} else if (contentPlan.getLtmhFlag().equals("1800")) { // 180일 후 재복습. - 장기기억으로 내면화.
				partitionCurrTime = OpenStringUtils.get1DayLaterHivePartition(contentPlan.getFirstTime());
				contentPlan.setLtmhFlag("9999");
			}

			System.out.println("contentPlan.getLtmhFlag() : "+contentPlan.getLtmhFlag());
			System.out.println("next time : "+partitionCurrTime);
			
			Map<String, String> partMap = OpenStringUtils.getHivePartitionMap(partitionCurrTime);

			contentPlan.setpYear(partMap.get("pYear"));
			contentPlan.setpMonth(partMap.get("pMonth"));
			contentPlan.setpDay(partMap.get("pDay"));
			contentPlan.setpHour(partMap.get("pHour"));
			contentPlan.setpMinute(partMap.get("pMinute"));
			
			try {
				myelinQueueManager.getQueueByName("ORC_WRITE_EVENT").put(contentPlan);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// myelinQueueManager.getQueueByName("ORC_WRITE_EVENT").put(content);
	}
}
