package com.devdream.db.vo;

import com.devdream.annotation.DBKey;
import com.devdream.annotation.DBKey.Key;

public class GameVO {

	@DBKey(key=Key.PRIMARY)
	private int id;
	@DBKey(key=Key.FOREIGN, REFERENCES="Teams", ON="Id")
	private int idHomeTeam;
	@DBKey(key=Key.FOREIGN, REFERENCES="Teams", ON="Id")
	private int idAwayTeam;
	@DBKey(key=Key.FOREIGN, REFERENCES="Seasons", ON="Id")
	private int idSeason;
	private String date;
	
	public GameVO(int id, int idHomeTeam, int idAwayTeam, int idSeason, String date) {
		this.id = id;
		this.idHomeTeam = idHomeTeam;
		this.idAwayTeam = idAwayTeam;
		this.idSeason = idSeason;
		this.date = date;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdHomeTeam() {
		return idHomeTeam;
	}

	public void setIdHomeTeam(int idHomeTeam) {
		this.idHomeTeam = idHomeTeam;
	}

	public int getIdAwayTeam() {
		return idAwayTeam;
	}

	public void setIdAwayTeam(int idAwayTeam) {
		this.idAwayTeam = idAwayTeam;
	}

	public int getIdSeason() {
		return idSeason;
	}

	public void setIdSeason(int idSeason) {
		this.idSeason = idSeason;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
