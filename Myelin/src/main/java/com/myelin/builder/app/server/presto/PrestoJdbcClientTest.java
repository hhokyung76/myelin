package com.myelin.builder.app.server.presto;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Map;
import java.sql.DriverManager;

public class PrestoJdbcClientTest {
	private static String driverName = "com.facebook.presto.jdbc.PrestoDriver";

	/**
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		// replace "hive" here with the name of the user the queries should run
		// as
//		Connection con = DriverManager.getConnection("jdbc:presto://192.168.40.121:9987/hive/default/", "hdfs", "");
		Connection con = DriverManager.getConnection("jdbc:presto://192.168.0.42:9087/hive/default/", "hdfs", "");
		Statement stmt = con.createStatement();
		
		String query = "select * from airline";
	    ResultSet rs = stmt.executeQuery(query);
	    ResultSetMetaData rmd = rs.getMetaData();
	    System.out.println("meta columnCount: "+rmd.getColumnCount());
	    for (int ii = 1; ii <= rmd.getColumnCount(); ii++) {
	    	System.out.println(rmd.getColumnName(ii)+" => "+rmd.getColumnTypeName(ii));
	    }
//	      if (rs.next()) {
//	    	  int cnt = rs.getInt("cnt");
//	    	  System.out.println("cnt: "+cnt);
//	      }
//		stmt.executeUpdate(" insert into bluecoat.bluecoat_saction select s_action, count(*), '20160927', '2016', '09', '27', '10' from bluecoat_main_parquet2 group by s_action having count(*) > 500000");
		stmt.close();
		con.close();
	}
}