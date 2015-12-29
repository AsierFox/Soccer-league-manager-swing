package com.devdream.model;

import java.util.ArrayList;

import com.devdream.util.DateHelper;
import com.devdream.util.DateHelper.PeriodType;

// TODO Display number of teams taking from the size() of the ArrayList
public class League {

	//
	// Attributes
	private String startDate;
	private String endDate;
	private String name;
	private String description;
	private ArrayList<Season> seasons;
	
	public League(String startDate, String endDate, String name, String description) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.name = name;
		this.description = description;
		seasons = new ArrayList<>();
	}
	//
	// Getters and setters
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getPeriod(PeriodType periodType) {
		return DateHelper.getDatePeriod(getStartDate(), getEndDate(), periodType);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ArrayList<Season> getSeasons() {
		return seasons;
	}
	public void setSeasons(ArrayList<Season> seasons) {
		this.seasons = seasons;
	}

}
