package com.devdream.main;

import java.sql.SQLException;

import javax.swing.SwingUtilities;

import com.devdream.db.dao.DAO;
import com.devdream.ui.LoginView;
import com.devdream.ui.custom.Alert;

/**
 * Main class to run the application.
 * 
 * @author Asier Gonzalez
 */
public class App {
	public static final boolean DEBUG = false;
	
	/** Main method. Runs the application. */
	public static void main(String[] args) {
		try {
			DAO.checkTables();
		} catch (SQLException e) {
			Alert.showError(null, "Error connecting to the database.");
		}
		SwingUtilities.invokeLater(() -> new LoginView());
	}

}
