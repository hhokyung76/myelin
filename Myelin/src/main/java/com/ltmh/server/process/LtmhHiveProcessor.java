package com.ltmh.server.process;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import javax.sql.DataSource;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.io.orc.CompressionKind;
import org.apache.hadoop.hive.ql.io.orc.OrcFile;
import org.apache.hadoop.hive.serde2.io.TimestampWritable;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ltmh.app.entity.LtmContentPlan;
import com.ltmh.core.config.DatabaseH2Config;
import com.ltmh.core.config.DatabaseHiveConfig;
import com.ltmh.framework.core.orc.OrcField;
import com.ltmh.framework.core.orc.OrcRow;
import com.ltmh.framework.core.orc.OrcRowInspector;
import com.ltmh.framework.core.orc.OrcWriter;
import com.ltmh.framework.util.OpenStringUtils;
import com.ltmh.server.hdfs.HadoopFileSystemUtil;
import com.ltmh.server.manager.MyelinQueueManager;
import com.ltmh.server.util.LtmhUtils;

@Component
@Scope("prototype")
public class LtmhHiveProcessor implements Runnable {
	private final Logger log = LogManager.getLogger(this.getClass());

	private MyelinQueueManager myelinQueueManager;

	private HadoopFileSystemUtil hadoopFileSystemUtil;

	private BlockingQueue<Object> msgQueue;
	

	private DataSource dbHiveDataSource;

	private DataSource dbPrestoDataSource;


	private JdbcTemplate hiveTemplate;

	private JdbcTemplate prestoTemplate;

	public DataSource getDbHiveDataSource() {
		return dbHiveDataSource;
	}

	public void setDbHiveDataSource(DataSource dbHiveDataSource) {
		this.dbHiveDataSource = dbHiveDataSource;
		hiveTemplate = new JdbcTemplate(dbHiveDataSource);
	}

	public DataSource getDbPrestoDataSource() {
		return dbPrestoDataSource;
	}

	public void setDbPrestoDataSource(DataSource dbPrestoDataSource) {
		this.dbPrestoDataSource = dbPrestoDataSource;
		prestoTemplate = new JdbcTemplate(dbPrestoDataSource);
	}

	public HadoopFileSystemUtil getHadoopFileSystemUtil() {
		return hadoopFileSystemUtil;
	}

	public void setHadoopFileSystemUtil(HadoopFileSystemUtil hadoopFileSystemUtil) {
		this.hadoopFileSystemUtil = hadoopFileSystemUtil;
	}

	public MyelinQueueManager getMyelinQueueManager() {
		return myelinQueueManager;
	}

	public void setMyelinQueueManager(MyelinQueueManager myelinQueueManager) {
		this.myelinQueueManager = myelinQueueManager;
		msgQueue = this.myelinQueueManager.getQueueByName("CREATE_PARTITION_EVENT");
	}

	public LtmhHiveProcessor() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		LtmContentPlan takenMsg;
		// TODO Auto-generated method stub
		while (!Thread.currentThread().isInterrupted()) {
			try {
				log.debug("Before taking message.....");
				takenMsg = (LtmContentPlan) msgQueue.take();
				if (takenMsg.equals("##FORCE_STOP"))
					break;
				createPartition(takenMsg);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
	}

	private void createPartition(LtmContentPlan cpObj) throws IOException {
		String checkPartiton = "show partitions from ltmh_contents_plan where p_minute = '"+cpObj.getpMinute()+"'";
		
		List<Map<String, Object>> list = prestoTemplate.queryForList(checkPartiton);
		log.info("check presto show partition size: "+list.size());
		for (int ii = 0; ii < list.size(); ii++) {
		   log.info("hive temp map: "+list.get(ii));
		}
		
		if (list.size() == 0) {
			hiveTemplate.execute("alter table ltmh_contents_plan add partition (p_year="+cpObj.getpYear()+", p_month="+cpObj.getpMonth()+", p_day="+cpObj.getpDay()+", p_hour="+cpObj.getpHour()+", p_minute="+cpObj.getpMinute()+")");
		}
		
	}

}
