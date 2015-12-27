package com.devdream.db.vo;

import com.devdream.annotation.DBKey;
import com.devdream.annotation.DBKey.Key;

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
	private int dorsal;
	private String position;
	private int age;
	
	//
	// Constructors
	public PlayerVO(String firstName, String surname, int dorsal, String position, int age) {
		this.firstName = firstName;
		this.surname = surname;
		this.dorsal = dorsal;
		this.position = position;
		this.age = age;
	}
	
	public PlayerVO(int idTeam, String firstName, String surname, int dorsal, String position, int age) {
		this(firstName, surname, dorsal, position, age);
		this.idTeam = idTeam;
	}
	
	public PlayerVO(int id, int idTeam, String firstName, String surname, int dorsal, String position, int age) {
		this(idTeam, firstName, surname, dorsal, position, age);
		this.id = id;
	}

	//
	// Methods
	@Override
	public String toString() {
		return "PlayerVO [id=" + id + ", idTeam=" + idTeam + ", firstName=" + firstName + ", surname=" + surname
				+ ", dorsal=" + dorsal + ", position=" + position + ", age=" + age + "]";
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
	public int getDorsal() {
		return dorsal;
	}
	public void setDorsal(int dorsal) {
		this.dorsal = dorsal;
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
