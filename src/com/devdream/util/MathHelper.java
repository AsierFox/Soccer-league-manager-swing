package com.devdream.util;

/**
 * This class helps us to manage Math operations.
 * 
 * @author Asier Gonzalez
 */
public class MathHelper {

	/**
	 * Checks if a number is negative.
	 * @param num The number to check with
	 * @return Negative or not
	 */
	public static boolean isNegativeNumber(int num) {
		return num < 0;
	}
	
	/**
	 * Checks if a String is numerical.
	 * @param string The string to check with
	 * @return Numeric or not
	 */
	public static boolean isNumeric(String s) {
		return s.matches("[-+]?\\d*\\.?\\d+");
	}

	/** Prints a multidimensional array into a grid. */
	public static void printGrid(int[][] array) {
		int size = array.length;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < array[i].length; j++)
				System.out.printf("%5d ", array[i][j]);
			System.out.println();
		}
	}

}
