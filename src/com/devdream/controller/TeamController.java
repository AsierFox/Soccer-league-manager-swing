package com.devdream.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.devdream.db.dao.PlayerDAO;
import com.devdream.db.dao.TeamDAO;
import com.devdream.db.dao.UserDAO;
import com.devdream.db.vo.TeamVO;
import com.devdream.exception.InvalidInputException;
import com.devdream.exception.RecordAlreadyException;
import com.devdream.model.Player;
import com.devdream.model.Team;
import com.devdream.validator.TeamValidator;

/**
 * The team controller manages the team and the players.
 * 
 * @author Asier Gonzalez
 */
public class TeamController extends Controller {

	//
	// Attributes
	private Team userTeam;
	private HashMap<Integer, Player> teamPlayers;
	
	private boolean hasUserTeam;
	private boolean hasTeamPlayers;
	
	//
	// Constructors
	public TeamController() throws SQLException {
		hasUserTeam = super.getLoggedUser().hasTeam();
		if (hasUserTeam) {
			setUserTeamPlayers();
			hasTeamPlayers = userTeam.hasPlayers();
		} else {
			hasTeamPlayers = false;
		}
	}
	
	//
	// Methods
	private void setUserTeamPlayers() throws SQLException {
		UserDAO userDAO = new UserDAO();
		PlayerDAO playerDAO = new PlayerDAO();
		int userTeamId = userDAO.getUserTeamId(Controller.getLoggedUser().getUsername());
		
		// Set user team
		userTeam = super.getLoggedUser().getTeam();

		// Set team players
		teamPlayers = playerDAO.getTeamPlayers(userTeamId);
		userTeam.setPlayers(teamPlayers);
	}
	
	/**
	 * Inserts a team for an user.
	 * @param username
	 * @param name
	 * @param shortName
	 * @param foundedYear
	 * @param achievements
	 * @param location
	 * @param logo
	 * @throws SQLException
	 * @throws RecordAlreadyException
	 * @throws InvalidInputException
	 */
	public void submitUserTeam(String username, String name, String shortName, String foundedYear,
			String location, String logo) throws SQLException, RecordAlreadyException, InvalidInputException
	{
		TeamValidator teamValidator = new TeamValidator(name, shortName, foundedYear, location, logo);
		teamValidator.validate();
		TeamDAO teamDAO = new TeamDAO();
		int teamId = teamDAO.insertTeam(new TeamVO(name, shortName,
				teamValidator.getFoundedYear(), location, logo));
		UserDAO userDAO = new UserDAO();
		userDAO.addTeam(username, teamId);
	}
	
	/**
	 * Submits a new team.
	 * @param name
	 * @param shortName
	 * @param foundedYear
	 * @param achievements
	 * @param location
	 * @param logo
	 * @throws InvalidInputException
	 * @throws SQLException
	 * @throws RecordAlreadyException
	 */
	public void submitTeam(String name, String shortName, String foundedYear, String location, String logo)
			throws InvalidInputException, SQLException, RecordAlreadyException
	{
		TeamValidator teamValidator = new TeamValidator(name, shortName, foundedYear, location, logo);
		teamValidator.validate();
		TeamDAO teamDAO = new TeamDAO();
		teamDAO.insertTeam(new TeamVO(name, shortName, teamValidator.getFoundedYear(), location, logo));
	}
	
	//
	// Getters and setters
	/** Returns the current user team. */
	public Team getUserTeam() {
		return userTeam;
	}
	/** Returns the current user team players. */
	public HashMap<Integer, Player> getTeamPlayers() {
		return teamPlayers;
	}
	public void setTeamPlayers(HashMap<Integer, Player> teamPlayers) {
		this.teamPlayers = teamPlayers;
	}
	
	/** Returns the opponent teams. */
	public HashMap<String, Team> getOpponentTeams() {
		HashMap<String, Team> allTeams = new HashMap<>();
		ArrayList<TeamVO> teams = new ArrayList<>();
		TeamDAO teamDAO = new TeamDAO();
		try {
			teams = teamDAO.getAllTeams();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String userTeamName = userTeam.getName();
		for (TeamVO teamVO : teams) {
			if (!userTeamName.equals(teamVO.getName())) {
				allTeams.put(teamVO.getName(), new Team(teamVO.getId(), teamVO.getName(), teamVO.getShortName(),
						teamVO.getFoundedYear(), teamVO.getLocation(), teamVO.getLogo()));
			}
		}
		return allTeams;
	}
	
	public boolean hasUserTeam() {
		return hasUserTeam;
	}
	public boolean hasTeamPlayers() {
		return hasTeamPlayers;
	}

}
