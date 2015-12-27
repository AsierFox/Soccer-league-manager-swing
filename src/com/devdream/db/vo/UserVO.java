package com.devdream.db.vo;

import com.devdream.annotation.DBKey;
import com.devdream.annotation.DBKey.Key;

public class UserVO {

	//
	// Attributes
	@DBKey(key=Key.PRIMARY)
	private int id;
	@DBKey(key=Key.FOREIGN, REFERENCES="Teams", ON="Id")
	private int idTeam;
	private String username;
	private String password;
	private String name;
	private String surname;
	
	//
	// Constructors
	public UserVO(int idTeam, String username, String name, String surname) {
		this.idTeam = idTeam;
		this.username = username;
		this.name = name;
		this.surname = surname;
	}
	
	public UserVO(int idTeam, String username, String password, String name, String surname) {
		this(idTeam, username, name, surname);
		this.password = password;
	}
	
	public UserVO(int id, int idTeam, String username, String password, String name, String surname) {
		this(idTeam, username, password, name, surname);
		this.id = id;
	}
	
	//
	// Methods
	@Override
	public String toString() {
		return "UserVO [id=" + id + ", idTeam=" + idTeam + ", username=" + username + ", password=" + password
				+ ", name=" + name + ", surname=" + surname + "]";
	}
	
	//
	// Getters and setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdTeam() {
		return idTeam;
	}
	public void setIdTeam(int idTeam) {
		this.idTeam = idTeam;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
}
