package com.myelin.builder.server.process;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.stereotype.Component;

import com.myelin.builder.app.entity.MyelinContentPlan;
import com.myelin.builder.core.config.DatabaseH2Config;
import com.myelin.builder.core.config.DatabaseHiveConfig;
import com.myelin.builder.framework.core.orc.OrcField;
import com.myelin.builder.framework.core.orc.OrcRow;
import com.myelin.builder.framework.core.orc.OrcRowInspector;
import com.myelin.builder.framework.core.orc.OrcWriter;
import com.myelin.builder.framework.util.OpenStringUtils;
import com.myelin.builder.server.dto.MyelinPartitionInfo;
import com.myelin.builder.server.hdfs.HadoopFileSystemUtil;
import com.myelin.builder.server.manager.MyelinQueueManager;
import com.myelin.builder.server.util.LtmhUtils;

@Component
@Scope("prototype")
public class MyelinOrcProcessor implements Runnable {
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

	public MyelinOrcProcessor() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		MyelinContentPlan takenMsg;
		// TODO Auto-generated method stub
		while (!Thread.currentThread().isInterrupted()) {
			try {
				log.debug("Before taking message.....");
				takenMsg = (MyelinContentPlan) msgQueue.take();
				if (takenMsg.equals("##FORCE_STOP"))
					break;
				buildMyelinPlan(takenMsg);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
	}
	
	private void buildMyelinPlan(MyelinContentPlan cpObj) {
		String firstTime = OpenStringUtils.getCurrentTimeFullDisplayHivePartitionMinute();
		try {
			List<Map<String, String>> myelinPartitions = LtmhUtils.getMyelinTimeMaps(firstTime);
			
			Map<String, String> firstMap = LtmhUtils.getMyelinTimes("0-0", firstTime);
		    myelinPartitions.add(firstMap);
		    		    
		    writeMyelinContentFile(cpObj, myelinPartitions);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * myelinPartitions는 최초 입력된 myelinContentPlan의 피보나치 수열 형식 orcPath에 orc파일을 저장처리하도록 한기 위한 hive partition 정보들을 담고 있다.
	 * 이를 통해 각 파티션 폴더 생성 및 파일을 생성 처리한다.
	 * 
	 * @param cpObj
	 * @param myelinPartitions
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void writeMyelinContentFile(MyelinContentPlan cpObj, List<Map<String, String>> myelinPartitions) throws IOException, InterruptedException {
		System.setProperty("HADOOP_USER_NAME", "hdfs");
		System.setProperty("HADOOP_GROUP_NAME", "supergroup");
		System.setProperty("hadoop.home.dir", "e:\\hadoop\\");
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://mdc1nn01:8020");
		conf.set("hadoop.job.ugi", "hdfs");
		conf.set("dfs.replication", "3");
		// conf.set("fs.default.name", "hdfs://192.168.0.100:8020");

		for (int ii = 0; ii < myelinPartitions.size(); ii++) {
			Map<String, String> myelinPartInfo = myelinPartitions.get(ii);
			String mFlag = myelinPartInfo.get("MyelinFlag");
			cpObj.setMyelinFlag(mFlag);
			cpObj.setpYear(myelinPartInfo.get("pYear"));
			cpObj.setpMonth(myelinPartInfo.get("pMonth"));
			cpObj.setpDay(myelinPartInfo.get("pDay"));
			cpObj.setpHour(myelinPartInfo.get("pHour"));
			cpObj.setpMinute(myelinPartInfo.get("pMinute"));
			
			MyelinPartitionInfo pInfo = new MyelinPartitionInfo();
			pInfo.setDbName("myelin");
			pInfo.setTableName("myelin_contents_plan");
			pInfo.setpYear(cpObj.getpYear());
			pInfo.setpMonth(cpObj.getpMonth());
			pInfo.setpDay(cpObj.getpDay());
			pInfo.setpHour(cpObj.getpHour());
			pInfo.setpMinute(cpObj.getpMinute());
			
			String fileName = LtmhUtils.getRandomUUID();
			String path = "/apps/hive/warehouse/myelin.db/myelin_contents_plan/p_year=" + cpObj.getpYear() + "/p_month="
					+ cpObj.getpMonth() + "/p_day=" + cpObj.getpDay() + "/p_hour=" + cpObj.getpHour() + "/p_minute="
					+ cpObj.getpMinute() + "/";

			String filePath = path + fileName + ".orc";
			OrcWriter orcWriter = new OrcWriter();
			try {

				// conf = new Configuration();
				// FileSystem fs = FileSystem.getLocal(conf);

				ObjectInspector ObjInspector = new OrcRowInspector(
						new OrcField("id", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 0),
						new OrcField("cust_id", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 1),
						new OrcField("cust_email", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 2),
						new OrcField("myelin_roomid", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 3),
						new OrcField("myelin_flag", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 4),
						new OrcField("myelin_subject", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 5),
						new OrcField("myelin_content", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 6),
						new OrcField("create_time", PrimitiveObjectInspectorFactory.writableTimestampObjectInspector, 7),
						new OrcField("first_time", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 8),
						new OrcField("p_year", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 9),
						new OrcField("p_month", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 10),
						new OrcField("p_day", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 11),
						new OrcField("p_hour", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 12),
						new OrcField("p_minute", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 13));

				orcWriter.writer = OrcFile.createWriter(new Path(filePath),
						OrcFile.writerOptions(conf).inspector(ObjInspector).stripeSize(1000000).bufferSize(100000)
								.compress(CompressionKind.ZLIB).version(OrcFile.Version.V_0_12));

				OrcRow orcRecord = new OrcRow(14);
				orcRecord.setFieldValue(0, new Text(OpenStringUtils.getCurrentTimeFullDisplayHmmss()));
				orcRecord.setFieldValue(1, new Text(cpObj.getCustId()));
				orcRecord.setFieldValue(2, new Text(cpObj.getCustEmail()));
				orcRecord.setFieldValue(3, new Text(cpObj.getMyelinRoomId()));
				orcRecord.setFieldValue(4, new Text(cpObj.getMyelinFlag()));
				orcRecord.setFieldValue(5, new Text(cpObj.getMyelinSubject()));
				orcRecord.setFieldValue(6, new Text(cpObj.getMyelinContent()));
				orcRecord.setFieldValue(7, new TimestampWritable(new Timestamp(new Date().getTime())));
				orcRecord.setFieldValue(8, new Text(cpObj.getFirstTime()));
				orcRecord.setFieldValue(9, new Text(cpObj.getpYear()));
				orcRecord.setFieldValue(10, new Text(cpObj.getpMonth()));
				orcRecord.setFieldValue(11, new Text(cpObj.getpDay()));
				orcRecord.setFieldValue(12, new Text(cpObj.getpHour()));
				orcRecord.setFieldValue(13, new Text(cpObj.getpMinute()));
				orcWriter.writer.addRow(orcRecord);

				orcWriter.writer.close();
				

				this.myelinQueueManager.getQueueByName("CREATE_PARTITION_EVENT").put(pInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	private void writeFile(MyelinContentPlan cpObj) throws IOException, InterruptedException {
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
			orcRecord.setFieldValue(3, new Text(cpObj.getMyelinFlag()));
			orcRecord.setFieldValue(4, new Text(cpObj.getMyelinSubject()));
			orcRecord.setFieldValue(5, new Text(cpObj.getMyelinContent()));
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
