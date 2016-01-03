package com.devdream.exception;

public class NotTeamSelectedException extends Exception {
	private static final long serialVersionUID = -1404798192984013060L;
	
	private static final String NOT_TEAM_SELECTED = "There are no teams selected!";
	
	@Override
	public String getMessage() {
		return NOT_TEAM_SELECTED;
	}

}
