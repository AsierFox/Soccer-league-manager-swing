package com.devdream.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.devdream.db.vo.LeagueVO;
import com.devdream.db.vo.PerformanceVO;
import com.devdream.model.Performance;
import com.devdream.model.Team;
import com.devdream.util.QueryBuilder;

public class PerformanceDAO extends DAO {

	//
	// Attributes
	private int idTeam;
	private int idGame;
	private int idLeague;
	
	//
	// Constructors
	public PerformanceDAO() {
		idTeam = -1;
		idGame = -1;
		try {
			LeagueVO currentLeague = new LeagueDAO().getCurrentLeague();
			if (currentLeague != null) idLeague = currentLeague.getId();
		} catch (SQLException e) {}
	}
	
	public PerformanceDAO(int idTeam, int idGame) {
		this();
		this.idTeam = idTeam;
		this.idGame = idGame;
	}
	
	//
	// Methods
	@Override
	public void createTable() throws SQLException {
		super.initTableCreation(PerformanceVO.class);
	}
	
	/**
	 * Gets the teams ordered by a specific performance statistic.
	 * @param performance The performance
	 * @param order The order
	 * @return The ordered teams
	 * @throws SQLException
	 */
	public ArrayList<Team> getTeamsByPerformance(final String performance, final String order) throws SQLException {
		ArrayList<Team> teams = new ArrayList<>();
		GoalDAO goalDAO = new GoalDAO();
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		String performaceTableName = QueryBuilder.getTableNameFromDAO(getClass());
		String teamsTableName = QueryBuilder.getTableNameFromDAO(TeamDAO.class);
		String seasonsTableName = QueryBuilder.getTableNameFromDAO(SeasonDAO.class);
		try {
			String sql = "SELECT t.*," +
					"(SELECT IdGame FROM " + performaceTableName + " WHERE t.Id = IdTeam) 'IdGame', " +
					"(SELECT SUM(Shots) FROM " + performaceTableName + " WHERE t.Id = IdTeam) 'Shots', " +
					"(SELECT SUM(Passes)  FROM " + performaceTableName + " WHERE t.Id = IdTeam) 'Passes', " +
					"(SELECT SUM(Fouls)  FROM " + performaceTableName + " WHERE t.Id = IdTeam) 'Fouls', " +
					"(SELECT SUM(Offsides)  FROM " + performaceTableName + " WHERE t.Id = IdTeam) 'Offsides', " +
					"(SELECT SUM(Corners)  FROM " + performaceTableName + " WHERE t.Id = IdTeam) 'Corners' " +
					"FROM " + teamsTableName + " t WHERE Shots IS NOT NULL AND Passes IS NOT NULL AND Fouls IS NOT NULL AND Offsides IS NOT NULL AND Corners IS NOT NULL " +
					"AND IdGame IN (SELECT Id FROM " + seasonsTableName + " WHERE IdLeague = ?) " +
					"ORDER BY " + performance + " " + order + ";";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, idLeague);
			rs = preparedStmt.executeQuery();
			while (rs.next()) {
				Team team = new Team(rs.getString("Name"), rs.getString("ShortName"), rs.getInt("FoundedYear"),
						rs.getString("Location"), rs.getString("Logo"));
				
				int score = goalDAO.getAllTeamGoals(rs.getInt("Id"));
				team.setPerformances(new Performance(score, rs.getInt("Shots"), rs.getInt("Passes"), rs.getInt("Fouls"),
						rs.getInt("Offsides"), rs.getInt("Corners")));
				
				teams.add(team);
			}
		} finally {
			super.closeConnection(preparedStmt, rs);
		}
		return teams;
	}
	
	public ArrayList<Team> getTeamByGoals(String selOrder) throws SQLException {
		ArrayList<Team> teams = new ArrayList<>();
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		String performancesTableName = QueryBuilder.getTableNameFromDAO(getClass());
		String teamsTableName = QueryBuilder.getTableNameFromDAO(TeamDAO.class);
		String goalsTableName = QueryBuilder.getTableNameFromDAO(GoalDAO.class);
		try {
			String sql = "SELECT t.*, " +
					"(SELECT Shots FROM " + performancesTableName + " WHERE IdTeam = g.IdTeam) 'Shots', " +
					"(SELECT Passes FROM " + performancesTableName + " WHERE IdTeam = g.IdTeam) 'Passes', " +
					"(SELECT Fouls FROM " + performancesTableName + " WHERE IdTeam = g.IdTeam) 'Fouls', " +
					"(SELECT Offsides FROM " + performancesTableName + " WHERE IdTeam = g.IdTeam) 'Offsides', " +
					"(SELECT Corners FROM " + performancesTableName + " WHERE IdTeam = g.IdTeam) 'Corners', " +
					" SUM(g.Score) 'TotalGoals' FROM " + goalsTableName + " g " +
					"JOIN (SELECT * FROM " + teamsTableName + ") t ON t.Id = g.IdTeam " +
					"GROUP BY g.IdTeam ORDER BY TotalGoals " + selOrder + ";";
			preparedStmt = super.getConnection().prepareStatement(sql);
			rs = preparedStmt.executeQuery();
			while (rs.next()) {
				Team team = new Team(rs.getString("Name"), rs.getString("ShortName"),
						rs.getInt("FoundedYear"), rs.getString("Location"), rs.getString("Logo"));
				
				team.setPerformances(new Performance(rs.getInt("TotalGoals"), rs.getInt("Shots"),  rs.getInt("Passes"),
						 rs.getInt("Fouls"), rs.getInt("Offsides"),  rs.getInt("Corners")));
				
				teams.add(team);
			}
		} finally {
			super.closeConnection(preparedStmt);
		}
		return teams;
	}
	
	/**
	 * Updates a team season game performance, if it does not exist it creates it.
	 * @param idGame The season game id
	 * @param idTeam The id of the team
	 * @param performance The statistics of the performance
	 * @return If the performance has been modified.
	 * @throws SQLException
	 */
	public boolean updatePerformances(int idGame, int idTeam, Performance performance) throws SQLException {
		PreparedStatement preparedStmt = null;
		boolean updated;
		String performancesTableName = QueryBuilder.getTableNameFromDAO(getClass());
		try {
			String idPerformance = getTeamPerformanceIdByTeam(idGame, idTeam);
			String sql = "INSERT OR REPLACE INTO " + QueryBuilder.getTableNameFromDAO(getClass()) +
					" (Id, IdGame, IdTeam, Shots, Passes, Fouls, Offsides, Corners) VALUES ( " +
					idPerformance + ", " +
					"COALESCE((SELECT IdGame FROM " + performancesTableName + " WHERE IdGame = ? AND IdTeam = ?), ?), " +
					"COALESCE((SELECT IdTeam FROM " + performancesTableName + " WHERE IdGame = ? AND IdTeam = ?), ?), " +
					"?, ?, ?, ?, ?);";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, idGame);
			preparedStmt.setInt(2, idTeam);
			preparedStmt.setInt(3, idGame);
			preparedStmt.setInt(4, idGame);
			preparedStmt.setInt(5, idTeam);
			preparedStmt.setInt(6, idTeam);
			preparedStmt.setInt(7, performance.getShots());
			preparedStmt.setInt(8, performance.getPasses());
			preparedStmt.setInt(9, performance.getFouls());
			preparedStmt.setInt(10, performance.getOffsides());
			preparedStmt.setInt(11, performance.getCorners());
			updated = preparedStmt.executeUpdate() == 1;
		} finally {
			super.closeConnection(preparedStmt);
		}
		return updated;
	}
	
	/**
	 * Gets the performance id if exists, if not it returns 'NULL'.
	 * @throws SQLException
	 */
	public String getTeamPerformanceIdByTeam(int idGame, int idTeam) throws SQLException {
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		String idPerformance = "NULL";
		try {
			String sqlId = QueryBuilder.createSelect(getClass(), "Id") + " WHERE IdGame = ? AND IdTeam = ?;";
			preparedStmt = super.getConnection().prepareStatement(sqlId);
			preparedStmt.setInt(1, idGame);
			preparedStmt.setInt(2, idTeam);
			rs = preparedStmt.executeQuery();
			if (rs.next()) idPerformance = Integer.toString(rs.getInt(1));
		} finally {
			super.closeConnection(preparedStmt, rs);
		}
		return idPerformance;
	}
	
	public int getShots() throws SQLException {
		int shots = 0;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = QueryBuilder.createSelect(getClass(), "Shots") + " WHERE IdTeam = ? AND IdGame = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, idTeam);
			preparedStmt.setInt(2, idGame);
			rs = preparedStmt.executeQuery();
			if (rs.next()) {
				shots = rs.getInt(1);
			}
		} finally {
			super.closeConnection(preparedStmt, rs);
		}
		return shots;
	}
	
	public int getPasses() throws SQLException {
		int passes = 0;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = QueryBuilder.createSelect(getClass(), "Passes") + " WHERE IdTeam = ? AND IdGame = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, idTeam);
			preparedStmt.setInt(2, idGame);
			rs = preparedStmt.executeQuery();
			if (rs.next()) {
				passes = rs.getInt(1);
			}
		} finally {
			super.closeConnection(preparedStmt, rs);
		}
		return passes;
	}
	
	public int getFouls() throws SQLException {
		int fouls = 0;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = QueryBuilder.createSelect(getClass(), "Fouls") + " WHERE IdTeam = ? AND IdGame = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, idTeam);
			preparedStmt.setInt(2, idGame);
			rs = preparedStmt.executeQuery();
			if (rs.next()) {
				fouls = rs.getInt(1);
			}
		} finally {
			super.closeConnection(preparedStmt, rs);
		}
		return fouls;
	}
	
	public int getOffsides() throws SQLException {
		int offsides = 0;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = QueryBuilder.createSelect(getClass(), "Offsides") + " WHERE IdTeam = ? AND IdGame = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, idTeam);
			preparedStmt.setInt(2, idGame);
			rs = preparedStmt.executeQuery();
			if (rs.next()) {
				offsides = rs.getInt(1);
			}
		}
		finally {
			super.closeConnection(preparedStmt, rs);
		}
		return offsides;
	}
	
	public int getCorners() throws SQLException {
		int corners = 0;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = QueryBuilder.createSelect(getClass(), "Corners") + " WHERE IdTeam = ? AND IdGame = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, idTeam);
			preparedStmt.setInt(2, idGame);
			rs = preparedStmt.executeQuery();
			if (rs.next()) {
				corners = rs.getInt(1);
			}
		}
		finally {
			super.closeConnection(preparedStmt, rs);
		}
		return corners;
	}
	
	/**
	 * Gets the total score of a team on a specific game.
	 * @param idGame
	 * @param idTeam
	 * @return
	 * @throws SQLException
	 */
	public int getTotalScore(int idGame, int idTeam) throws SQLException {
		int score = 0;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = QueryBuilder.createSelect(GoalDAO.class, "SUM(g.Score)") +
					" g WHERE IdGame = ? AND IdTeam = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, idGame);
			preparedStmt.setInt(2, idTeam);
			rs = preparedStmt.executeQuery();
			if (rs.next()) {
				score = rs.getInt(1);
			}
		}
		finally {
			super.closeConnection(preparedStmt, rs);
		}
		return score;
	}

}
