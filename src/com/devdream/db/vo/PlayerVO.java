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
	private int age;
	private int dorsal;
	private String position;
	
	//
	// Constructors
	public PlayerVO(String firstName, String surname, int age, int dorsal, String position) {
		this.firstName = firstName;
		this.surname = surname;
		this.age = age;
		this.dorsal = dorsal;
		this.position = position;
	}
	
	public PlayerVO(int idTeam, String firstName, String surname, int age, int dorsal, String position) {
		this(firstName, surname, age, dorsal, position);
		this.idTeam = idTeam;
	}
	
	public PlayerVO(int id, int idTeam, String firstName, String surname, int age, int dorsal, String position) {
		this(idTeam, firstName, surname, age, dorsal, position);
		this.id = id;
	}

	//
	// Methods
	@Override
	public String toString() {
		return "PlayerVO [id=" + id + ", idTeam=" + idTeam + ", firstName=" + firstName + ", surname=" + surname
				+ ", age=" + age + ", dorsal=" + dorsal + ", position=" + position + "]";
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
