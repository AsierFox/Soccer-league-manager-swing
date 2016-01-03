package com.devdream.validator;

import java.sql.SQLException;
import java.util.ArrayList;

import com.devdream.db.dao.LeagueDAO;
import com.devdream.exception.InvalidInputException;
import com.devdream.exception.LeagueUnderwayException;
import com.devdream.model.Team;
import com.devdream.util.DateHelper;
import com.devdream.util.MathHelper;
import com.devdream.util.StringHelper;

/**
 * This class validates the data for submitting a league.
 * 
 * @author Asier Gonzalez
 */
public class LeagueValidator {

	//
	// Attributes
	private String name;
	private String startDate;
	private String endDate;
	private String description;
	private ArrayList<Team> teams;
	
	//
	// Constructors
	public LeagueValidator(String name, String startDate, String endDate, String description, ArrayList<Team> teams) {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
		this.teams = teams;
	}
	
	//
	// Methods
	/**
	 * Checks the league data is correct. 
	 * @throws InvalidInputException 
	 * @throws SQLException 
	 * @throws LeagueUnderwayException
	 */
	public void validate() throws InvalidInputException, SQLException, LeagueUnderwayException {
		if (StringHelper.isStringNull(name) || StringHelper.isStringNull(startDate) ||
				StringHelper.isStringNull(endDate) || StringHelper.isStringNull(description)) {
			throw new InvalidInputException("You must fill all the fields");
		}
		if (MathHelper.isNumeric(name)) {
			throw new InvalidInputException("The league name can't be an integer!");
		}
		if (teams.size() <= 0) {
			throw new InvalidInputException("You must select at leat one opponent team!");
		}
		if (!DateHelper.isPeriodValid(startDate, endDate)) {
			throw new InvalidInputException("The date period is not valid!");
		}
		if (DateHelper.hasDatePeriodPassed(startDate) || DateHelper.hasDatePeriodPassed(endDate)) {
			throw new InvalidInputException("The date of some date has already passed!");
		}
		LeagueDAO leagueDAO = new LeagueDAO();
		if (leagueDAO.isLeagueUnderway()) {
			throw new LeagueUnderwayException(true);
		}
	}
	
	//
	// Getters and setters
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public String getStartDate() {
		return startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public ArrayList<Team> getTeams() {
		return teams;
	}
	public int getNumberSeasons() {
		return teams.size();
	}

}
