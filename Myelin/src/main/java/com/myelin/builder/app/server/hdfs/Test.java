package com.myelin.builder.app.server.hdfs;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.List;

import org.apache.calcite.avatica.com.google.protobuf.Timestamp;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.serde2.io.TimestampWritable;
import org.apache.hadoop.hive.serde2.objectinspector.StructField;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.util.Progressable;

import com.myelin.builder.framework.core.orc.OrcField;
import com.myelin.builder.framework.core.orc.OrcRow;
import com.myelin.builder.framework.core.orc.OrcWriterFromJson;
import com.myelin.builder.framework.util.OpenStringUtils;

public class Test {

	public static void mai99n(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

		System.setProperty("HADOOP_USER_NAME", "hdfs");
		System.setProperty("HADOOP_GROUP_NAME", "hdfs");
		Configuration conf = new Configuration();
//		conf.set("fs.default.name", "hdfs://192.168.40.121:8020");
		conf.set("fs.defaultFS", "hdfs://master.hadoop.com:8020");

		String path = "/tmp/file127-t4012.orc";

		OrcWriterFromJson orcWriter = new OrcWriterFromJson();
		try {

			// conf = new Configuration();
			// FileSystem fs = FileSystem.getLocal(conf);
			List<StructField> fieldList = new ArrayList<StructField>();
			OrcField field1 = new OrcField("field1", PrimitiveObjectInspectorFactory.writableLongObjectInspector, 0);
			OrcField field2 = new OrcField("field2", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 1);
			OrcField field3 = new OrcField("field3", PrimitiveObjectInspectorFactory.writableTimestampObjectInspector, 2);
			fieldList.add(field1);
			fieldList.add(field2);
			fieldList.add(field3);

			orcWriter.createWriter(conf, path, fieldList);

			String content = "<36>[SNIPER-0000] [Attack_Name=(2335)Ethereal SMB Malformed Packet DoS], [Time=2016/09/27 15:42:07], [Hacker=192.168.40.200], [Victim=192.168.40.255], [Protocol=udp/138], [Risk=High], [Handling=Alarm], [Information=], [SrcPort=138], [HackType=01100]";

			for (int ii = 0; ii < 1000; ii++) {
				OrcRow orcRecord = new OrcRow(3);
				orcRecord.setFieldValue(0, new LongWritable(ii));
				orcRecord.setFieldValue(1, new Text(ii+content));
				orcRecord.setFieldValue(2, new TimestampWritable());
				orcWriter.addRow(orcRecord);
				if (ii % 10 == 0)
					System.out.println(ii + " " + OpenStringUtils.getCurrentTime());
			}

			System.out.println("end; " + OpenStringUtils.getCurrentTime());
			orcWriter.closeWriter();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void mai21n(String[] args) throws IOException, URISyntaxException {
	    Configuration conf = new Configuration();
	    FileSystem fs = FileSystem.get(new URI("hdfs://master.hadoop.com:8020/"), conf);
	    FileStatus[] fileStatus = fs.listStatus(new Path("hdfs://master.hadoop.com:8020/"));
	    for(FileStatus status : fileStatus){
	        System.out.println(status.getPath().toString());
	    }
	}
	public static void main(String[] args) throws IOException, URISyntaxException {
//		Configuration conf = new Configuration();
//		conf.set("fs.defaultFS","hdfs://master.hadoop.com:8020");
//		FileSystem fs = FileSystem.get(conf);

	    System.setProperty("hadoop.home.dir", "/");
		//System.setProperty("hadoop.home.dir", "e:\\hadoop\\");

		System.setProperty("HADOOP_USER_NAME", "hdfs");
		System.setProperty("HADOOP_GROUP_NAME", "hive");
		System.setProperty("hadoop.proxyuser.admin.groups", "*");
		System.setProperty("hadoop.proxyuser.admin.hosts", "*");
		Configuration conf = new Configuration();
//		conf.addResource(new Path("/etc/hadoop/2.6.0.3-8/0/core-site.xml"));
//		conf.addResource(new Path("/etc/hadoop/2.6.0.3-8/0/hdfs-site.xml"));
//		conf.addResource(new Path("/etc/hadoop/2.6.0.3-8/0/mapred-site.xml"));
		 
        //conf.set("hadoop.job.ugi", "hdfs");
		conf.set("fs.default.name","hdfs://192.168.0.100:8020");
		FileSystem fs = FileSystem.get(conf);
		
		FileStatus[] fsStatus = fs.listStatus(new Path("/tmp"));
		for(int i = 0; i < fsStatus.length; i++){
		   System.out.println(fsStatus[i].getPath().toString());
		}
	}
	
	public static void mai222n(String args[]) {

        try {
            UserGroupInformation ugi
                = UserGroupInformation.createRemoteUser("hdfs");

            ugi.doAs(new PrivilegedExceptionAction<Void>() {

                public Void run() throws Exception {

                    Configuration conf = new Configuration();
                    conf.set("fs.defaultFS", "hdfs://192.168.0.100:8020/user/hdfs");
                    conf.set("hadoop.job.ugi", "hdfs");

                    FileSystem fs = FileSystem.get(conf);

                    fs.createNewFile(new Path("/tmp/test"));

                    FileStatus[] status = fs.listStatus(new Path("/tmp"));
                    for(int i=0;i<status.length;i++){
                        System.out.println(status[i].getPath());
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
