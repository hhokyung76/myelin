package com.myelin.builder.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Component;

//@Component
public class PrestoDataSourceNative {
private static String driverName = "com.facebook.presto.jdbc.PrestoDriver";
	
	private Connection prestoConnection; 
	
	
	public Connection getPrestoConnection() {
		return prestoConnection;
	}


	public void setPrestoConnection(Connection prestoConnection) {
		this.prestoConnection = prestoConnection;
	}


	public void createConnectionForPresto() throws SQLException {
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		// replace "hive" here with the name of the user the queries should run as
		prestoConnection = DriverManager.getConnection("jdbc:presto://192.168.0.42:9087/hive/default/", "hdfs", "");
	}
	
	public void testQuery() throws SQLException {
		Statement stmt = prestoConnection.createStatement();
		
		String query = "select * from airline";
	    ResultSet rs = stmt.executeQuery(query);
	    ResultSetMetaData rmd = rs.getMetaData();
	    System.out.println("meta columnCount: "+rmd.getColumnCount());
	    for (int ii = 1; ii <= rmd.getColumnCount(); ii++) {
	    	System.out.println(rmd.getColumnName(ii)+" => "+rmd.getColumnTypeName(ii));
	    }
	}
}
