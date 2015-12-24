package com.devdream.util;

/**
 * This class helps us to manage Math
 * operations.
 * 
 * @author Asier Gonzalez
 * @version 1.0
 * @since 1.0
 */
public class MathHelper {
	
	/**
	 * Checks if a number is negative.
	 * 
	 * @param num The number to check with
	 * @return Negative or not
	 */
	public static boolean isNegativeNumber(int num) {
		return num < 0;
	}
	
	/**
	 * Checks if a String is numerical.
	 * 
	 * @param string The string to check with
	 * @return Numeric or not
	 */
	public static boolean isNumeric(String s) {
		return s.matches("[-+]?\\d*\\.?\\d+");
	}
	
}
