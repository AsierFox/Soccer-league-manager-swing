package com.devdream.exception;

/**
 * Only one league is allowed at the same time, so this
 * exception is thrown when a league is already underway or other
 * league wants to be created.
 * 
 * @author Asier Gonzalez
 */
public class LeagueUnderwayException extends Exception {
	private static final long serialVersionUID = -8121522116896879391L;

	private static String LEAGUE_ALREADY_UNDERWAY_MSG = " league is currently underway!";
	
	public LeagueUnderwayException(boolean isAlreadyLeague) {
		if (isAlreadyLeague) {
			LEAGUE_ALREADY_UNDERWAY_MSG = "A " + LEAGUE_ALREADY_UNDERWAY_MSG;
		} else {
			LEAGUE_ALREADY_UNDERWAY_MSG = "Any " + LEAGUE_ALREADY_UNDERWAY_MSG;
		}
	}
	
	@Override
	public String getMessage() {
		return LEAGUE_ALREADY_UNDERWAY_MSG;
	}

}
