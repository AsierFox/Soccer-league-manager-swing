package com.devdream.db.vo;

import com.devdream.annotation.DBKey;
import com.devdream.annotation.DBKey.Key;

public class GameVO {

	@DBKey(key=Key.PRIMARY)
	private int id;
	@DBKey(key=Key.FOREIGN, REFERENCES="Seasons", ON="Id")
	private int idSeason;
	@DBKey(key=Key.FOREIGN, REFERENCES="Teams", ON="Id")
	private int idHomeTeam;
	@DBKey(key=Key.FOREIGN, REFERENCES="Teams", ON="Id")
	private int idAwayTeam;

	public GameVO(int idHomeTeam, int idAwayTeam) {
		this.idHomeTeam = idHomeTeam;
		this.idAwayTeam = idAwayTeam;
	}
	
	public GameVO(int idSeason, int idHomeTeam, int idAwayTeam) {
		this(idHomeTeam, idAwayTeam);
		this.idSeason = idSeason;
	}
	
	public GameVO(int id, int idSeason, int idHomeTeam, int idAwayTeam) {
		this(idSeason, idHomeTeam, idAwayTeam);
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdSeason() {
		return idSeason;
	}
	public void setIdSeason(int idSeason) {
		this.idSeason = idSeason;
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

}
