package com.devdream.model;

/**
 * This model represents a game of a soccer match.
 * 
 * @author Asier Gonzalez
 */
public class Game {

	//
	// Attributes
	private final int id;
	private String date;
	private Team homeTeam;
	private Team awayTeam;
	
	//
	// Constructors
	public Game(final int id, Team homeTeam, Team awayTeam) {
		this.id = id;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
	}
	
	public Game(final int id, Team homeTeam, Team awayTeam, String date) {
		this(id, homeTeam, awayTeam);
		this.date = date;
	}
	
	//
	// Methods
	public Team getWinnerTeam() {
		Team winnerTeam = null;
		if (homeTeam.getScore() > awayTeam.getScore()) {
			winnerTeam = homeTeam;
		}
		else if (homeTeam.getScore() < awayTeam.getScore()) {
			winnerTeam = awayTeam;
		}
		return winnerTeam;
	}
	
	//
	// Getters and setters
	public int getId() {
		return id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Team getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(Team homeTeam) {
		this.homeTeam = homeTeam;
	}
	public Team getAwayTeam() {
		return awayTeam;
	}
	public void setAwayTeam(Team awayTeam) {
		this.awayTeam = awayTeam;
	}

}
