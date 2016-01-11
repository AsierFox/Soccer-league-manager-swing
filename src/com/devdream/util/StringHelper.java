package com.devdream.util;

import java.io.File;
import java.text.DecimalFormat;

/**
 * Helper class that contains static methods to manage text.
 * 
 * @author Asier Gonzalez
 */
public class StringHelper {

	private StringHelper() {}
	
	/**
	 * Checks if a String is empty or null.
	 * @param string The string to check the emptiness
	 * @return True if it is an empty String
	 */
	public static boolean isStringNull(String s) {
		if (s == null) return true;
		s = s.trim();
		return s.isEmpty();
	}
	
	/**
	 * Capitalizes the first letter of a text.
	 * @param text
	 * @return
	 */
	public static String capitalizeFirst(String text) {
		return Character.toUpperCase(text.charAt(0)) + text.substring(1);
	}
	
	/**
	 * Format an amount of money.
	 * @param amount Amount of money
	 * @return
	 */
	public static String formatAmount(double amount) {
		return new DecimalFormat("0.00").format(amount);
	}

	/** Gets the file name from a path. */
	public static String getFileNameFromPath(final String FILE_PATH) {
		return FILE_PATH.substring(FILE_PATH.lastIndexOf(File.separator) + 1);
	}

}
