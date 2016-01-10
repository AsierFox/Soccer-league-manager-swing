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
	private String location;
	private String logo;
	
	//
	// Constructors
	public TeamVO(String name, String shortName, int foundedYear, String location, String logo) {
		this.name = name;
		this.shortName = shortName;
		this.foundedYear = foundedYear;
		this.location = location;
		this.logo = logo;
	}
	
	public TeamVO(int id, String name, String shortName, int foundedYear, String location, String logo) {
		this(name, shortName, foundedYear, location, logo);
		this.id = id;
	}
	
	//
	// Methods
	@Override
	public String toString() {
		return "TeamVO [id=" + id + ", name=" + name + ", shortName=" + shortName + ", foundedYear=" + foundedYear
				+ ", location=" + location + ", logo=" + logo + "]";
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
