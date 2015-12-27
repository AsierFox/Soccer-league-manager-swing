package com.devdream.controller;

import com.devdream.model.User;

public abstract class Controller {

	//
	// Attributes
	private static User loggedUser;
	
	//
	// Constructor
	public Controller() {}
	
	//
	// Getters and setters
	public static User getLoggedUser() {
		return loggedUser;
	}
	protected User setLoggedUser(User loggedUser) {
		return Controller.loggedUser = loggedUser;
	}
	
}
