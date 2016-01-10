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

	public static final boolean DEBUG = true;
	
	/** Main method. Runs the application 
	 * @throws SQLException */
	public static void main(String[] args) {
		try {
			DAO.createTables();
		} catch (SQLException e) {
			Alert.showError(null, "Error connecting to the database.");
			System.exit(1);
		}
		SwingUtilities.invokeLater(() -> new LoginView());
	}

}
