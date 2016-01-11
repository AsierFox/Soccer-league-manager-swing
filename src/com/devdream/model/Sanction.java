package com.devdream.model;

/**
 * A sanction is of a specific type, and it also can have
 * an description explaining the saction rule.
 * 
 * @author Asier Gonzalez
 */
public class Sanction {

	//
	// Attributes
	private String type;
	private String description;

	//
	// Constructors
	public Sanction(String type) {
		this.type = type;
	}

	public Sanction(String type, String description) {
		this(type);
		this.description = description;
	}

	//
	// Methods
	@Override
	public String toString() {
		return getType();
	}
	
	//
	// Getters and setters
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
