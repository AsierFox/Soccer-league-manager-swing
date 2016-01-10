package com.devdream.model;

public class Scorer {

	//
	// Attributes
	private int score;
	private Player player;
	
	//
	// Constructors
	public Scorer(int score, Player player) {
		super();
		this.score = score;
		this.player = player;
	}
	
	//
	// Methods
	@Override
	public String toString() {
		return player.getFirstName() + " - " + score;
	}
	
	//
	// Getters and setters
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}

}
