package com.devdream.model;

public class Game {

	private final int id;
	private Team homeTeam;
	private Team awayTeam;
	
	public Game(final int id, Team homeTeam, Team awayTeam) {
		this.id = id;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
	}
	
	public int getId() {
		return id;
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
