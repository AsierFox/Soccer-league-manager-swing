package com.devdream.exception;

/**
 * If the team already exists on the database this exception is thrown.
 * 
 * @author Asier Gonzalez
 */
public class TeamAlreadyExistsException extends Exception {
	private static final long serialVersionUID = 1763574381295386330L;
	
	private static String TEAM_EXISTS_MSG;
	
	public TeamAlreadyExistsException(String teamName) {
		TEAM_EXISTS_MSG = "The team with the name '" + teamName + "' already exists.";
	}
	
	@Override
	public String getMessage() {
		return TEAM_EXISTS_MSG;
	}
	
}
