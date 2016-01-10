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
 */
public class DateHelper {

	/** Date format on the application. */
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	
	private DateHelper() {}
	
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
	
	/** Returns the LocalDate of a date. */
	public static LocalDate parseDateToLocalDate(String date) {
		return LocalDate.of(getYearFromDate(date), getMonthFromDate(date), getDayFromDate(date));
	}
	
	/** Returns the current date. */
	public static String getCurrentDate() {
		return new SimpleDateFormat(DATE_FORMAT).format(new Date());
	}
	
	/** Returns the current year */
	public static int getCurrentYear() {
		String year = getCurrentDate();
		return Integer.parseInt(year.substring(year.lastIndexOf("/") + 1));
	}
	
	/** Returns the period (in months or days) between two dates. By default it return the period in months. */
	public static int getDatePeriod(String sinceDate, String toDate) {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DATE_FORMAT);
		int days = (int) ChronoUnit.DAYS.between(LocalDate.parse(sinceDate, dateFormat), LocalDate.parse(toDate, dateFormat));
		return days;
	}
	
	/** Checks if the date is inside the passed period. */
	public static boolean isDateWithinPeriod(String date, String startDate, String endDate) {
		LocalDate localDate = parseDateToLocalDate(date);
		if (localDate.isEqual(parseDateToLocalDate(startDate)) ||
				localDate.isEqual(parseDateToLocalDate(endDate))) return true;
		int i = 0;
		if (localDate.isAfter(parseDateToLocalDate(startDate))) i++;
		if (localDate.isBefore(parseDateToLocalDate(endDate))) i++;
		return i == 2;
	}
	
	/** Gets the day from the date. */
	public static int getDayFromDate(String date) {
		return Integer.parseInt(date.substring(0, date.indexOf("/")));
	}

	/** Gets the month from the date. */
	public static int getMonthFromDate(String date) {
		return Integer.parseInt(date.substring(date.indexOf("/") + 1, date.lastIndexOf("/")));
	}

	/** Gets the year from the date. */
	public static int getYearFromDate(String date) {
		return Integer.parseInt(date.substring(date.lastIndexOf("/") + 1, date.length()));
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
