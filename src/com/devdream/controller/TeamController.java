package com.devdream.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.devdream.db.dao.PlayerDAO;
import com.devdream.db.dao.TeamDAO;
import com.devdream.db.dao.UserDAO;
import com.devdream.db.vo.PlayerVO;
import com.devdream.db.vo.TeamVO;
import com.devdream.exception.InvalidInputException;
import com.devdream.exception.ItemAlreadyException;
import com.devdream.model.Player;
import com.devdream.model.Team;
import com.devdream.validator.PlayerValidator;
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
	 * Submits a new team.
	 * @param name
	 * @param shortName
	 * @param foundedYear
	 * @param achievements
	 * @param location
	 * @param logo
	 * @throws InvalidInputException
	 * @throws SQLException
	 * @throws ItemAlreadyException
	 */
	public void submitTeam(String name, String shortName, String foundedYear, int achievements, String location, String logo)
			throws InvalidInputException, SQLException, ItemAlreadyException
	{
		TeamValidator teamValidator = new TeamValidator(name, shortName, foundedYear, achievements, location, logo);
		teamValidator.validate();
		TeamDAO teamDAO = new TeamDAO();
		teamDAO.insertTeam(new TeamVO(name, shortName, teamValidator.getFoundedYear(), achievements, location, logo));
	}
	
	/**
	 * Checks and submits a new player.
	 * @param firstName
	 * @param surname
	 * @param age
	 * @param position
	 * @param dorsal
	 * @return The submitted player
	 * @throws ItemAlreadyException 
	 * @throws SQLException 
	 * @throws InvalidInputException 
	 */
	public void submitPlayer(int teamId, String firstName, String surname, int age, String position, int dorsal)
			throws InvalidInputException, SQLException, ItemAlreadyException
	{
		PlayerValidator playerValidator = new PlayerValidator(firstName, surname, age, position, dorsal);
		playerValidator.validate();
		PlayerDAO playerDAO = new PlayerDAO();
		playerDAO.insertPlayer(new PlayerVO(teamId, firstName, surname, age, dorsal, position));
	}
	
	/**
	 * Deletes a player from a team
	 * @param playerDorsal The dorsal of the player
	 * @throws SQLException
	 */
	public void deleteTeamPlayer(int playerDorsal) throws SQLException {
		PlayerDAO playerDAO = new PlayerDAO();
		playerDAO.deletePlayer(playerDorsal);
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
			// TODO TIRAR ARRIBA
			e.printStackTrace();
		}
		String userTeamName = userTeam.getName();
		for (TeamVO teamVO : teams) {
			if (!userTeamName.equals(teamVO.getName())) {
				allTeams.put(teamVO.getName(), new Team(teamVO.getId(), teamVO.getName(), teamVO.getShortName(),
						teamVO.getFoundedYear(), teamVO.getAchievements(), teamVO.getLocation(), teamVO.getLogo()));
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
