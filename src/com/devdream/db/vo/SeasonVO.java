package com.devdream.db.vo;

import com.devdream.annotation.DBKey;
import com.devdream.annotation.DBKey.Key;

public class SeasonVO {

	@DBKey(key=Key.PRIMARY)
	private int id;
	@DBKey(key=Key.FOREIGN, REFERENCES="Leagues", ON="Id")
	private int idLeague;
	
	public SeasonVO(int id, int idLeague) {
		this.id = id;
		this.idLeague = idLeague;
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

}
