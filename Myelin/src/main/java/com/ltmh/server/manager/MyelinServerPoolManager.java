package com.ltmh.server.manager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;


@SuppressWarnings("serial")
@Component
public class MyelinServerPoolManager extends ThreadPoolTaskExecutor {
	private static final Logger log = LogManager.getLogger(MyelinServerPoolManager.class);
	
	@Autowired
	private ApplicationContext context;

	private Map<String, Object> threadMap;
	
	public  void putThreadMap(String name, Object threadItem) {
		threadMap.put(name, threadItem);
	}
	@PostConstruct
	public void postConstruct() {
		threadMap = new HashMap<String, Object>();
		
		log.info("postConstruct");

		this.setCorePoolSize(100);
		this.setMaxPoolSize(150);
		this.setWaitForTasksToCompleteOnShutdown(false);
		
	}

	@PreDestroy
	public void preDestroy() throws IOException {
		log.info("Called preDestroy");
		
		if (getActiveCount() > 0) {			
			this.shutdown();
		}
	}
}
