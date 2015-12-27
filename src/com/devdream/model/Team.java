package com.devdream.model;

import java.util.ArrayList;

public class Team {

	//
	// Attributes
	private String name;
	private String shortName;
	private int foundedYear;
	private int achievements;
	private String location;
	private String logo;
	private ArrayList<Player> players;
	
	//
	// Constructors
	public Team(String name, String shortName, int foundedYear, int achievements, String location, String logo) {
		this.name = name;
		this.shortName = shortName;
		this.foundedYear = foundedYear;
		this.achievements = achievements;
		this.location = location;
		this.logo = logo;
		players = new ArrayList<>();
	}
	
	public Team(String name, String shortName, int foundedYear, int achievements, String location, String logo, ArrayList<Player> players) {
		this(name, shortName, foundedYear, achievements, location, logo);
		this.players = new ArrayList<>();
	}
	
	//
	// Methods
	public boolean hasPlayers() {
		return !players.isEmpty();
	}
	
	//
	// Getters and setters
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
	public ArrayList<Player> getPlayers() {
		return players;
	}
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
}
