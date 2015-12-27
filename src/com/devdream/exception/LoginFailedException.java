package com.devdream.exception;

public class LoginFailedException extends Exception {

	private static final long serialVersionUID = -4218446717127244266L;
	
	private static final String ERROR_MSG = "The username or password is wrong.";

	@Override
	public String getMessage() {
		return ERROR_MSG;
	}
	
}
