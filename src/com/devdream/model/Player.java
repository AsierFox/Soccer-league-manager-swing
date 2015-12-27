package com.devdream.model;

public class Player {

	//
	// Attributes
	private String firstName;
	private String surname;
	private String position;
	private int age;
	
	//
	// Constructors
	public Player(String firstName, String surname, String position, int age) {
		this.firstName = firstName;
		this.surname = surname;
		this.position = position;
		this.age = age;
	}
	
	//
	// Getters and setters
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
