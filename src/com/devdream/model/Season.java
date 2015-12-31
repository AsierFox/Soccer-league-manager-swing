package com.devdream.model;

public class Season {

	//
	// Attributes
	private Game game;
	private String date;

	//
	// Constructors
	public Season(Game game, String date) {
		this.game = game;
		this.date = date;
	}
	
	//
	// Getters and setters
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

}
