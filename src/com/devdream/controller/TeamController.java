package com.devdream.controller;

import java.sql.SQLException;
import java.util.HashMap;

import com.devdream.db.dao.PlayerDAO;
import com.devdream.db.dao.UserDAO;
import com.devdream.model.Player;
import com.devdream.model.Team;

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

	//
	// Getters and setters
	public Team getUserTeam() {
		return userTeam;
	}
	public HashMap<Integer, Player> getTeamPlayers() {
		return teamPlayers;
	}

	public boolean hasUserTeam() {
		return hasUserTeam;
	}
	public boolean hasTeamPlayers() {
		return hasTeamPlayers;
	}
	
}
