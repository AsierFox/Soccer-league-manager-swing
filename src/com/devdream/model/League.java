package com.devdream.model;

import java.util.ArrayList;

import com.devdream.util.DateHelper;

public class League {

	//
	// Attributes
	private String startDate;
	private String endDate;
	private String name;
	private String description;
	private int numSeasons;
	private ArrayList<Season> seasons;
	
	//
	// Constructors
	public League(String startDate, String endDate, String name, String description, int numSeasons) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.name = name;
		this.description = description;
		this.numSeasons = numSeasons;
		seasons = new ArrayList<>(numSeasons);
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
	public int getPeriod() {
		return DateHelper.getDatePeriod(getStartDate(), getEndDate());
	}
	public int getLeftDays() {
		return DateHelper.getDatePeriod(DateHelper.getCurrentDate(), getEndDate());
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
	public int getNumberSeasons() {
		return numSeasons;
	}
	public ArrayList<Season> getSeasons() {
		return seasons;
	}
	public void setSeasons(ArrayList<Season> seasons) {
		this.seasons = seasons;
	}

}
