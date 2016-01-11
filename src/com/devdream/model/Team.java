package com.devdream.model;

import java.util.ArrayList;
import java.util.HashMap;

import com.devdream.controller.Controller;

/**
 * The team model for the application. Containing the information of it
 * and the performances that it has. Also contains the players, that
 * can play in the team.
 * 
 * @author Asier Gonzalez
 */
public class Team {

	//
	// Attributes
	private String name;
	private int id;
	private String shortName;
	private int foundedYear;
	private String location;
	private String logo;
	private Performance performances;
	/** Store the team players indexing them with its dorsal number. */
	private HashMap<Integer, Player> players;
	
	//
	// Constructors
	public Team(String name, String shortName, int foundedYear, String location, String logo) {
		this.name = name;
		this.shortName = shortName;
		this.foundedYear = foundedYear;
		this.location = location;
		this.logo = logo;
		init();
	}
	
	public Team(final int id, String name, String shortName, int foundedYear, String location, String logo) {
		this.id = id;
		this.name = name;
		this.shortName = shortName;
		this.foundedYear = foundedYear;
		this.location = location;
		this.logo = logo;
		init();
	}
	
	public Team(final int id, String name, String shortName, int foundedYear, String location, String logo, HashMap<Integer, Player> players) {
		this(id, name, shortName, foundedYear, location, logo);
		this.players = players;
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
	public boolean isUserTeam() {
		return name.equals(Controller.getLoggedUser().getTeam().getName());
	}
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
	public Performance getPerformances() {
		return performances;
	}
	public ArrayList<Scorer> getScorers() {
		return performances.getScorers();
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
	public void setPerformances(Performance performances) {
		this.performances = performances;
	}
	public void setScore(int score) {
		performances.setScore(score);
	}
	public int getScore() {
		return performances.getScore();
	}
	public void setScorers(ArrayList<Scorer> scorers) {
		performances.setScorers(scorers);
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
	public void setSanctions(ArrayList<Sanctioned> sanctions) {
		performances.setSactions(sanctions);
	}
	public ArrayList<Sanctioned> getSanctions() {
		return performances.getSactions();
	}

}
