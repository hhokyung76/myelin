package com.ltmh.server.manager;



import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.annotation.PreDestroy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ltmh.framework.util.OpenStringUtils;
import com.ltmh.server.util.LtmhUtils;

@Component
public class MyelinQueueManager {
	private final Logger log = LogManager.getLogger(this.getClass());
	
	@SuppressWarnings("rawtypes")
	private Map<String, BlockingQueue<Object>> queueMap;

	public Map<String, BlockingQueue<Object>> getQueueMap() {
		return queueMap;
	}

	public void setQueueMap(Map<String, BlockingQueue<Object>> queueMap) {
		this.queueMap = queueMap;
	}
	
	
	@PreDestroy
	public void preDestroy() throws IOException, InterruptedException {
		log.info("Called preDestroy");

	}

	

	public MyelinQueueManager() {
		queueMap = new HashMap<String, BlockingQueue<Object>>();

		BlockingQueue<Object> eventLogMsgQueue = new ArrayBlockingQueue<Object>(10000);
		queueMap.put("ORC_WRITE_EVENT", eventLogMsgQueue);
		
		BlockingQueue<Object> eventCreatPartitionQueue = new ArrayBlockingQueue<Object>(10000);
		queueMap.put("CREATE_PARTITION_EVENT", eventCreatPartitionQueue);
		
		
	}
	
	public BlockingQueue<Object> getQueueByName(String eventName) {
		return queueMap.get(eventName);
	}

	public void putObjectToQueue(Integer eventName, String message) throws InterruptedException {
		//log.info("EVENT_NAME:"+eventName);
//		log.info("queueMap:"+queueMap.get(eventName));
		//log.info("message:"+message);
		BlockingQueue<Object> temp = queueMap.get(eventName);
		temp.put(message);
	}

	public Object takeObjectFromQueue(Integer eventName) throws InterruptedException {
		Object returnVal = null;
		BlockingQueue<Object> temp = queueMap.get(eventName);
		if (temp.size() > 0) {
			returnVal = queueMap.get(eventName).take();
		}
		return returnVal;
	}
	
	public void status() {
		log.info("-----------------------------------------------------------------------------");
		log.info("* current time: "+OpenStringUtils.getCurrentTime());
		log.info("ORC_WRITE_EVENT Queue size: "+queueMap.get("ORC_WRITE_EVENT").size());
		log.info("CREATE_PARTITION_EVENT Queue size: "+queueMap.get("CREATE_PARTITION_EVENT").size());
		log.info("-----------------------------------------------------------------------------");
		
	}
	
	public void shutdownQueue() throws InterruptedException {

		for( Map.Entry<String, BlockingQueue<Object>> elem : queueMap.entrySet() ){
            System.out.println( String.format("키 : %s, 값 : %s", elem.getKey(), elem.getValue()) );
            
            BlockingQueue<Object> tempQueue = elem.getValue();
            tempQueue.put("##FORCE_STOP");
            
        }
	}
	

	public void statusQueues() throws InterruptedException {

		for( Map.Entry<String, BlockingQueue<Object>> elem : queueMap.entrySet() ){
            log.info( String.format("MapName : %s, Size : %s", elem.getKey(), elem.getValue().size()) );
            
            
        }
	}

}
