package com.devdream.model;

/**
 * A sanctioned consists of a player with an
 * specific sanction.
 * 
 * @author Asier Gonzalez
 */
public class Sanctioned {

	//
	// Attributes
	private Player player;
	private Sanction sanction;
	
	//
	// Constructors
	public Sanctioned(Player player, Sanction sanction) {
		this.player = player;
		this.sanction = sanction;
	}
	
	//
	// Getters and setters
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public Sanction getSanction() {
		return sanction;
	}
	public void setSanction(Sanction sanction) {
		this.sanction = sanction;
	}

}
