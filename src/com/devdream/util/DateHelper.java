package com.devdream.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * This class is the helper for date management.
 * 
 * @author Asier Gonzalez
 * @version 1.0
 * @since 1.0
 */
public class DateHelper {

	/**
	 * Generates a custom date.
	 * @param day The day of the date
	 * @param month The month of the date
	 * @param year The year of the date
	 * @return
	 */
	public static String getCustomDate(int day, int month, int year) {
		return LocalDate.of(year, month, day).format(DateTimeFormatter.ofPattern("d/MM/uuuu"));
	}
	
	/** Returns the current date. */
	public static String getCurrentDate() {
		return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
	}
	
}
