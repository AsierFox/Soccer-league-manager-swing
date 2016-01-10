package com.devdream.db.vo;

import com.devdream.annotation.DBKey;
import com.devdream.annotation.DBKey.Key;

public class LeagueVO {

	@DBKey(key=Key.PRIMARY)
	private int id;
	private String startDate;
	private String endDate;
	private String name;
	private String description;
	private int numSeasons;
	private int period;

	public LeagueVO(String startDate, String endDate, String name, String description, int numSeasons) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.name = name;
		this.description = description;
		this.numSeasons = numSeasons;
	}
	
	public LeagueVO(String startDate, String endDate, String name, String description, int numSeasons, int period) {
		this(startDate, endDate, name, description, numSeasons);
		this.period = period;
	}
	
	public LeagueVO(int id, String startDate, String endDate, String name, String description, int numSeasons, int period) {
		this(startDate, endDate, name, description, numSeasons, period);
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public int getNumSeasons() {
		return numSeasons;
	}
	public void setNumSeasons(int numSeasons) {
		this.numSeasons = numSeasons;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}

}
