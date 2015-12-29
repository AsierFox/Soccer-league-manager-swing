package com.devdream.controller;

import java.sql.SQLException;

import com.devdream.db.dao.LeagueDAO;
import com.devdream.db.vo.LeagueVO;
import com.devdream.model.League;

public class LeagueController extends Controller {

	private boolean isLeagueUnderway;
	private LeagueDAO leagueDAO;
	private League currentLeague;
	
	public LeagueController() throws SQLException {
		leagueDAO = new LeagueDAO();
		isLeagueUnderway = leagueDAO.isLeagueUnderway();
		if (isLeagueUnderway) {
			setCurrentLeague();
		}
	}
	
	private void setCurrentLeague() {
		try {
			LeagueVO leagueVO = leagueDAO.getCurrentLeague();
			currentLeague = new League(leagueVO.getStartDate(), leagueVO.getEndDate(),
					leagueVO.getName(), leagueVO.getDescription());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//
	// Getters and setters
	public League getCurrentLeague() {
		return currentLeague;
	}
	
	public boolean isLeagueUnderway() {
		return isLeagueUnderway;
	}

}
