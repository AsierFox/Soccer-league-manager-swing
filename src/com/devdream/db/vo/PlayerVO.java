package com.devdream.db.vo;

import com.devdream.db.vo.DBKey.Key;

/**
 * Player Value Object to represent the database table.
 * 
 * @author Asier Gonzalez
 */
public class PlayerVO {

	//
	// Attributes
	@DBKey(key=Key.PRIMARY)
	private int id;
	@DBKey(key=Key.FOREIGN, REFERENCES="Teams", ON="Id")
	private int idTeam;
	private String firstName;
	private String surname;
	private String position;
	private int age;
	
	//
	// Constructors
	public PlayerVO(int idTeam, String firstName, String surname, String position, int age) {
		this.idTeam = idTeam;
		this.firstName = firstName;
		this.surname = surname;
		this.position = position;
		this.age = age;
	}
	
	public PlayerVO(int id, int idTeam, String firstName, String surname, String position, int age) {
		this(idTeam, firstName, surname, position, age);
		this.id = id;
	}

	//
	// Methods
	@Override
	public String toString() {
		return "PlayerVO [id=" + id + ", idTeam=" + idTeam + ", firstName=" + firstName + ", surname=" + surname
				+ ", position=" + position + ", age=" + age + "]";
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}
