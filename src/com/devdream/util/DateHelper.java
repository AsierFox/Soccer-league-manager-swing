package com.devdream.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
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

	public enum PeriodType { MONTHS, DAYS }
	
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	
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
	public static int getDatePeriod(String sinceDate, String toDate, PeriodType dateType) {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DATE_FORMAT);
		Period period = Period.between(LocalDate.parse(sinceDate, dateFormat), LocalDate.parse(toDate, dateFormat));
		switch (dateType) {
			case DAYS:
				return period.getDays();
			case MONTHS:
			default:
				return period.getMonths();
		}
	}
	
	public static boolean hasDatePeriodPassed(String endDate) {
		String currentDate = DateHelper.getCurrentDate();
		return DateHelper.getDatePeriod(currentDate, endDate, PeriodType.DAYS) < 0;
	}

}
