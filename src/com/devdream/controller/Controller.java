package com.devdream.controller;

import java.sql.SQLException;

import com.devdream.db.dao.TeamDAO;
import com.devdream.db.vo.TeamVO;
import com.devdream.model.Team;
import com.devdream.model.User;

/**
 * The controller abstract class. The controller manages
 * the classes connecting the model actions
 * that are called by a view. That is, it is used to
 * communicate between model classes and view. Also retains
 * the logged user of the application.
 * 
 * @author Asier Gonzalez
 */
public abstract class Controller {

	//
	// Attributes
	private static User loggedUser;
	
	//
	// Methods
	public Team getUserTeam(String username) throws SQLException {
		TeamDAO teamDAO = new TeamDAO();
		TeamVO teamVO = teamDAO.getUserTeam(username);
		if (teamVO == null) return null;
		return new Team(teamVO.getId(), teamVO.getName(), teamVO.getShortName(),
				teamVO.getFoundedYear(), teamVO.getLocation(), teamVO.getLogo());
	}
	
	//
	// Getters and setters
	public static User getLoggedUser() {
		return loggedUser;
	}
	protected User setLoggedUser(User loggedUser) {
		return Controller.loggedUser = loggedUser;
	}
	
}
