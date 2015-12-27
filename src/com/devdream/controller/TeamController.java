package com.devdream.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import com.devdream.db.dao.PlayerDAO;
import com.devdream.db.dao.UserDAO;
import com.devdream.model.Player;
import com.devdream.model.Team;

/**
 * The team controller manages the team and the players.
 * 
 * @author SkyFoXx
 *
 */
public class TeamController extends Controller {

	//
	// Attributes
	private Team userTeam;
	private ArrayList<Player> teamPlayers;
	
	//
	// Constructors
	public TeamController() throws SQLException {
		if (Controller.getLoggedUser().hasTeam()) {
			UserDAO userDAO = new UserDAO();
			PlayerDAO playerDAO = new PlayerDAO();
			int userTeamId = userDAO.getUserTeamId(Controller.getLoggedUser().getUsername());
			System.out.println(userTeamId);
			
			teamPlayers = playerDAO.getTeamPlayers(userTeamId);
			userTeam = super.getLoggedUser().getTeam();
			userTeam.setPlayers(teamPlayers);
		}
	}

	//
	// Getters and setters
	public Team getUserTeam() {
		return userTeam;
	}
	public ArrayList<Player> getTeamPlayers() {
		return teamPlayers;
	}
	
}
