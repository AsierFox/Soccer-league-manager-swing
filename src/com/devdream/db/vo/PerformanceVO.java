package com.devdream.db.vo;

import com.devdream.annotation.DBKey;
import com.devdream.annotation.DBKey.Key;

public class PerformanceVO {

	@DBKey(key=Key.PRIMARY)
	private int id;
	@DBKey(key=Key.FOREIGN, REFERENCES="Games", ON="Id")
	private int idGame;
	@DBKey(key=Key.FOREIGN, REFERENCES="Teams", ON="Id")
	private int idTeam;
	private int passes;
	private int shots;
	private int fouls;
	private int offsides;
	private int corners;
	private float possession;
	
	public PerformanceVO(int id, int idGame, int idTeam, int passes, int shots, int fouls, int offsides, int corners,
			float possession) {
		this.id = id;
		this.idGame = idGame;
		this.idTeam = idTeam;
		this.passes = passes;
		this.shots = shots;
		this.fouls = fouls;
		this.offsides = offsides;
		this.corners = corners;
		this.possession = possession;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdGame() {
		return idGame;
	}
	public void setIdGame(int idGame) {
		this.idGame = idGame;
	}
	public int getIdTeam() {
		return idTeam;
	}
	public void setIdTeam(int idTeam) {
		this.idTeam = idTeam;
	}
	public int getPasses() {
		return passes;
	}
	public void setPasses(int passes) {
		this.passes = passes;
	}
	public int getShots() {
		return shots;
	}
	public void setShots(int shots) {
		this.shots = shots;
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
	public void setPossession(float possession) {
		this.possession = possession;
	}

}
