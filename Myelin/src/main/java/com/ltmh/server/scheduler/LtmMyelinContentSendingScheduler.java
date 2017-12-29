package com.ltmh.server.scheduler;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.search.FlagTerm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ltmh.app.entity.LtmContentPlan;
import com.ltmh.dao.mariadb.DbMariaSource;
import com.ltmh.framework.mail.MailSender;
import com.ltmh.framework.util.OpenStringUtils;
import com.ltmh.server.db.MariadbContentFactory;
import com.ltmh.server.manager.MyelinQueueManager;

@Component
public class LtmMyelinContentSendingScheduler {
	private final Logger log = LogManager.getLogger(this.getClass());

	@Autowired 
	MariadbContentFactory  mariadbContentFactory;
	@Autowired 
	MailSender mailSender;

	@Autowired
    private MyelinQueueManager myelinQueueManager;
	
	private static int number = 1148;

//	@Scheduled(cron = "40 0/10 7-23 * * ?")
	public void engWordToEmail() {
		try {
			number = number + 1;
			LtmContentPlan content = mariadbContentFactory.getContent(number);
			mailSender.sendingMail("hhokyung@gmail.com", "00", content);
			content.setCustId("hhokyung");
			content.setCustEmail("hhokyung@gmail.com");
			content.setFirstTime(OpenStringUtils.getCurrentTimeFullDisplayHivePartitionMinute());
			String partitionCurrTime = OpenStringUtils.get10MinuteLaterHivePartition(content.getFirstTime());
			content.setLtmhFlag("10");
			
			System.out.println("10 minute late: "+partitionCurrTime);
			
			Map<String, String> partMap = OpenStringUtils.getHivePartitionMap(partitionCurrTime);
			
			content.setpYear(partMap.get("pYear"));
			content.setpMonth(partMap.get("pMonth"));
			content.setpDay(partMap.get("pDay"));
			content.setpHour(partMap.get("pHour"));
			content.setpMinute(partMap.get("pMinute"));
			
			System.out.println("-------------------------------------------------");
			System.out.println("content: "+content.toString());
			
			myelinQueueManager.getQueueByName("ORC_WRITE_EVENT").put(content);
			
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
