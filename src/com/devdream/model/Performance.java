package com.devdream.model;

public class Performance {

	//
	// Attributes
	private int goals;
	private int shots;
	private int passes;
	private int fouls;
	private int offsides;
	private int corners;
	private float possession;
	
	//
	// Constructors
	protected Performance() {}
	
	public Performance(int goals, int shots, int passes, int fouls, int offsides, int corners, float possession) {
		this.goals = goals;
		this.shots = shots;
		this.passes = passes;
		this.fouls = fouls;
		this.offsides = offsides;
		this.corners = corners;
		this.possession = possession;
	}

	//
	// Getters and setters
	public int getGoals() {
		return goals;
	}
	public void setGoals(int goals) {
		this.goals = goals;
	}
	public int getShots() {
		return shots;
	}
	public void setShots(int shots) {
		this.shots = shots;
	}
	public int getPasses() {
		return passes;
	}
	public void setPasses(int passes) {
		this.passes = passes;
	}
	public int getFouls() {
		return fouls;
	}
	public void setFouls(int fouls) {
		this.fouls = fouls;
	}
	public int getOffsides() {
		return offsides;
	}
	public void setOffsides(int offsides) {
		this.offsides = offsides;
	}
	public int getCorners() {
		return corners;
	}
	public void setCorners(int corners) {
		this.corners = corners;
	}
	public float getPossession() {
		return possession;
	}
	public void setPossesion(float possesion) {
		this.possession = possesion;
	}

}
