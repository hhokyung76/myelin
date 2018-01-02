package com.myelin.builder.app.server.presto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class PrestoTest {
	private static String driverName = "com.facebook.presto.jdbc.PrestoDriver";

	public static void main(String[] args) throws SQLException {
		//System.setProperty("HADOOP_USER_NAME", "hdfs");
		//System.setProperty("HADOOP_PROXY_USER", "hdfs");
		// URL parameters
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		String url = "jdbc:presto://192.168.0.42:9087/hive/default";
//		Connection conn = DriverManager.getConnection(url, properties);
//		Connection conn = DriverManager.getConnection(url, "hdfs", null); //DriverManager.getConnection(url, properties);
		Connection conn = DriverManager.getConnection("jdbc:presto://192.168.0.42:9087/hive/default/", "hdfs", "");
		Connection con  = DriverManager.getConnection("jdbc:presto://192.168.0.42:9087/hive/default/", "hdfs", "");
		
		Statement stmt = null;
        ResultSet rs = null;

        String query = "SELECT count(*) FROM airline";
        try {
            stmt = con.createStatement();

            rs = stmt.executeQuery(query);

            while (rs.next()) { 
                int empno = rs.getInt(1);
                System.out.println( 
                    empno ); 
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                con.close(); 
            } catch ( SQLException e ) {}
        }
		// properties
//		String url = "jdbc:presto://example.net:8080/hive/sales?user=test&password=secret&SSL=true";
//		Connection connection = DriverManager.getConnection(url)
	}
}
