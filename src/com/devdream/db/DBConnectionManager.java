package com.devdream.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class to manage connection to the database.
 *
 * @author Asier Gonzalez
 * @version 1.0
 * @since 1.0
 */
public abstract class DBConnectionManager {

	//
	// Attributes
	private static String DB_DRIVER;
	private static String DB_CONNECTION;
	@SuppressWarnings("unused")
	private static String DB_USERNAME;
	@SuppressWarnings("unused")
	private static String DB_PASSWORD;
	
	private Connection connection;
	
	//
	// Constructors
	public DBConnectionManager() {
		DB_DRIVER = "";
		DB_CONNECTION = "";
		DB_USERNAME = "";
		DB_PASSWORD = "";
		
		setProperties();
		
		connection = getDBConnection();
	}

	//
	// Methods
	private void setProperties() {
		DBProperties properties = new DBProperties();
		
		DB_DRIVER = properties.getProperty(DBProperties.DRIVER);
		DB_CONNECTION = properties.getProperty(DBProperties.SERVER) + System.getProperty("user.dir") + "\\db\\" + properties.getProperty(DBProperties.DATABASE);
		System.out.println(DB_CONNECTION);
		
		properties.close();
	}
	
	private Connection getDBConnection() {
		try {
			Class.forName(DB_DRIVER);
			connection = DriverManager.getConnection(DB_CONNECTION);
			return connection;
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
		}
		return connection;
	}
	
	public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//
	// Getters and Setters
	public Connection getConnection() {
		return connection;
	}
	
}
