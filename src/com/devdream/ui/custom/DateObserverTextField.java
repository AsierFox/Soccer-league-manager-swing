package com.devdream.ui.custom;

import java.util.Calendar;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JTextField;

import com.devdream.util.DateHelper;

/**
 * TextField that displays the date chose by the DatePicker.
 * 
 * @author Asier Gonzalez
 */
public class DateObserverTextField extends JTextField implements Observer {
	private static final long serialVersionUID = -1598127114364609451L;

	@Override
	public void update(Observable o, Object arg) {
		Calendar calendar = (Calendar) arg;
		setLocale(Locale.getDefault());
		int day = calendar.get(5);
		int month = calendar.get(2) + 1;
		int year = calendar.get(1);
		setText(DateHelper.getCustomDate(day, month, year));
    }
	
}
