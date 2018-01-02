package com.myelin.builder.core.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableAsync
//@PropertySource("classpath:node-config.properties")
public class TinyfarmerHubConfig {
	private static final Logger log = LogManager.getLogger(TinyfarmerHubConfig.class);
	
	@Autowired
	Environment env;
	
	private boolean isMasterNode;
	private boolean isCoordinator;
	private String nodeId;
	private String nodeIp;
	private String[] clusterNodeIpList;
	private String hazelBusUrl;
	private String MQTYPE;
	private String hdfsUrl;
	
	private String marlinRootPath;

	private String hiveDriverClassName;
	private String hiveJdbUrl;
	private String hiveUserName;
	private String hivePassword;

	private String mysqlDriverClassName;
	private String mysqlJdbUrl;
	private String mysqlUserName;
	private String mysqlPassword;

	private String prestoDriverClassName;
	private String prestoJdbUrl;
	private String prestoUserName;
	private String prestoPassword;
	

	private String rQueueEngineUserId;
	private String rQueueEnginePasswd;
	private String rQueueEngineIp;
	
	private boolean isRemovingTrash;
	
	
	
	public String[] getClusterNodeIpList() {
		return clusterNodeIpList;
	}

	public void setClusterNodeIpList(String[] clusterNodeIpList) {
		this.clusterNodeIpList = clusterNodeIpList;
	}

	public String getHiveDriverClassName() {
		return hiveDriverClassName;
	}

	public void setHiveDriverClassName(String hiveDriverClassName) {
		this.hiveDriverClassName = hiveDriverClassName;
	}

	public String getHiveJdbUrl() {
		return hiveJdbUrl;
	}

	public void setHiveJdbUrl(String hiveJdbUrl) {
		this.hiveJdbUrl = hiveJdbUrl;
	}

	public String getHiveUserName() {
		return hiveUserName;
	}

	public void setHiveUserName(String hiveUserName) {
		this.hiveUserName = hiveUserName;
	}

	public String getHivePassword() {
		return hivePassword;
	}

	public void setHivePassword(String hivePassword) {
		this.hivePassword = hivePassword;
	}

	public String getHdfsUrl() {
		return hdfsUrl;
	}

	public void setHdfsUrl(String hdfsUrl) {
		this.hdfsUrl = hdfsUrl;
	}

	public String getMarlinRootPath() {
		return marlinRootPath;
	}

	public void setMarlinRootPath(String marlinRootPath) {
		this.marlinRootPath = marlinRootPath;
	}

	public boolean isMasterNode() {
		return isMasterNode;
	}

	public void setMasterNode(boolean isMasterNode) {
		this.isMasterNode = isMasterNode;
	}

	public boolean isCoordinator() {
		return isCoordinator;
	}

	public void setCoordinator(boolean isCoordinator) {
		this.isCoordinator = isCoordinator;
	}

	public String getHazelBusUrl() {
		return hazelBusUrl;
	}

	public void setHazelBusUrl(String hazelBusUrl) {
		this.hazelBusUrl = hazelBusUrl;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}


	public String getNodeIp() {
		return nodeIp;
	}

	public void setNodeIp(String nodeIp) {
		this.nodeIp = nodeIp;
	}

	@PostConstruct
	public void initNotificationPostConstruct() {
	    log.info("Post Construct");
	}
	
	public String getMQTYPE() {
		return MQTYPE;
	}

	public void setMQTYPE(String mQTYPE) {
		MQTYPE = mQTYPE;
	}
	
	public String getMysqlDriverClassName() {
		return mysqlDriverClassName;
	}

	public void setMysqlDriverClassName(String mysqlDriverClassName) {
		this.mysqlDriverClassName = mysqlDriverClassName;
	}

	public String getMysqlJdbUrl() {
		return mysqlJdbUrl;
	}

	public void setMysqlJdbUrl(String mysqlJdbUrl) {
		this.mysqlJdbUrl = mysqlJdbUrl;
	}

	public String getMysqlUserName() {
		return mysqlUserName;
	}

	public void setMysqlUserName(String mysqlUserName) {
		this.mysqlUserName = mysqlUserName;
	}

	public String getMysqlPassword() {
		return mysqlPassword;
	}

	public void setMysqlPassword(String mysqlPassword) {
		this.mysqlPassword = mysqlPassword;
	}

	public String getPrestoDriverClassName() {
		return prestoDriverClassName;
	}

	public void setPrestoDriverClassName(String prestoDriverClassName) {
		this.prestoDriverClassName = prestoDriverClassName;
	}

	public String getPrestoJdbUrl() {
		return prestoJdbUrl;
	}

	public void setPrestoJdbUrl(String prestoJdbUrl) {
		this.prestoJdbUrl = prestoJdbUrl;
	}

	public String getPrestoUserName() {
		return prestoUserName;
	}

	public void setPrestoUserName(String prestoUserName) {
		this.prestoUserName = prestoUserName;
	}

	public String getPrestoPassword() {
		return prestoPassword;
	}

	public void setPrestoPassword(String prestoPassword) {
		this.prestoPassword = prestoPassword;
	}

	public String getrQueueEngineUserId() {
		return rQueueEngineUserId;
	}

	public void setrQueueEngineUserId(String rQueueEngineUserId) {
		this.rQueueEngineUserId = rQueueEngineUserId;
	}

	public String getrQueueEnginePasswd() {
		return rQueueEnginePasswd;
	}

	public void setrQueueEnginePasswd(String rQueueEnginePasswd) {
		this.rQueueEnginePasswd = rQueueEnginePasswd;
	}

	public String getrQueueEngineIp() {
		return rQueueEngineIp;
	}

	public void setrQueueEngineIp(String rQueueEngineIp) {
		this.rQueueEngineIp = rQueueEngineIp;
	}

	public boolean isRemovingTrash() {
		return isRemovingTrash;
	}

	public void setRemovingTrash(boolean isRemovingTrash) {
		this.isRemovingTrash = isRemovingTrash;
	}

	public void setConfigurationInfo() {
		env.getProperty("");
	}
	public void getNodeConfigurationInfo(String confPath) throws IOException {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream(confPath+"node-config.properties");
        props.load(new java.io.BufferedInputStream(fis));
        
		isMasterNode = Boolean.parseBoolean(props.getProperty("node.ismasternode"));
		isCoordinator = Boolean.parseBoolean(props.getProperty("node.coordinator"));
		nodeId = props.getProperty("node.id");
		nodeIp = props.getProperty("node.ip");
		
		String nodeClusterIpList = props.getProperty("node.cluster.iplist");
		String[] ipList = nodeClusterIpList.split(",");
		this.clusterNodeIpList = ipList;
		
		hazelBusUrl = props.getProperty("node.master.url");
		MQTYPE = props.getProperty("node.mq.type");
		hdfsUrl = props.getProperty("hdfs.url");

		hiveDriverClassName = props.getProperty("hive.driver.classname");
		hiveJdbUrl = props.getProperty("hive.jdbc.url");
		hiveUserName = props.getProperty("hive.username");
		hivePassword = props.getProperty("hive.password");
		
		mysqlDriverClassName = props.getProperty("mysql.driver.classname");
		mysqlJdbUrl = props.getProperty("mysql.jdbc.url");
		mysqlUserName = props.getProperty("mysql.username");
		mysqlPassword = props.getProperty("mysql.password");

		
		prestoDriverClassName = props.getProperty("presto.driver.classname");
		prestoJdbUrl = props.getProperty("presto.jdbc.url");
		prestoUserName = props.getProperty("presto.username");
		prestoPassword = props.getProperty("presto.password");
		

		rQueueEngineIp = props.getProperty("rabbit.ip");
		rQueueEngineUserId = props.getProperty("rabbit.username");
		rQueueEnginePasswd = props.getProperty("rabbit.password");
		

		isRemovingTrash = Boolean.parseBoolean(props.getProperty("hdfs.removetrash"));
	}




	@Override
	public String toString() {
		return "MarlinConfig [env=" + env + ", isMasterNode=" + isMasterNode + ", nodeId=" + nodeId + ", nodeIp="
				+ nodeIp + ", hazelBusUrl=" + hazelBusUrl + ", MQTYPE=" + MQTYPE + ", hdfsUrl=" + hdfsUrl
				+ ", marlinRootPath=" + marlinRootPath + ", hiveDriverClassName=" + hiveDriverClassName
				+ ", hiveJdbUrl=" + hiveJdbUrl + ", hiveUserName=" + hiveUserName + ", hivePassword=" + hivePassword
				+ ", mysqlDriverClassName=" + mysqlDriverClassName + ", mysqlJdbUrl=" + mysqlJdbUrl + ", mysqlUserName="
				+ mysqlUserName + ", mysqlPassword=" + mysqlPassword + ", prestoDriverClassName="
				+ prestoDriverClassName + ", prestoJdbUrl=" + prestoJdbUrl + ", prestoUserName=" + prestoUserName
				+ ", prestoPassword=" + prestoPassword + ", rQueueEngineUserId=" + rQueueEngineUserId
				+ ", rQueueEnginePasswd=" + rQueueEnginePasswd + ", rQueueEngineIp=" + rQueueEngineIp
				+ ", isRemovingTrash=" + isRemovingTrash + "]";
	}

		
	
}
