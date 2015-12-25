package com.devdream.util;

import java.text.DecimalFormat;

/**
 * Helper class that contains static methods
 * to manage text.
 * 
 * @author Asier Gonzalez
 * @version 1.1
 * @since 1.0
 */
public class StringHelper {

	/**
	 * Checks if a String is empty or null.
	 * @param string The string to check the emptiness
	 * @return Empty or not
	 */
	public static boolean isStringNull(String s) {
		s = s.trim();
		return s != null && s.isEmpty();
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

}
