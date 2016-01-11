package com.devdream.model;

/**
 * The model of the user contains the information of the user
 * and team that they have.
 * 
 * @author Asier Gonzalez
 */
public class User {

	//
	// Attributes
	private String username;
	private String firstName;
	private String surname;
	private Team team;

	//
	// Constructors
	public User(String username, String firstName, String surname, Team team) {
		this.username = username;
		this.firstName = firstName;
		this.surname = surname;
		this.team = team;
	}
	
	//
	// Methods
	/** If the user has a team returns true. */
	public boolean hasTeam() {
		return team != null;
	}
	
	//
	// Getters and setters
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	
}
