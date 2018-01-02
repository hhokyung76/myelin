package com.myelin.builder.framework.core.orc;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.security.UserGroupInformation;

import com.myelin.builder.framework.util.OpenStringUtils;

import org.apache.hadoop.hive.ql.io.orc.*;
import org.apache.hadoop.hive.serde2.io.TimestampWritable;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.SettableStructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructField;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

public class OrcWriter {

	private Configuration conf = new Configuration();
	public Writer writer;	
	
	public OrcWriter() {
//		System.setProperty("HADOOP_USER_NAME", "hdfs");
//		System.setProperty("HADOOP_GROUP_NAME", "supergroup");
//		System.setProperty("hadoop.home.dir", "e:\\hadoop\\");
//		Configuration conf = new Configuration();
//		conf.set("fs.defaultFS", "hdfs://mdc1nn01:8020");	
//		conf.set("hadoop.job.ugi", "hdfs");	
//		conf.set("dfs.replication", "1");
	}



	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

		UserGroupInformation ugi
         = UserGroupInformation.createRemoteUser("hdfs");

		System.setProperty("HADOOP_USER_NAME", "hdfs");
		System.setProperty("HADOOP_GROUP_NAME", "supergroup");
		System.setProperty("hadoop.home.dir", "e:\\hadoop\\");
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://mdc1nn01:8020");	
		conf.set("hadoop.job.ugi", "hdfs");	
		conf.set("dfs.replication", "1");
//		conf.set("fs.default.name", "hdfs://192.168.0.100:8020");		
		
		String path = "/apps/hive/warehouse/myelin.db/ltmh_contents_plan/p_year=2017/p_month=201711/p_day=20171117/p_hour=2017111716/p_minute=201711171630/temp0211.orc";

		OrcWriter orcWriter = new OrcWriter();
		try {

//			conf = new Configuration();
//			FileSystem fs = FileSystem.getLocal(conf);

			ObjectInspector ObjInspector = new OrcRowInspector(
					new OrcField("id", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 0),
					new OrcField("cust_id", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 1),
					new OrcField("cust_email", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 2),
					new OrcField("ltmh_flag", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 3),
					new OrcField("ltmh_content", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 4),
					new OrcField("create_time", PrimitiveObjectInspectorFactory.writableTimestampObjectInspector, 5),
					new OrcField("p_year", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 6),
					new OrcField("p_month", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 7),
					new OrcField("p_day", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 8),
					new OrcField("p_hour", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 9),
					new OrcField("p_minute", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 10));
			orcWriter.writer = OrcFile.createWriter(new Path(path),
					OrcFile.writerOptions(conf).inspector(ObjInspector).stripeSize(1000000).bufferSize(100000)
							.compress(CompressionKind.ZLIB).version(OrcFile.Version.V_0_12));

			String content = "dfskadfsklfdsajkl";
			
			for(int ii = 0; ii < 1000000; ii++) {
				OrcRow orcRecord = new OrcRow(11);
				orcRecord.setFieldValue(0, new Text(Integer.toString(ii)));
				orcRecord.setFieldValue(1, new Text("hhokyung"));
				orcRecord.setFieldValue(2, new Text("hhokyung@gmail.com"));
				orcRecord.setFieldValue(3, new Text("00"));
				orcRecord.setFieldValue(4, new Text(content+ii));
				orcRecord.setFieldValue(5, new TimestampWritable(new Timestamp(new Date().getTime())));
				orcRecord.setFieldValue(6, new Text("2017"));
				orcRecord.setFieldValue(7, new Text("201711"));
				orcRecord.setFieldValue(8, new Text("20171117"));
				orcRecord.setFieldValue(9, new Text("2017111716"));
				orcRecord.setFieldValue(10, new Text("201711171630"));
				orcWriter.writer.addRow(orcRecord);
				if (ii % 10000 == 0) 
					System.out.println(ii+" "+OpenStringUtils.getCurrentTime());
			}

			orcWriter.writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}