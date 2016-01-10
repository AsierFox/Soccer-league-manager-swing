package com.devdream.db.vo;

import com.devdream.annotation.DBKey;
import com.devdream.annotation.DBKey.Key;

/**
 * The Value Object of the Season.
 * 
 * @author Asier Gonzalez
 */
public class SeasonVO {

	@DBKey(key=Key.PRIMARY)
	private int id;
	@DBKey(key=Key.FOREIGN, REFERENCES="Leagues", ON="Id")
	private int idLeague;
	private String date;
	
	public SeasonVO(int idLeague) {
		this.idLeague = idLeague;
	}
	
	public SeasonVO(String date) {
		this.date = date;
	}
	
	public SeasonVO(int idLeague, String date) {
		this.idLeague = idLeague;
		this.date = date;
	}
	
	public SeasonVO(int id, int idLeague, String date) {
		this(idLeague, date);
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "SeasonVO [id=" + id + ", idLeague=" + idLeague + ", date=" + date + "]";
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

}
