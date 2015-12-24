package com.devdream.main;

import javax.swing.SwingUtilities;

import com.devdream.ui.LoginView;

public class App {

	/** Main method. Runs the application */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new LoginView());
	}

}
