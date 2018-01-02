package com.myelin.builder.dao.mariadb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.myelin.builder.app.entity.MyelinContentPlan;

@Component
public class DbMariaSource {
	private final Logger log = LogManager.getLogger(this.getClass());
	
	private Connection connection = null;

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}


}