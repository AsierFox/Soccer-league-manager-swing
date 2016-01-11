package com.devdream.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class that grabs the properties from the properties file to load the
 * information of the database.
 *
 * @author Asier Gonzalez
 */
class DBProperties {

	//
	// Global
	private static final String DIR_PATH = "db" + File.separator + "conf" + File.separator;
	private static final String FILE_NAME = "database.properties";
	
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
	/** Loads the properties from the file. */
	protected DBProperties() throws IOException {
		properties = new Properties();
		input = new FileInputStream(DIR_PATH + FILE_NAME);
		properties.load(input);
	}
	
	//
	// Methods
	/**
	 * Gets a specific property from the properties file.
	 * @param PROPERTY The property to select
	 * @return The String value of the property
	 */
	protected String getProperty(final String PROPERTY) {
		return properties.getProperty(PROPERTY);
	}
	
	/** Closes the input stream that reads the properties file. */
	protected void close() throws IOException {
		input.close();
	}

}
