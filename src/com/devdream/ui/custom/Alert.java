package com.devdream.ui.custom;

import java.awt.Component;
import java.io.File;
import java.sql.SQLException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.devdream.exception.OperationCancelledException;

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
	
	/**
	 * Shows a confirm dialog.s
	 * @return Return true if OK / Confirm is pressed
	 */
	public static boolean showConfirm(Component c, String title, String msg) {
		return showConfirmDialog(c, msg, title, JOptionPane.YES_NO_OPTION) == OK_OPTION;
	}
	
	/**
	 * Shows a file chooser dialog.
	 * @param fileExtension The file extension including '.':
	 * <br>
	 * <b>Example -> showFileChooser(".csv")</b>
	 * @return The selected path
	 * @throws OperationCancelledException 
	 * @throws SQLException
	 */
	public static String showFileChooser(String fileExtension) throws OperationCancelledException {
		JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
		    File file = fileChooser.getSelectedFile();
		    if (file.getAbsolutePath().contains(fileExtension)) return file.getAbsolutePath();
		    return file.getAbsolutePath() + fileExtension;
		}
		throw new OperationCancelledException();
	}

}
