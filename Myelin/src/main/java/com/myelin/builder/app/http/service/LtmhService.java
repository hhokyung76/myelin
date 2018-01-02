package com.myelin.builder.app.http.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.myelin.builder.app.entity.MyelinContentPlan;
import com.myelin.builder.app.server.hive.HiveDbConfig;
import com.myelin.builder.app.server.presto.PrestoDbConfig;
import com.myelin.builder.core.config.DatabaseHiveConfig;
import com.myelin.builder.dao.h2.DbH2Mapper;
import com.myelin.builder.dao.hive.DbHiveMapper;
import com.myelin.builder.dao.presto.DbPrestoMapper;
import com.myelin.builder.framework.mail.MailSender;
import com.myelin.builder.framework.util.OpenStringUtils;
import com.myelin.builder.server.dto.LtmhContent;
import com.myelin.builder.server.main.Customer;
import com.myelin.builder.server.manager.MyelinQueueManager;
import com.myelin.builder.server.util.LtmhUtils;
import com.mysql.fabric.xmlrpc.base.Data;

@Component
public class LtmhService {
	private static final Logger logger = LogManager.getLogger(LtmhService.class);

	@Value("${name:test2ssasssdsfasaa73}")
	private String name;

    @Autowired
    DbH2Mapper dbH2Mapper;
    

    @Autowired
    DbHiveMapper dbHiveMapper;

	@Autowired 
	MailSender mailSender;

	@Autowired
    private MyelinQueueManager myelinQueueManager;

	@Autowired
	private DatabaseHiveConfig databaseHiveConfig;
	
	private JdbcTemplate hiveTemplate;
	
	private DataSource dbHiveDataSource;
	
    //@Autowired
   // DbPrestoMapper dbPrestoMapper;
    
//    @Autowired
//    PrestoDbConfig prestoDbConfig;
//    
//
//    @Autowired
//    HiveDbConfig hiveDbConfig;

	public String getHelloMessage() throws Exception {
		logger.info("good");
//		List<RestaurantVo> list = restaurantMapper.getRestaurantsList();
//		logger.info(list.toString());
//		jdbcTemplate.query(
//                "SELECT id, name, email FROM users ", new Object[] { },
//                (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("name"), rs.getString("email"))
//        ).forEach(customer -> logger.info(customer.toString()));

		String h2Val = dbH2Mapper.getDual();
		String hiveVal = dbHiveMapper.getDual();
		//String prestoVal = dbPrestoMapper.getDual();

		logger.info("h2Val: "+h2Val);
		logger.info("hiveVal: "+hiveVal);
		//logger.info("prestoVal: "+prestoVal);
		
//		JdbcTemplate template = prestoDbConfig.jdbcTemplate();
//		List<Map<String, Object>> result = template.queryForList("select count(*) as count from ltmh_contents_plan ");
//		
//		for (int ii = 0; ii < result.size(); ii++) {
//			Map<String, Object> temp = result.get(ii);
//			logger.info("temp: "+temp);
//		}
		

//		JdbcTemplate hiveTemplate = hiveDbConfig.jdbcTemplate();
//		
//		Map<String, String> partionInfo = LtmhUtils.getLtmhPartion();
//		hiveTemplate.execute("alter table ltmh_contents_plan add partition (p_year="+partionInfo.get("pYear")
//							+", p_month="+partionInfo.get("pYear")+", p_day="+partionInfo.get("pYear")+", p_hour="+partionInfo.get("pYear") + ", p_minute="+partionInfo.get("pYear")+")");
		
		
		
		return "Helldso " + this.name;
	}
	
	public void addLtmhContentPlan(LtmhContent content) {
		MyelinContentPlan planContent = new MyelinContentPlan();
		planContent.setCustEmail(content.getCustEmail());
		planContent.setCustId(content.getCustId());
		planContent.setMyelinContent(content.getLtmhContent());
		planContent.setMyelinSubject(content.getLtmhSubject());
		planContent.setMyelinFlag("00");
		
		try {
			mailSender.sendingMail("hhokyung@gmail.com", "00", planContent);
		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String partitionCurrTime = OpenStringUtils.get10MinuteLaterHivePartition(OpenStringUtils.getCurrentTimeFullDisplayHivePartitionMinute());
		planContent.setMyelinFlag("10");
		String[] parts = partitionCurrTime.split("/");
		System.out.println("10 minute late: "+partitionCurrTime);
		
		Map<String, String> partMap = new HashMap<String, String>();
		for (int ii = 0; ii < parts.length; ii++) {
			System.out.println("parts["+ii+"]= "+parts[ii]);
			if (ii == 0) {
				partMap.put("pYear", parts[ii]);
			}else if (ii == 1) {
				partMap.put("pMonth", parts[ii]);
			}else if (ii == 2) {
				partMap.put("pDay", parts[ii]);
			}else if (ii == 3) {
				partMap.put("pHour", parts[ii]);
			}else if (ii == 4) {
				partMap.put("pMinute", parts[ii]);
			}
		}

		planContent.setpYear(partMap.get("pYear"));
		planContent.setpMonth(partMap.get("pMonth"));
		planContent.setpDay(partMap.get("pDay"));
		planContent.setpHour(partMap.get("pHour"));
		planContent.setpMinute(partMap.get("pMinute"));
		
		System.out.println("-------------------------------------------------");
		System.out.println("planContent: "+planContent.toString());
		
		try {
			myelinQueueManager.getQueueByName("ORC_WRITE_EVENT").put(planContent);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void createPartitions(String dateMinuteStr) throws ParseException {
		System.out.println("good start createPartitions.....");
		dbHiveDataSource = databaseHiveConfig.dbHiveDataSource();
		JdbcTemplate hiveJdbcTemplate = new JdbcTemplate(dbHiveDataSource);

	    DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
	    
		Date targetDateTime = dateFormat.parse(dateMinuteStr);
	    
//	    Date tomorrow = cal.getTime(); // returns new date object, one hour in the future
	    
//	    String currDateStr = df2.format(currDate);
//	    System.out.println(currDateStr);
//	    String tomorrowDateStr = df2.format(tomorrow);
//	    System.out.println(tomorrowDateStr);
	    
	    //String hiveQuery = "alter table myelin_contents_plan add partition (p_year=?, p_month=?, p_day=?, p_hour=?, p_minute=?)";
		
	    List<Object[]> parameters = new ArrayList<Object[]>();

	    List<String> querys = new ArrayList<String>();
	    
		for (int ii = 0; ii < 1440; ii++) {
			Calendar cal = Calendar.getInstance(); // creates calendar
			cal.setTime(targetDateTime); // sets calendar time/date
		    cal.add(Calendar.MINUTE, ii); // adds one hour
		    Date nextTime = cal.getTime(); 
		    String nextTimeStr = dateFormat.format(nextTime);

			String pYear = nextTimeStr.substring(0, 4);
			String pMonth = nextTimeStr.substring(0, 6);
			String pDay = nextTimeStr.substring(0, 8);
			String pHour = nextTimeStr.substring(0, 10);
			String pMinute  = nextTimeStr;
			parameters.add(
					new Object[] {pYear, pMonth, pDay, pHour, pMinute} 
			);
			String hiveQuery = "alter table myelin_contents_plan add partition (p_year="+pYear+", p_month="+pMonth+", p_day="+pDay+", p_hour="+pHour+", p_minute="+pMinute+")";
			
//			querys.add(hiveQuery);
			System.out.println(pMinute);
			hiveJdbcTemplate.execute(hiveQuery);

		}

		String[] array = (String[]) querys.toArray(new String[querys.size()]);
//		hiveJdbcTemplate.batchUpdate(querys.stream());
	}

}