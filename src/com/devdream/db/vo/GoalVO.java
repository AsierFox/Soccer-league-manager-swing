package com.devdream.db.vo;

import com.devdream.annotation.DBKey;
import com.devdream.annotation.DBKey.Key;

public class GoalVO {

	@DBKey(key=Key.PRIMARY)
	private int id;
	@DBKey(key=Key.FOREIGN, REFERENCES="Games", ON="Id")
	private int idGame;
	@DBKey(key=Key.FOREIGN, REFERENCES="Teams", ON="Id")
	private int idTeam;
	@DBKey(key=Key.FOREIGN, REFERENCES="Players", ON="Id")
	private int idPlayer;
	private int score;
	
	public GoalVO(int id, int idGame, int idTeam, int idPlayer, int score) {
		this.id = id;
		this.idGame = idGame;
		this.idTeam = idTeam;
		this.idPlayer = idPlayer;
		this.score = score;
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
	public int getIdPlayer() {
		return idPlayer;
	}
	public void setIdPlayer(int idPlayer) {
		this.idPlayer = idPlayer;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

}
