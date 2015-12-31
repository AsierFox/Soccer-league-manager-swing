package com.devdream.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import com.devdream.db.dao.LeagueDAO;
import com.devdream.db.dao.SeasonDAO;
import com.devdream.db.dao.TeamDAO;
import com.devdream.db.vo.LeagueVO;
import com.devdream.db.vo.SeasonVO;
import com.devdream.db.vo.TeamVO;
import com.devdream.model.Game;
import com.devdream.model.League;
import com.devdream.model.Season;
import com.devdream.model.Team;

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
	
	private void setCurrentLeague() throws SQLException {
		leagueVO = leagueDAO.getCurrentLeague();
		currentLeague = new League(leagueVO.getStartDate(), leagueVO.getEndDate(),
				leagueVO.getName(), leagueVO.getDescription(), leagueVO.getNumSeasons());
	}
	
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
