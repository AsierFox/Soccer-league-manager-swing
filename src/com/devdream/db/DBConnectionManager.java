package com.devdream.db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.devdream.ui.custom.Alert;

/**
 * Class to manage the connections to the database.
 *
 * @author Asier Gonzalez
 */
public abstract class DBConnectionManager {

	//
	// Global
	public static final String CONNECT_ERROR_MSG = "Error connection to the database!";
	
	public static final String DB_PATH = "db" + File.separator;
	public static final String DB_FILE_EXT = ".db3";
	public static final String DB_FILENAME = "soccermanager" + DB_FILE_EXT;
	
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
	}

	//
	// Methods
	/** Sets the properties needed to connect to the database. */
	private void setProperties() {
		try {
			DBProperties properties = new DBProperties();

			DB_DRIVER = properties.getProperty(DBProperties.DRIVER);
			DB_CONNECTION = properties.getProperty(DBProperties.SERVER) + DB_PATH
					+ properties.getProperty(DBProperties.DATABASE);
			
			if (properties != null) properties.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** Connects to the loaded database */
	public Connection getConnection() {
		try {
			Class.forName(DB_DRIVER);
			connection = DriverManager.getConnection(DB_CONNECTION);
			return connection;
		} catch (ClassNotFoundException | SQLException e) {
			Alert.showError(null, e.getMessage());
		}
		return connection;
	}
	
	/**
	 * Close the connection to the database.
	 */
	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch(SQLException e) {
				Alert.showError(null, "Error closing the database connection.");
			}
		}
	}
	
	/**
	 * Close the connection including the PreparedStatement.
	 * @param preparedStatement
	 */
	public void closeConnection(PreparedStatement preparedStatement) {
		try {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		} catch(SQLException e) {
			Alert.showError(null, "Error closing the database Statement.");
		}
		finally {
			closeConnection();
		}
	}
	
	/**
	 * Close the connection including the PreparedStatement and ResultSet
	 * @param preparedStatement
	 * @param resultSet
	 */
	public void closeConnection(PreparedStatement preparedStatement, ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (SQLException e) {
			Alert.showError(null, "Error closing the database ResultSet.");
		}
		finally {
			closeConnection(preparedStatement);
		}
	}
	
}
