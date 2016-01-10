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
	private int shots;
	private int passes;
	private int fouls;
	private int offsides;
	private int corners;
	
	public PerformanceVO(int id, int idGame, int idTeam, int shots, int passes, int fouls, int offsides,
			int corners) {
		this.id = id;
		this.idGame = idGame;
		this.idTeam = idTeam;
		this.shots = shots;
		this.passes = passes;
		this.fouls = fouls;
		this.offsides = offsides;
		this.corners = corners;
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

}
