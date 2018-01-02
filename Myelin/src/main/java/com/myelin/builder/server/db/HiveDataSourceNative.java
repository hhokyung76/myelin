package com.myelin.builder.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Component;

//@Component
public class HiveDataSourceNative {
	private static String driverName = "org.apache.hive.jdbc.HiveDriver";
	
	private Connection hiveConnection; 
	
	public Connection getHiveConnection() {
		return hiveConnection;
	}

	public void setHiveConnection(Connection hiveConnection) {
		this.hiveConnection = hiveConnection;
	}

	public void createConnectionForHive() throws SQLException {
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		// replace "hive" here with the name of the user the queries should run as
		hiveConnection = DriverManager.getConnection("jdbc:hive2://192.168.0.42:10000/ltmh", "hdfs", "");
	}
	
	public void testQuery() throws SQLException {
		Statement stmt = hiveConnection.createStatement();
		
		String query = "select * from ltmh_contents_plan";
	    ResultSet rs = stmt.executeQuery(query);
	    ResultSetMetaData rmd = rs.getMetaData();
	    System.out.println("hive meta columnCount: "+rmd.getColumnCount());
	    for (int ii = 1; ii <= rmd.getColumnCount(); ii++) {
	    	System.out.println("hive : "+rmd.getColumnName(ii)+" => "+rmd.getColumnTypeName(ii));
	    }
	}
}
