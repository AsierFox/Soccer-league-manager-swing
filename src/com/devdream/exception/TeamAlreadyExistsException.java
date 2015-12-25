package com.devdream.exception;

/**
 * If the team already exists on the database this exception is thrown.
 * 
 * @author Asier Gonzalez
 */
public class TeamAlreadyExistsException extends Exception {
	private static final long serialVersionUID = 1763574381295386330L;
	
	private static final String TEAM_EXISTS_MSG = "The team already exists with that name.";
	
	@Override
	public String getMessage() {
		return TEAM_EXISTS_MSG;
	}
	
}
