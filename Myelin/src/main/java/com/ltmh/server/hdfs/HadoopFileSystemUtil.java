package com.ltmh.server.hdfs;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Component;

@Component
public class HadoopFileSystemUtil {
	private static final Logger logger = Logger.getLogger(HadoopFileSystemUtil.class.getName());

	private String path = "/user/hdfs/example/hdfs/";
	private String fileName = "hello.csv";
	private String fileContent = "hello;world";
	private String hdfsuri = "hdfs://192.168.0.43:8020";

	private FileSystem fs;

	public void init() throws IOException {

		// ====== Init HDFS File System Object
		Configuration conf = new Configuration();
		// Set FileSystem URI
		conf.set("fs.defaultFS", hdfsuri);
		// Because of Maven
		conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
		conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
		// Set HADOOP user
		System.setProperty("HADOOP_USER_NAME", "hdfs");
		System.setProperty("hadoop.home.dir", "e:\\hadoop\\");
		// Get the filesystem - HDFS
		fs = FileSystem.get(URI.create(hdfsuri), conf);

		// ==== Create folder if not exists
		Path workingDir = fs.getWorkingDirectory();

	}

	public boolean checkHdfsPath(String path) throws IOException {
		Path newFolderPath = new Path(path);
		if (!fs.exists(newFolderPath)) {
			// Create new Directory
			fs.mkdirs(newFolderPath);
			logger.info("Path " + path + " created.");
		} else {
			logger.info("Path " + path + " is exists.");
		}
		
		return true;
	}
}
