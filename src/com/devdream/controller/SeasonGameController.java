package com.devdream.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.devdream.db.dao.GoalDAO;
import com.devdream.db.dao.LeagueDAO;
import com.devdream.db.dao.PerformanceDAO;
import com.devdream.exception.InvalidInputException;
import com.devdream.model.Performance;
import com.devdream.model.SeasonGame;
import com.devdream.model.Team;
import com.devdream.validator.PerformanceValidator;

public class SeasonGameController extends Controller {

	private SeasonGame seasonGame;
	private Team homeTeam;
	private Team awayTeam;
	private int idGame;
	
	//
	// Constructors
	public SeasonGameController() {}
	
	public SeasonGameController(SeasonGame seasonGame) {
		this.seasonGame = seasonGame;
		idGame = seasonGame.getGame().getId();
		setTeamPerformances(homeTeam = seasonGame.getGame().getHomeTeam());
		setTeamPerformances(awayTeam = seasonGame.getGame().getAwayTeam());
	}
	
	private void setTeamPerformances(Team team) {
		PerformanceDAO performanceDAO = new PerformanceDAO(team.getId(), idGame);
		GoalDAO goalDAO = new GoalDAO();
		try {
			team.setScore(goalDAO.getTeamGoals(idGame, team.getId()));
			team.setShots(performanceDAO.getShots());
			team.setPasses(performanceDAO.getPasses());
			team.setFouls(performanceDAO.getFouls());
			team.setOffsides(performanceDAO.getOffsides());
			team.setCorners(performanceDAO.getCorners());
			team.setScore(performanceDAO.getTotalScore(idGame, team.getId()));
			if (team.isUserTeam()) team.setScorers(goalDAO.getScorers(idGame, team.getId()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateStats(String gameDate, Performance homeTeamPerformance, Performance awayTeamPerformance)
			throws SQLException, InvalidInputException
	{	// Validate
		LeagueDAO leagueDAO = new LeagueDAO();
		PerformanceValidator performanceValidator = new PerformanceValidator(gameDate, leagueDAO.getCurrentLeague());
		performanceValidator.validate(homeTeamPerformance);
		performanceValidator.validate(awayTeamPerformance);
		
		// TODO UPDATE THE SCORE (GOALS)
		
		// Set performances to the teams
		homeTeam.setPerformances(homeTeamPerformance);
		awayTeam.setPerformances(awayTeamPerformance);
		
		// Update to the database
		PerformanceDAO performanceDAO = new PerformanceDAO();
		performanceDAO.updatePerformances(seasonGame.getGame().getId(), homeTeam.getId(), homeTeamPerformance);
		performanceDAO.updatePerformances(seasonGame.getGame().getId(), awayTeam.getId(), awayTeamPerformance);
	}
	
	public Map<String, String> getPerformanceMap() {
		TreeMap<String, String> performance = new TreeMap<>();
		performance.put("Shots", "Shots");
		performance.put("Passes", "Passes");
		performance.put("Fouls", "Fouls");
		performance.put("Offsides", "Offsides");
		performance.put("Corners", "Corners");
		return performance;
	}
	
	public Map<String, String> getOrderMap() {
		TreeMap<String, String> performance = new TreeMap<>();
		performance.put("ASC", "Ascendent");
		performance.put("DESC", "Descendent");
		return performance;
	}

	public ArrayList<Team> searchTeamsByPerformance(String performance, String order) throws SQLException {
		return new PerformanceDAO().getTeamsByPerformance(performance, order);
	}
	
	//
	// Setters and getters
	public SeasonGame getSeason() {
		return seasonGame;
	}
	public Team getHomeTeam() {
		return homeTeam;
	}
	public Team getAwayTeam() {
		return awayTeam;
	}
	public boolean isUserHomeTeam() {
		return homeTeam.isUserTeam();
	}

}
