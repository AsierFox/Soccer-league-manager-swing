package com.devdream.controller;

import java.sql.SQLException;

import com.devdream.db.dao.PerformanceDAO;
import com.devdream.model.Season;
import com.devdream.model.Team;

public class SeasonGameController extends Controller {
	
	private Season season;
	private Team homeTeam;
	private Team awayTeam;
	private int gameId;
	
	public SeasonGameController(Season season) {
		this.season = season;
		gameId = season.getGame().getId();
		setTeamPerformances(homeTeam = season.getGame().getHomeTeam());
		setTeamPerformances(awayTeam = season.getGame().getAwayTeam());
	}
	
	private void setTeamPerformances(Team team) {
		PerformanceDAO performanceDAO = new PerformanceDAO(team.getId(), gameId);
		try {
			team.setGoals(performanceDAO.getGoals());
			team.setPossesion(performanceDAO.getPossession());
			team.setShots(performanceDAO.getShots());
			team.setPasses(performanceDAO.getPasses());
			team.setFouls(performanceDAO.getFouls());
			team.setOffsides(performanceDAO.getOffsides());
			team.setCorners(performanceDAO.getCorners());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//
	// Setters and getters
	public Team getHomeTeam() {
		return homeTeam;
	}
	public Team getAwayTeam() {
		return awayTeam;
	}

}
