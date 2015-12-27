package com.devdream.exception;

public class UserAlreadyExistsException extends Exception {
	private static final long serialVersionUID = -8862716944422911269L;
	
	private static String USER_EXISTS_MSG;
	
	public UserAlreadyExistsException(String username) {
		USER_EXISTS_MSG = "The user with the username '" + username + "' already exists.";
	}
	
	@Override
	public String getMessage() {
		return USER_EXISTS_MSG;
	}
	
}
