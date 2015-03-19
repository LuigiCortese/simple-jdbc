package com.mui.jdbcproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Main {

	String userName = "****";
	String password = "****";
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
			
			connection = connect();
			
			createTable();
			
			populateTable();
			
			viewTable();
			
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
	 * Create table SUPPLIERS
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
	
	/**
	 * Populate table SUPPLIERS
	 * 
	 * @throws SQLException
	 */
	public void populateTable() throws SQLException {

	    Statement stmt = null;
	    try {
	        stmt = connection.createStatement();
	        stmt.executeUpdate(
	        	"insert into " + dbName +".SUPPLIERS values(49, 'Superior Coffee', '1 Party Place', 'Mendocino', 'CA', '95460')");
	        stmt.executeUpdate(
	            "insert into " + dbName +".SUPPLIERS values(101, 'Acme, Inc.', '99 Market Street', 'Groundsville', 'CA', '95199')");
	        stmt.executeUpdate(
	            "insert into " + dbName +".SUPPLIERS values(150, 'The High Ground', '100 Coffee Lane', 'Meadows', 'CA', '93966')");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (stmt != null) { stmt.close(); }
	    }
	}
	
	/**
	 * Query table SUPPLIERS
	 * 
	 * @param con
	 * @throws SQLException
	 */
	public void viewTable() throws SQLException {
		Statement stmt = null;
		String query = "select SUP_ID, SUP_NAME, STREET, CITY, STATE, ZIP from SUPPLIERS";
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int supplierID = rs.getInt("SUP_ID");
				String supplierName = rs.getString("SUP_NAME");
				String street = rs.getString("STREET");
				String city = rs.getString("CITY");
				String state = rs.getString("STATE");
				String zip = rs.getString("ZIP");
				System.out.println(supplierName + "(" + supplierID + "): "
						+ street + ", " + city + ", " + state + ", " + zip);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}
}
