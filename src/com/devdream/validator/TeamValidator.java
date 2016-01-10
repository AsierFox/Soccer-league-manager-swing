package com.devdream.validator;

import java.sql.SQLException;

import com.devdream.db.dao.TeamDAO;
import com.devdream.exception.InvalidInputException;
import com.devdream.exception.RecordAlreadyException;
import com.devdream.ui.View;
import com.devdream.util.DateHelper;
import com.devdream.util.MathHelper;
import com.devdream.util.StringHelper;

/**
 * Class for the validation of the team data.
 * 
 * @author Asier Gonzalez
 */
public class TeamValidator {

	//
	// Attributes
	private String name;
	private String shortName;
	private String foundedYear;
	private String location;
	private String logo;
	
	//
	// Constructors
	public TeamValidator(String name, String shortName, String foundedYear, String location, String logo) {
		this.name = name;
		this.shortName = shortName;
		this.foundedYear = foundedYear;
		this.location = location;
		this.logo = logo;
	}
	
	//
	// Methods
	public void validate() throws InvalidInputException, RecordAlreadyException, SQLException {
		if (StringHelper.isStringNull(name) || StringHelper.isStringNull(shortName) ||
				StringHelper.isStringNull(foundedYear) || StringHelper.isStringNull(location)) {
			throw new InvalidInputException("You must fill all the fields unless the logo!");
		}
		if (!MathHelper.isNumeric(foundedYear)) {
			throw new InvalidInputException("The founded year must be a numeric value!");
		}
		if (MathHelper.isNumeric(name)) {
			throw new InvalidInputException("The name of the team can't be a numeric value!");
		}
		if (getFoundedYear() < 1700 || getFoundedYear() > DateHelper.getCurrentYear()) {
			throw new InvalidInputException("Founded year must be valid!");
		}
		TeamDAO teamDAO = new TeamDAO();
		if (teamDAO.existsTeamName(name)) {
			throw new RecordAlreadyException("team", "name", name);
		}
		if (logo.isEmpty()) {
			logo = View.ImagePath.DEFAULT_TEAM_LOGO;
		}
	}
	
	//
	// Getters
	public String getName() {
		return name;
	}
	public String getShortName() {
		return shortName;
	}
	public int getFoundedYear() {
		return Integer.parseInt(foundedYear);
	}
	public String getLocation() {
		return location;
	}
	public String getLogo() {
		return logo;
	}

}
