package com.mui.jdbcproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import java.sql.Statement;

public class Main {

	String userName = "**";
	String password = "**";
	String dbms = "mysql";
	String serverName = "localhost";
	String portNumber = "3306";
	String dbName = "test";
	Connection connection = null;

	public static void main(String[] args) {
		new Main().operate();
	}

	void operate() {
		try {
			//create connection
			connection = connect();
			
			//execute DDL
			createTable();
			
			//execute Query
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {connection.close();} 
			catch (SQLException e) {e.printStackTrace();}
		}
	}

	/**
	 * Create connection
	 * 
	 * @return
	 * @throws SQLException
	 */
	Connection connect() throws SQLException {
		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", this.userName);
		connectionProps.put("password", this.password);

		String url = "jdbc:" + dbms + "://" + serverName + ":" + portNumber
				+ "/" + dbName;
		conn = DriverManager.getConnection(url, connectionProps);

		System.out.println("Connected to database");
		return conn;
	}

	/**
	 * Create a table
	 * 
	 * @throws SQLException
	 */
	public void createTable() throws SQLException {
		String createString = "create table " + dbName + ".SUPPLIERS "
				+ "(SUP_ID integer NOT NULL, "
				+ "SUP_NAME varchar(40) NOT NULL, "
				+ "STREET varchar(40) NOT NULL, "
				+ "CITY varchar(20) NOT NULL, " + "STATE char(2) NOT NULL, "
				+ "ZIP char(5), " + "PRIMARY KEY (SUP_ID))";

		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(createString);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}
}
