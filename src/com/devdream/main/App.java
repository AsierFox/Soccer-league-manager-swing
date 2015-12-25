package com.devdream.main;

import javax.swing.SwingUtilities;

import com.devdream.controller.LoadController;
import com.devdream.ui.MainView;

public class App {

	/** Main method. Runs the application */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new MainView(new LoadController()));
	}

}
