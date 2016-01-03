package com.devdream.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * This class is the helper for date management.
 * 
 * @author Asier Gonzalez
 * @version 1.0
 * @since 1.0
 */
public class DateHelper {

	/** Date format on the application. */
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	
	/**
	 * Generates a custom date.
	 * @param day The day of the date
	 * @param month The month of the date
	 * @param year The year of the date
	 * @return
	 */
	public static String getCustomDate(int day, int month, int year) {
		return LocalDate.of(year, month, day).format(DateTimeFormatter.ofPattern(DATE_FORMAT));
	}
	
	/** Returns the current date. */
	public static String getCurrentDate() {
		return new SimpleDateFormat(DATE_FORMAT).format(new Date());
	}
	
	/** Returns the period (in months or days) between two dates. By default it return the period in months. */
	public static int getDatePeriod(String sinceDate, String toDate) {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DATE_FORMAT);
		int days = (int) ChronoUnit.DAYS.between(LocalDate.parse(sinceDate, dateFormat), LocalDate.parse(toDate, dateFormat));
		return days;
	}
	
	/** Returns true if the dates are in a valid period. */
	public static boolean isPeriodValid(String startDate, String endDate) {
		return getDatePeriod(startDate, endDate) > 0;
	}
	
	/** Returns true if the period has passed. */
	public static boolean hasDatePeriodPassed(String endDate) {
		return DateHelper.getDatePeriod(DateHelper.getCurrentDate(), endDate) < 0;
	}

}
