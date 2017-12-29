package com.ltmh.server.process;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
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
public class LtmhOrcProcessor implements Runnable {
	private final Logger log = LogManager.getLogger(this.getClass());

	private MyelinQueueManager myelinQueueManager;

	private HadoopFileSystemUtil hadoopFileSystemUtil;

	private BlockingQueue<Object> msgQueue;
	

	private DataSource dbHiveDataSource;

	private DataSource dbPrestoDataSource;


	public DataSource getDbHiveDataSource() {
		return dbHiveDataSource;
	}

	public void setDbHiveDataSource(DataSource dbHiveDataSource) {
		this.dbHiveDataSource = dbHiveDataSource;
	}

	public DataSource getDbPrestoDataSource() {
		return dbPrestoDataSource;
	}

	public void setDbPrestoDataSource(DataSource dbPrestoDataSource) {
		this.dbPrestoDataSource = dbPrestoDataSource;
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
		msgQueue = this.myelinQueueManager.getQueueByName("ORC_WRITE_EVENT");
	}

	public LtmhOrcProcessor() {
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
				writeFile(takenMsg);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
	}

	private void writeFile(LtmContentPlan cpObj) throws IOException, InterruptedException {
		System.setProperty("HADOOP_USER_NAME", "hdfs");
		System.setProperty("HADOOP_GROUP_NAME", "supergroup");
		System.setProperty("hadoop.home.dir", "e:\\hadoop\\");
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://mdc1nn01:8020");
		conf.set("hadoop.job.ugi", "hdfs");
		conf.set("dfs.replication", "3");
		// conf.set("fs.default.name", "hdfs://192.168.0.100:8020");

		String fileName = LtmhUtils.getRandomUUID();
		String path = "/apps/hive/warehouse/myelin.db/ltmh_contents_plan/p_year=" + cpObj.getpYear() + "/p_month="
				+ cpObj.getpMonth() + "/p_day=" + cpObj.getpDay() + "/p_hour=" + cpObj.getpHour() + "/p_minute="
				+ cpObj.getpMinute() + "/";

		boolean checked = hadoopFileSystemUtil.checkHdfsPath(path);
		
		log.info("checkHdfsPath: "+checked);
		/**
		 * create partition if not exists
		 */		
		//if (!checked) 
		this.myelinQueueManager.getQueueByName("CREATE_PARTITION_EVENT").put(cpObj);
		
		String filePath = path + fileName + ".orc";
		OrcWriter orcWriter = new OrcWriter();
		try {

			// conf = new Configuration();
			// FileSystem fs = FileSystem.getLocal(conf);

			ObjectInspector ObjInspector = new OrcRowInspector(
					new OrcField("id", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 0),
					new OrcField("cust_id", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 1),
					new OrcField("cust_email", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 2),
					new OrcField("ltmh_flag", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 3),
					new OrcField("ltmh_subject", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 4),
					new OrcField("ltmh_content", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 5),
					new OrcField("create_time", PrimitiveObjectInspectorFactory.writableTimestampObjectInspector, 6),
					new OrcField("first_time", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 7),
					new OrcField("p_year", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 8),
					new OrcField("p_month", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 9),
					new OrcField("p_day", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 10),
					new OrcField("p_hour", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 11),
					new OrcField("p_minute", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 12));

			orcWriter.writer = OrcFile.createWriter(new Path(filePath),
					OrcFile.writerOptions(conf).inspector(ObjInspector).stripeSize(1000000).bufferSize(100000)
							.compress(CompressionKind.ZLIB).version(OrcFile.Version.V_0_12));

			OrcRow orcRecord = new OrcRow(13);
			orcRecord.setFieldValue(0, new Text(OpenStringUtils.getCurrentTimeFullDisplayHmmss()));
			orcRecord.setFieldValue(1, new Text(cpObj.getCustId()));
			orcRecord.setFieldValue(2, new Text(cpObj.getCustEmail()));
			orcRecord.setFieldValue(3, new Text(cpObj.getLtmhFlag()));
			orcRecord.setFieldValue(4, new Text(cpObj.getLtmhSubject()));
			orcRecord.setFieldValue(5, new Text(cpObj.getLtmhContent()));
			orcRecord.setFieldValue(6, new TimestampWritable(new Timestamp(new Date().getTime())));
			orcRecord.setFieldValue(7, new Text(cpObj.getFirstTime()));
			orcRecord.setFieldValue(8, new Text(cpObj.getpYear()));
			orcRecord.setFieldValue(9, new Text(cpObj.getpMonth()));
			orcRecord.setFieldValue(10, new Text(cpObj.getpDay()));
			orcRecord.setFieldValue(11, new Text(cpObj.getpHour()));
			orcRecord.setFieldValue(12, new Text(cpObj.getpMinute()));
			orcWriter.writer.addRow(orcRecord);

			orcWriter.writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
