package com.ltmh.app.server.hdfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;

public class WriteFileToHdfs {

	public static void main(String[] args) throws IOException {
		BufferedReader br = null;
		try {
		    System.setProperty("hadoop.home.dir", "/");
			System.setProperty("hadoop.home.dir", "e:\\hadoop\\");

			System.setProperty("HADOOP_USER_NAME", "hdfs");
			System.setProperty("HADOOP_GROUP_NAME", "hdfs");
		    Path p=new Path("/tmp/test_file.txt");
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://192.168.0.100:8020");
			
//			conf.set("fs.defaultFS", "swebhdfs://192.168.0.42:50470");

//			conf.set("hadoop.security.authentication", "kerberos");
//			 
//			UserGroupInformation.setConfiguration(conf);
//			// Subject is taken from current user context
//			UserGroupInformation.loginUserFromSubject(null);

		    FileSystem fs = FileSystem.get(conf);
		    System.out.println(p.getName() + " exists: " + fs.exists(p));

//		    if(fs.exists(p) == false) {
		    	fs.create(p);
//		    }else {
//		    	fs.deleteOnExit(p);
//		    }
////		    FSDataOutputStream fsDataOutputStream = fs.append(p);
////
////	        PrintWriter writer  = new PrintWriter(fsDataOutputStream);
////	        for (int ii = 0; ii < 10; ii++)
////	       		writer.write(ii+"good`daskadsfklj`1848484\n");
////	        writer.close();
////	        fsDataOutputStream.close();
	        
		    br=new BufferedReader(new InputStreamReader(fs.open(p)));
		    String line = br.readLine();

		    while (line != null) {
		        System.out.println(line);
		        line=br.readLine();
		    }
		}
		finally {
		    if(br != null) br.close();
		}
	}
}
