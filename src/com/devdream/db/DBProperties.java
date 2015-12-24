package com.devdream.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class that grabs the properties from the properties file to load the
 * information of the database.
 *
 * @author Asier Gonzalez
 * @version 1.0
 * @since 1.0
 */
class DBProperties {

	//
	// Global
	private static final String FILE_NAME = "database.properties";
	private static final String PROPERTIES_FILE_PATH = System.getProperty("user.dir") + "\\db\\"; // TODO change to relative path
	
	protected static final String DRIVER   = "driver";
	protected static final String SERVER   = "server";
	protected static final String DATABASE = "database";
	protected static final String USERNAME = "username";
	protected static final String PASSWORD = "password";

	//
	// Attributes
	private Properties properties;
	private InputStream input;
	
	//
	// Constructors
	protected DBProperties() {
		properties = new Properties();
		try {
			input = new FileInputStream(PROPERTIES_FILE_PATH + FILE_NAME);
			properties.load(input);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//
	// Methods
	protected String getProperty(final String PROPERTY) {
		return properties.getProperty(PROPERTY);
	}
	
	protected void close() {
		try {
			input.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
}
