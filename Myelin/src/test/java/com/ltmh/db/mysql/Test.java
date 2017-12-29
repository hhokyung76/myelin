package com.ltmh.db.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {
	public static void main(String[] args) {
		Connection connection = null;
		Statement st = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mariadb://192.168.0.44:3306/myelin", "myelin", "myelin!23");
			st = connection.createStatement();

			String sql;
			sql = "select * FROM engkor where number = 111;";

			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				String number = rs.getString("number");
				String word = rs.getString("word");
				String description = rs.getString("description");
				System.out.println(number+" "+word+" "+description);
			}

			rs.close();
			st.close();
			connection.close();
		} catch (SQLException se1) {
			se1.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException se2) {
			}
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

}