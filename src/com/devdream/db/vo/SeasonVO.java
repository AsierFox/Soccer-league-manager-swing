package com.devdream.db.vo;

import com.devdream.annotation.DBKey;
import com.devdream.annotation.DBKey.Key;

public class SeasonVO {

	@DBKey(key=Key.PRIMARY)
	private int id;
	@DBKey(key=Key.FOREIGN, REFERENCES="Leagues", ON="Id")
	private int idLeague;
	@DBKey(key=Key.FOREIGN, REFERENCES="Games", ON="Id")
	private int idGame;
	private String date;
	
	public SeasonVO(int idLeague, int idGame) {
		this.idLeague = idLeague;
		this.idGame = idGame;
	}

	public SeasonVO(int idGame, String date) {
		this.idGame = idGame;
		this.date = date;
	}
	
	public SeasonVO(int idLeague, int idGame, String date) {
		this.idLeague = idLeague;
		this.idGame = idGame;
		this.date = date;
	}
	
	public SeasonVO(int id, int idLeague, int idGame, String date) {
		this(idLeague, idGame, date);
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdLeague() {
		return idLeague;
	}
	public void setIdLeague(int idLeague) {
		this.idLeague = idLeague;
	}
	public int getIdGame() {
		return idGame;
	}
	public void setIdGame(int idGame) {
		this.idGame = idGame;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

}
