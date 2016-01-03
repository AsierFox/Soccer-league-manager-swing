package com.devdream.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.devdream.db.dao.LeagueDAO;
import com.devdream.db.dao.SeasonDAO;
import com.devdream.db.dao.TeamDAO;
import com.devdream.db.vo.LeagueVO;
import com.devdream.db.vo.SeasonVO;
import com.devdream.db.vo.TeamVO;
import com.devdream.exception.InvalidInputException;
import com.devdream.exception.LeagueUnderwayException;
import com.devdream.model.Game;
import com.devdream.model.League;
import com.devdream.model.Season;
import com.devdream.model.Team;

import validator.LeagueValidator;

public class LeagueController extends Controller {

	private LeagueDAO leagueDAO;
	private LeagueVO leagueVO;
	private League currentLeague;

	private boolean isLeagueUnderway;
	
	public LeagueController() throws SQLException {
		leagueDAO = new LeagueDAO();
		isLeagueUnderway = leagueDAO.isLeagueUnderway();
		if (isLeagueUnderway) {
			setCurrentLeague();
		}
	}
	
	/** Set the league that is underway. */
	private void setCurrentLeague() throws SQLException {
		leagueVO = leagueDAO.getCurrentLeague();
		currentLeague = new League(leagueVO.getStartDate(), leagueVO.getEndDate(),
				leagueVO.getName(), leagueVO.getDescription(), leagueVO.getNumSeasons());
	}
	
	/** Creates a new league, submitting it to the database. */
	public void createNewLeague(String name, String startDate, String endDate, String description, ArrayList<Team> teams)
			throws SQLException, LeagueUnderwayException, InvalidInputException {
		LeagueValidator leagueValidator = new LeagueValidator(name, startDate, endDate, description, teams);
		leagueValidator.validate();
		leagueDAO.insertLeague(new LeagueVO(leagueValidator.getStartDate(), leagueValidator.getEndDate(),
				leagueValidator.getName(), leagueValidator.getDescription(), leagueValidator.getNumberSeasons()));
	}
	
	/**
	 * Generates the season pairing.
	 * @param teams The ArrayList of selected team for pairing
	 */
	public ArrayList<Team> generateSeasonsPairing(ArrayList<Team> teams) {
		final int numTeams = teams.size();
		Team[] teamOrder = new Team[numTeams];
		boolean[] generatedIndexes = new boolean[numTeams];
		Random r = new Random();
		for (int i = 0; i < numTeams; ) {
			int gen = r.nextInt(numTeams);
			if (generatedIndexes[gen]) {
				continue;
			}
			generatedIndexes[gen] = true;
			teamOrder[gen] = teams.get(i);
			i++;
		}
		return new ArrayList<Team>(Arrays.asList(teamOrder));
	}
	
	/** Returns the league seasons games. */
	public ArrayList<Season> getLeagueSeasons() {
		ArrayList<Season> seasons = new ArrayList<>();
		SeasonDAO seasonDAO = new SeasonDAO();
		TeamDAO teamDAO = new TeamDAO();
		try {
			ArrayList<SeasonVO> seasonsVO = seasonDAO.getLeagueSeasonsGameAndDate(leagueVO.getId());
			for (SeasonVO seasonVO : seasonsVO) {
				TeamVO homeTeamVO = teamDAO.getGameHomeTeamById(seasonVO.getIdGame());
				TeamVO awayTeamVO = teamDAO.getGameAwayTeamById(seasonVO.getIdGame());
				
				Team homeTeam = new Team(homeTeamVO.getName(), homeTeamVO.getShortName(),
						homeTeamVO.getFoundedYear(), homeTeamVO.getAchievements(), homeTeamVO.getLocation(), homeTeamVO.getLogo());
				Team awayTeam = new Team(awayTeamVO.getName(), awayTeamVO.getShortName(),
						awayTeamVO.getFoundedYear(), awayTeamVO.getAchievements(), awayTeamVO.getLocation(), awayTeamVO.getLogo());
				
				seasons.add(new Season(new Game(homeTeam, awayTeam), seasonVO.getDate()));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return seasons;
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
