package com.myelin.builder.core.rabbitmq;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.myelin.builder.core.config.TinyfarmerHubConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

@Component
public class RabbitMqServerManager {
	private static final Logger log = LogManager.getLogger(RabbitMqServerManager.class);

	@Autowired
	private ApplicationContext context;

	@Autowired
	private TinyfarmerHubConfig tinyConfig;
	

	protected ConnectionFactory rabbitFactory;
	
	public ConnectionFactory getRabbitFactory() {
		return rabbitFactory;
	}

	public void setRabbitFactory(ConnectionFactory rabbitFactory) {
		this.rabbitFactory = rabbitFactory;
	}

	@PostConstruct
	public void postConstruct() throws SQLException {
		log.info("Called postConstruct");
	}
	
	@PreDestroy
	public void preDesctroy() {
		log.info("Called preDesctroy");		
	}
	
	public void init() throws IOException {
		rabbitFactory = new ConnectionFactory();
		rabbitFactory.setHost(tinyConfig.getrQueueEngineIp());
		rabbitFactory.setUsername(tinyConfig.getrQueueEngineUserId());
		rabbitFactory.setPassword(tinyConfig.getrQueueEnginePasswd());
		
		log.debug("rabbitFactory: "+rabbitFactory);
	}
	

	@Scheduled(cron = "0/30 * * * * ?")
	private void checkStatus() throws SQLException, IOException, InterruptedException {
		log.debug("#### MarlinRabbitQueueServerManager cron checkStatus....");
	}
	
}
