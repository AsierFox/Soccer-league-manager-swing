package com.devdream.model;

/**
 * An scorer consists of the scores in goals achieved by
 * a specific player on a game.
 * 
 * @author Asier Gonzalez
 */
public class Scorer {

	//
	// Attributes
	private int score;
	private Player player;
	
	//
	// Constructors
	public Scorer(int score, Player player) {
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
