package com.devdream.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.devdream.controller.Controller;
import com.devdream.db.vo.GameVO;
import com.devdream.db.vo.SeasonVO;
import com.devdream.db.vo.TeamVO;
import com.devdream.model.Game;
import com.devdream.model.SeasonGame;
import com.devdream.model.Team;
import com.devdream.util.QueryBuilder;

/**
 * The Data Access object for the seasons on the database.
 * 
 * @author Asier Gonzalez
 */
public class SeasonDAO extends DAO {

	@Override
	public void createTable() throws SQLException {
		super.initTableCreation(SeasonVO.class);
	}
	
	/**
	 * Inserts a new season.
	 * @param SeasonGame Value Object
	 * @throws SQLException
	 */
	public int insertSeason(SeasonVO newSeasonVO) throws SQLException {
		int seasonId = 0;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = QueryBuilder.createInsert(getClass(),
					new String[]{"IdLeague", "Date"},
					new String[]{
							Integer.toString(newSeasonVO.getIdLeague()),
							newSeasonVO.getDate()
						});
			preparedStmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStmt.executeUpdate();
			rs = preparedStmt.getGeneratedKeys();
            if(rs.next()) {
                seasonId = rs.getInt(1);
            }
		} finally {
			super.closeConnection(preparedStmt, rs);
		}
		return seasonId;
	}
	
	/**
	 * Gets the season games of a specific league.
	 * @param idLeague The id of the league from get the season games.
	 * @throws SQLException
	 */
	public ArrayList<SeasonGame> getLeagueSeasonsGamesAndDate(int idLeague) throws SQLException {
		ArrayList<SeasonGame> seasonGames = new ArrayList<>();
		GameDAO gameDAO = new GameDAO();
		TeamDAO teamDAO = new TeamDAO();
		String userTeamName = Controller.getLoggedUser().getTeam().getName();
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT g.Id 'IdGame', sd.Id 'IdSeason', sd.Date 'Date' FROM " + QueryBuilder.getTableNameFromDAO(GameDAO.class) + " g, "
					+ "(SELECT s.Id, s.Date FROM "  + QueryBuilder.getTableNameFromDAO(getClass()) +  " s WHERE s.IdLeague = ?) sd "
					+ "ON sd.Id = g.IdSeason;";
			preparedStmt = getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, idLeague);
			rs = preparedStmt.executeQuery();
			while (rs.next()) {
				GameVO gameVO = gameDAO.getGameById(rs.getInt("IdGame"));
				TeamVO homeTeamVO = teamDAO.getTeamById(gameVO.getIdHomeTeam());
				TeamVO awayTeamVO = teamDAO.getTeamById(gameVO.getIdAwayTeam());

				Team homeTeam = new Team(homeTeamVO.getId(), homeTeamVO.getName(), homeTeamVO.getShortName(),
						homeTeamVO.getFoundedYear(), homeTeamVO.getLocation(), homeTeamVO.getLogo());
				Team awayTeam = new Team(awayTeamVO.getId(), awayTeamVO.getName(), awayTeamVO.getShortName(),
						awayTeamVO.getFoundedYear(), awayTeamVO.getLocation(), awayTeamVO.getLogo());
				
				homeTeam.setUserTeam(homeTeam.getName().equals(userTeamName));
				awayTeam.setUserTeam(homeTeam.getName().equals(userTeamName));
				
				seasonGames.add(new SeasonGame(new Game(gameVO.getId(), homeTeam, awayTeam), rs.getString("Date")));
			}
		} finally {
			super.closeConnection(preparedStmt, rs);
		}
		return seasonGames;
	}

}
