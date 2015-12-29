package com.devdream.main;

import javax.swing.SwingUtilities;

import com.devdream.ui.LoginView;

/**
 * Main class to run the application.
 * 
 * @author Asier Gonzalez
 */
public class App {

	public static final boolean DEBUG = true;
	
	/** Main method. Runs the application */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new LoginView());
	}

}
