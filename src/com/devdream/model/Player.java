package com.devdream.model;

import java.util.ArrayList;

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
	private ArrayList<Goal> goals; // TODO this?
	private ArrayList<Assistance> assistances; // TODO this?
	
	//
	// Constructors
	public Player(String firstName, String surname, int age, int dorsal, String position) {
		this.firstName = firstName;
		this.surname = surname;
		this.age = age;
		this.dorsal = dorsal;
		this.position = position;
		goals = new ArrayList<>();
		assistances = new ArrayList<>();
	}
	
	public Player(String firstName, String surname, int age, int dorsal, String position, ArrayList<Goal> goals, ArrayList<Assistance> assistances) {
		this(firstName, surname, age, dorsal, position);
		this.goals = goals;
		this.assistances = assistances;
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
	public ArrayList<Goal> getGoals() {
		return goals;
	}
	public void setGoals(ArrayList<Goal> goals) {
		this.goals = goals;
	}
	public ArrayList<Assistance> getAssistances() {
		return assistances;
	}
	public void setAssistances(ArrayList<Assistance> assistances) {
		this.assistances = assistances;
	}

}
