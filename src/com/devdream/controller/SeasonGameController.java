package com.devdream.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.devdream.db.dao.GoalDAO;
import com.devdream.db.dao.PerformanceDAO;
import com.devdream.db.dao.PlayerDAO;
import com.devdream.db.dao.SanctionDAO;
import com.devdream.db.dao.SanctionTypeDAO;
import com.devdream.db.dao.SeasonDAO;
import com.devdream.exception.InvalidInputException;
import com.devdream.exception.NotTableItemSelectedException;
import com.devdream.model.Performance;
import com.devdream.model.Player;
import com.devdream.model.Sanction;
import com.devdream.model.Sanctioned;
import com.devdream.model.Scorer;
import com.devdream.model.SeasonGame;
import com.devdream.model.Team;
import com.devdream.util.MathHelper;
import com.devdream.validator.PerformanceValidator;

/**
 * This controller is responsible of loading the season
 * related objects and actions, like the season game, the
 * teams of that game, the performances that occurs in the game,
 * the scorers and sanctions that happened...
 * 
 * @author Asier Gonzalez
 */
public class SeasonGameController extends Controller {

	//
	// Attributes
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
	
	//
	// Methods
	/** Loads the team performances, sanctions and scorers of a game. */
	private void setTeamPerformances(Team team) {
		PerformanceDAO performanceDAO = new PerformanceDAO(team.getId(), idGame);
		try {
			GoalDAO goalDAO = new GoalDAO();
			team.setScore(goalDAO.getTeamGameGoals(idGame, team.getId()));
			team.setShots(performanceDAO.getShots());
			team.setPasses(performanceDAO.getPasses());
			team.setFouls(performanceDAO.getFouls());
			team.setOffsides(performanceDAO.getOffsides());
			team.setCorners(performanceDAO.getCorners());
			team.setScore(performanceDAO.getTotalScore(idGame, team.getId()));
			if (team.isUserTeam()) {
				SanctionDAO sanctionDAO = new SanctionDAO();
				team.setScorers(goalDAO.getGameScorers(idGame, team.getId()));
				team.setSanctions(sanctionDAO.getGameSanctions(idGame));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Saves updating the game statistics, such as the scorers goals, the performances of each team.
	 * @throws SQLException
	 * @throws InvalidInputException
	 */
	public void updateStats(String gameDate, Performance homeTeamPerformance, Performance awayTeamPerformance,
			ArrayList<Scorer> scorers)
			throws SQLException, InvalidInputException
	{	// Validate
		PerformanceValidator performanceValidator = new PerformanceValidator(gameDate);
		performanceValidator.validate(homeTeamPerformance);
		performanceValidator.validate(awayTeamPerformance);
		
		// Update the season date
		SeasonDAO seasonDAO = new SeasonDAO();
		seasonDAO.updateDate(idGame, gameDate);
		
		// Set performances to the teams
		homeTeam.setPerformances(homeTeamPerformance);
		awayTeam.setPerformances(awayTeamPerformance);
		
		// Update to the database
		PerformanceDAO performanceDAO = new PerformanceDAO();
		performanceDAO.updatePerformances(seasonGame.getGame().getId(), homeTeam.getId(), homeTeamPerformance);
		performanceDAO.updatePerformances(seasonGame.getGame().getId(), awayTeam.getId(), awayTeamPerformance);
		
		// Update the score
		GoalDAO goalDAO = new GoalDAO();
		if (isUserHomeTeam()) {
			homeTeam.setScorers(scorers);
			updateEachScorer(goalDAO, homeTeam);
			goalDAO.updateTeamGameScore(idGame, awayTeam.getId(), awayTeam.getScore());
		} else {
			awayTeam.setScorers(scorers);
			updateEachScorer(goalDAO, awayTeam);
			goalDAO.updateTeamGameScore(idGame, homeTeam.getId(), homeTeam.getScore());
		}
	}
	
	/** Updates the score of each team player. */
	private void updateEachScorer(GoalDAO goalDAO, Team team) throws SQLException {
		PlayerDAO playerDAO = new PlayerDAO();
		for (Scorer s : team.getScorers()) {
			goalDAO.updateScorer(idGame, team.getId(),
					playerDAO.getIdByDorsal(s.getPlayer().getDorsal()), s.getScore());
		}
	}
	
	/**
	 * Updates the score of a player.
	 * @param selPlayer
	 * @param goals
	 * @throws InvalidInputException 
	 * @throws SQLException 
	 */
	public void updateScorer(Player selPlayer, int goals) throws InvalidInputException, SQLException, NullPointerException {
		if (MathHelper.isNegativeNumber(goals)) {
			throw new InvalidInputException("The goals can't be negative and greater than 0!");
		}
		int playerId = new PlayerDAO().getIdByDorsal(selPlayer.getDorsal());
		GoalDAO goalDAO = new GoalDAO();
		goalDAO.updateScorer(idGame, isUserHomeTeam() ? homeTeam.getId() : awayTeam.getId(), playerId, goals);
	}

	public HashMap<String, Sanction> getSactionTypes() {
		HashMap<String, Sanction> sanctionTypes = new HashMap<>();
		SanctionTypeDAO sanctionTypeDAO = new SanctionTypeDAO();
		try {
			for (Sanction s : sanctionTypeDAO.getAllSanctionTypes()) {
				sanctionTypes.put(s.getType(), s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sanctionTypes;
	}

	/**
	 * Creates a sanction for an specific player in the season game.
	 * @throws SQLException
	 */
	public void createSanction(Player selPlayer, Sanction sanction) throws SQLException {
		PlayerDAO playerDAO = new PlayerDAO();
		SanctionDAO sanctionDAO = new SanctionDAO();
		SanctionTypeDAO sanctionTypeDAO = new SanctionTypeDAO();
		int playerId = playerDAO.getIdByDorsal(selPlayer.getDorsal());
		int sanctionTypeId = sanctionTypeDAO.getIdBySanctionType(sanction.getType());
		sanctionDAO.insertSanction(idGame, playerId, sanctionTypeId);
	}
	
	/** Searches the teams by a specific performance returning the teams with its statistics. */
	public ArrayList<Team> searchTeamsByPerformance(String performance, String order) throws SQLException {
		return new PerformanceDAO().getTeamsByPerformance(performance, order);
	}

	/** Searches the teams by total goals, ordering them. */
	public ArrayList<Team> searchTeamsByTotalGoals(String selOrder) throws SQLException {
		return new PerformanceDAO().getTeamByGoals(selOrder);
	}
	
	/**
	 * Deletes an scorer player from the game.
	 * @throws NotTableItemSelectedException
	 * @throws SQLException
	 */
	public void deleteScorer(Scorer scorer) throws NotTableItemSelectedException, SQLException {
		if (scorer == null) {
			throw new NotTableItemSelectedException("You must select an scorer");
		}
		PlayerDAO playerDAO = new PlayerDAO();
		GoalDAO goalDAO = new GoalDAO();
		int playerId = playerDAO.getIdByDorsal(scorer.getPlayer().getDorsal());
		goalDAO.deleteScorer(idGame, playerId);
	}
	
	/**
	 * Deletes the selected sanctioned sanction.
	 * @param selSanctioned The sanctioned player with the sanction
	 * @throws SQLException
	 */
	public void deleteSanctionedSanction(Sanctioned selSanctioned) throws SQLException {
		PlayerDAO playerDAO = new PlayerDAO();
		SanctionTypeDAO sanctionTypeDAO = new SanctionTypeDAO();
		SanctionDAO sanctionDAO = new SanctionDAO();
		int playerId = playerDAO.getIdByDorsal(selSanctioned.getPlayer().getDorsal());
		int sanctionId = sanctionTypeDAO.getIdBySanctionType(selSanctioned.getSanction().getType());
		sanctionDAO.deleteSanctioned(idGame, playerId, sanctionId);
	}
	
	/** Returns the possible orders that the performances can be ordered. */
	public Map<String, String> getOrderMap() {
		HashMap<String, String> performance = new HashMap<>();
		performance.put("DESC", "Descendent");
		performance.put("ASC", "Ascendent");
		return performance;
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
