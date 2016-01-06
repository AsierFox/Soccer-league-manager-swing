package com.devdream.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.devdream.db.dao.GameDAO;
import com.devdream.db.dao.LeagueDAO;
import com.devdream.db.dao.SeasonDAO;
import com.devdream.db.dao.TeamDAO;
import com.devdream.db.vo.GameVO;
import com.devdream.db.vo.LeagueVO;
import com.devdream.db.vo.SeasonVO;
import com.devdream.db.vo.TeamVO;
import com.devdream.exception.InvalidInputException;
import com.devdream.exception.LeagueUnderwayException;
import com.devdream.model.Game;
import com.devdream.model.League;
import com.devdream.model.Season;
import com.devdream.model.Team;
import com.devdream.validator.LeagueValidator;

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
	
	/**
	 * Creates a new league, submitting it to the database and returns the id of it.
	 * @param name
	 * @param startDate
	 * @param endDate
	 * @param description
	 * @param teams
	 * @return The Id of the created league
	 * @throws SQLException
	 * @throws LeagueUnderwayException
	 * @throws InvalidInputException
	 */
	public int submitNewLeague(String name, String startDate, String endDate, String description, ArrayList<Team> teams)
			throws SQLException, LeagueUnderwayException, InvalidInputException {
		LeagueValidator leagueValidator = new LeagueValidator(name, startDate, endDate, description, teams);
		leagueValidator.validate();
		return leagueDAO.insertLeague(new LeagueVO(leagueValidator.getStartDate(), leagueValidator.getEndDate(),
				leagueValidator.getName(), leagueValidator.getDescription(), leagueValidator.getNumberSeasons()));
	}
	
	/**
	 * Generates the season pairing.
	 * @param teamIds The ArrayList of selected team for pairing
	 */
	private int[][] generateSeasonsGamePairing(int[] teamIds, int userTeamId) {
		final int numTeams = teamIds.length;
		int[][] gamesOrder = new int[numTeams][2];
		boolean[] generatedIndexes = new boolean[numTeams];
		Random rand = new Random();
		for (int i = 0; i < numTeams; ) {
			int opponentNum = rand.nextInt(numTeams);
			boolean homeTeam = (rand.nextInt(2) == 1) ? true : false;
			if (generatedIndexes[opponentNum]) {
				continue;
			}
			generatedIndexes[opponentNum] = true;
			gamesOrder[opponentNum][homeTeam ? 0 : 1] = teamIds[i++];
			gamesOrder[opponentNum][homeTeam ? 1 : 0] = userTeamId;
		}
		return gamesOrder;
	}
	
	/**
	 * Submit the generated seasons games
	 * @param leagueId The league Id
	 * @param teams The opponents teams
	 * @throws SQLException
	 */
	public void submitSeasonsGames(int leagueId, ArrayList<Team> teams) throws SQLException {
		TeamDAO teamDAO = new TeamDAO();
		SeasonDAO seasonDAO = new SeasonDAO();
		GameDAO gameDAO = new GameDAO();
		int numSeasons = teams.size();
		int i = 0;
		int[] teamIds = new int[numSeasons];
		for (Team team : teams) {
			teamIds[i++] = teamDAO.getTeamIdByName(team.getName());
		}
		
		int userTeamId = teamDAO.getUserTeam(Controller.getLoggedUser().getUsername()).getId();
		int[][] games = generateSeasonsGamePairing(teamIds, userTeamId);
		for (i = 0; i < numSeasons; ++i) {
			int gameId = gameDAO.insertGame(new GameVO(games[i][0], games[i][1]));
			seasonDAO.insertSeason(new SeasonVO(leagueId, gameId));
		}
	}
	
	/** Returns the league seasons games. */
	public HashMap<Integer, Season> getLeagueSeasons() {
		HashMap<Integer, Season> seasons = new HashMap<>();
		SeasonDAO seasonDAO = new SeasonDAO();
		TeamDAO teamDAO = new TeamDAO();
		try {
			ArrayList<SeasonVO> seasonsVO = seasonDAO.getLeagueSeasonsGameAndDate(leagueVO.getId());
			for (SeasonVO seasonVO : seasonsVO) {
				int idGame = seasonVO.getIdGame();
				TeamVO homeTeamVO = teamDAO.getGameHomeTeamById(idGame);
				TeamVO awayTeamVO = teamDAO.getGameAwayTeamById(idGame);
				
				Team homeTeam = new Team(homeTeamVO.getId(), homeTeamVO.getName(), homeTeamVO.getShortName(),
						homeTeamVO.getFoundedYear(), homeTeamVO.getAchievements(), homeTeamVO.getLocation(), homeTeamVO.getLogo());
				Team awayTeam = new Team(awayTeamVO.getId(), awayTeamVO.getName(), awayTeamVO.getShortName(),
						awayTeamVO.getFoundedYear(), awayTeamVO.getAchievements(), awayTeamVO.getLocation(), awayTeamVO.getLogo());
				
				Game game = new Game(idGame, homeTeam, awayTeam);
				seasons.put(game.getId(), new Season(game, seasonVO.getDate()));
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
