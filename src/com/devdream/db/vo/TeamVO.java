package com.devdream.db.vo;

import com.devdream.annotation.DBKey;
import com.devdream.annotation.DBKey.Key;

/**
 * Team Value Object to represent the database table.
 * 
 * @author Asier Gonzalez
 */
public class TeamVO {

	//
	// Attributes
	@DBKey(key=Key.PRIMARY)
	private int id;
	private String name;
	private String shortName;
	private int foundedYear;
	private int achievements;
	private String location;
	private String logo;
	
	//
	// Constructors
	public TeamVO(String name, String shortName, int foundedYear, int achievements, String location, String logo) {
		this.name = name;
		this.shortName = shortName;
		this.foundedYear = foundedYear;
		this.achievements = achievements;
		this.location = location;
		this.logo = logo;
	}
	
	public TeamVO(int id, String name, String shortName, int foundedYear, int achievements, String location, String logo) {
		this(name, shortName, foundedYear, achievements, location, logo);
		this.id = id;
	}
	
	//
	// Methods
	@Override
	public String toString() {
		return "TeamVO [id=" + id + ", name=" + name + ", shortName=" + shortName + ", foundedYear=" + foundedYear
				+ ", achievements=" + achievements + ", location=" + location + ", logo=" + logo + "]";
	}
	
	//
	// Getters and setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public int getFoundedYear() {
		return foundedYear;
	}
	public void setFoundedYear(int foundedYear) {
		this.foundedYear = foundedYear;
	}
	public int getAchievements() {
		return achievements;
	}
	public void setAchievements(int achievements) {
		this.achievements = achievements;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
}
