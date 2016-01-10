package com.devdream.model;

public class Player {

	//
	// Globals
	public static final int MIN_DORSAL_VALUE = 1;
	public static final int MAX_DORSAL_VALUE = 25;
	
	//
	// Attributes
	private String firstName;
	private String surname;
	private int age;
	private String position;
	private int dorsal;
	
	//
	// Constructors
	public Player() {}
	
	public Player(String firstName, String surname, int age, int dorsal, String position) {
		this.firstName = firstName;
		this.surname = surname;
		this.age = age;
		this.dorsal = dorsal;
		this.position = position;
	}
	
	//
	// Methods
	public static Player getAnonymousPlayer() {
		return new Player("Anonymous", "anonymous", 0, 0, "retired");
	}
	
	public void modifyPlayer(String firstName, String surname, int age, int dorsal, String position) {
		setFirstName(firstName);
		setSurname(surname);
		setAge(age);
		setPosition(position);
		setDorsal(dorsal);
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
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

}
