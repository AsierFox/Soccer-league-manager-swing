package com.devdream.model;

import java.util.HashMap;

/**
 * The team model for the application.
 * 
 * @author Asier Gonzalez
 */
public class Team {

	//
	// Attributes
	private int id;
	private String name;
	private String shortName;
	private int foundedYear;
	private int achievements;
	private String location;
	private String logo;
	/** Store the team players indexing them with its dorsal. */
	private HashMap<Integer, Player> players;
	private Performance performances;
	
	//
	// Constructors
	public Team(String name, String shortName, int foundedYear, int achievements, int score, String location, String logo) {
		this.name = name;
		this.shortName = shortName;
		this.foundedYear = foundedYear;
		this.achievements = achievements;
		this.location = location;
		this.logo = logo;
		init();
	}
	
	public Team(String name, String shortName, int foundedYear, int achievements, String location, String logo) {
		this.name = name;
		this.shortName = shortName;
		this.foundedYear = foundedYear;
		this.achievements = achievements;
		this.location = location;
		this.logo = logo;
		init();
	}
	
	public Team(final int id, String name, String shortName, int foundedYear, int achievements, String location, String logo) {
		this.id = id;
		this.name = name;
		this.shortName = shortName;
		this.foundedYear = foundedYear;
		this.achievements = achievements;
		this.location = location;
		this.logo = logo;
		init();
	}
	
	public Team(final int id, String name, String shortName, int foundedYear, int achievements, String location, String logo, HashMap<Integer, Player> players) {
		this(id, name, shortName, foundedYear, achievements, location, logo);
		init();
	}
	
	//
	// Methods
	private void init() {
		this.players = new HashMap<>();
		performances = new Performance();
	}
	
	public boolean hasPlayers() {
		return !players.isEmpty();
	}
	
	//
	// Getters and setters
	public int getId() {
		return id;
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
	public Performance getPerformances() {
		return performances;
	}
	public int getGoals() {
		return performances.getGoals();
	}
	public int getShots() {
		return performances.getShots();
	}
	public int getPasses() {
		return performances.getPasses();
	}
	public int getFouls() {
		return performances.getFouls();
	}
	public int getOffsides() {
		return performances.getOffsides();
	}
	public int getCorners() {
		return performances.getCorners();
	}
	public float getPossession() {
		return performances.getPossession();
	}
	public void setPerformances(Performance performances) {
		this.performances = performances;
	}
	public void setGoals(int goals) {
		performances.setGoals(goals);
	}
	public void setShots(int shots) {
		performances.setShots(shots);
	}
	public void setPasses(int passes) {
		performances.setPasses(passes);
	}
	public void setFouls(int fouls) {
		performances.setFouls(fouls);
	}
	public void setOffsides(int offsides) {
		performances.setOffsides(offsides);
	}
	public void setCorners(int corners) {
		performances.setCorners(corners);
	}
	public void setPossesion(float possesion) {
		performances.setPossesion(possesion);
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
	public HashMap<Integer, Player> getPlayers() {
		return players;
	}
	public void setPlayers(HashMap<Integer, Player> players) {
		this.players = players;
	}

}
