package com.devdream.model;

import java.util.ArrayList;

/**
 * This class represents the model for the statistics of a season game.
 * 
 * @author Asier Gonzalez
 */
public class Performance {

	//
	// Attributes
	private int shots;
	private int passes;
	private int fouls;
	private int score;
	private int offsides;
	private int corners;
	private ArrayList<Scorer> scorers;
	
	//
	// Constructors
	protected Performance() {}
	
	public Performance(int score, int shots, int passes, int fouls, int offsides, int corners) {
		this.score = score;
		this.shots = shots;
		this.passes = passes;
		this.fouls = fouls;
		this.offsides = offsides;
		this.corners = corners;
		scorers = new ArrayList<>();
	}
	
	public Performance(int score, int shots, int passes, int fouls, int offsides, int corners, ArrayList<Scorer> scorers) {
		this(score, shots, passes, fouls, offsides, corners);
		this.scorers = scorers;
	}
	
	//
	// Methods
	@Override
	public String toString() {
		return "Performance [shots=" + shots + ", passes=" + passes + ", fouls=" + fouls + ", score=" + score
				+ ", offsides=" + offsides + ", corners=" + corners + ", scorers=" + scorers + "]";
	}
	
	//
	// Getters and setters
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
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
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
	public ArrayList<Scorer> getScorers() {
		return scorers;
	}
	public void setScorers(ArrayList<Scorer> scorers) {
		this.scorers = scorers;
	}

}
