package com.devdream.db.vo;

import com.devdream.annotation.DBKey;
import com.devdream.annotation.DBKey.Key;

public class SanctionVO {

	@DBKey(key=Key.PRIMARY)
	private int id;
	@DBKey(key=Key.FOREIGN, REFERENCES="Games", ON="Id")
	private int idGame;
	@DBKey(key=Key.FOREIGN, REFERENCES="Players", ON="Id")
	private int idPlayer;
	@DBKey(key=Key.FOREIGN, REFERENCES="Sanctions", ON="Id")
	private int idSanctionType;
	
	public SanctionVO(int id, int idGame, int idPlayer, int idSanctionType) {
		this.id = id;
		this.idGame = idGame;
		this.idPlayer = idPlayer;
		this.idSanctionType = idSanctionType;
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
	public int getIdPlayer() {
		return idPlayer;
	}
	public void setIdPlayer(int idPlayer) {
		this.idPlayer = idPlayer;
	}
	public int getIdSanctionType() {
		return idSanctionType;
	}
	public void setIdSanctionType(int idSanctionType) {
		this.idSanctionType = idSanctionType;
	}

}
