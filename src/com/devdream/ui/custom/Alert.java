package com.devdream.ui.custom;

import java.awt.Component;

import javax.swing.JOptionPane;

/**
 * This class shows alerts using JOptions Pane class.
 * 
 * @author Asier Gonzalez
 */
public class Alert extends JOptionPane {

	private static final long serialVersionUID = -1496885331548162635L;

	/** Displays an error alert. */
	public static void showError(Component c, String msg) {
		showError(c, "Error", msg);
	}
	
	/** Displays an error alert with personalized title. */
	public static void showError(Component c, String title, String msg) {
		showMessageDialog(c, msg, title, ERROR_MESSAGE);
	}

	/** Displays an information alert. */
	public static void showInfo(Component c, String msg) {
		showMessageDialog(c, msg, "Information", INFORMATION_MESSAGE);
	}
	
	/** Shows a confirm dialog. */
	public static int showConfirm(Component c, String title, String msg) {
		return showConfirmDialog(c, msg, title, JOptionPane.YES_NO_OPTION);
	}

}
