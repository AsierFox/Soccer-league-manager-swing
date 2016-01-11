package com.devdream.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.devdream.db.vo.GoalVO;
import com.devdream.db.vo.LeagueVO;
import com.devdream.db.vo.PlayerVO;
import com.devdream.model.Player;
import com.devdream.model.Scorer;
import com.devdream.util.QueryBuilder;

public class GoalDAO extends DAO {

	private int idLeague;
	
	/** This constructor gets the id league due to the of times used on the multiple queries. */
	public GoalDAO() throws SQLException {
		LeagueDAO leagueDAO = new LeagueDAO();
		LeagueVO currentLeague = null;
		if (leagueDAO.tableExists()) {
			currentLeague = leagueDAO.getCurrentLeague();
			if (currentLeague != null) idLeague = currentLeague.getId();
		}
	}
	
	@Override
	public void createTable() throws SQLException {
		super.initTableCreation(GoalVO.class);
	}
	
	public int getTeamGameGoals(int idGame, int idTeam) throws SQLException {
		int goals = 0;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = QueryBuilder.createSelect(getClass(), "SUM(Score)") + " WHERE IdGame = ? AND IdTeam = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, idGame);
			preparedStmt.setInt(2, idTeam);
			rs = preparedStmt.executeQuery();
			while (rs.next()) {
				goals = rs.getInt(1);
			}
		} finally {
			super.closeConnection(preparedStmt, rs);
		}
		return goals;
	}
	
	public int getAllTeamGoals(int idTeam) throws SQLException {
		int goals = 0;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT SUM(Score) FROM " + QueryBuilder.getTableNameFromDAO(getClass()) +
					" WHERE IdTeam = ? AND " +
					"IdGame = (SELECT Id FROM " + QueryBuilder.getTableNameFromDAO(SeasonDAO.class) + " WHERE IdLeague = ?);";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, idTeam);
			preparedStmt.setInt(2, idLeague);
			rs = preparedStmt.executeQuery();
			if (rs.next()) {
				goals = rs.getInt(1);
			}
		} finally {
			super.closeConnection(preparedStmt, rs);
		}
		return goals;
	}
	
	public ArrayList<Scorer> getGameScorers(int idGame, int idTeam) throws SQLException {
		ArrayList<Scorer> scorers = new ArrayList<>();
		PlayerDAO playerDAO = new PlayerDAO();
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql =  QueryBuilder.createSelect(getClass(), "Score, IdPlayer") + " WHERE IdGame = ? AND IdTeam = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, idGame);
			preparedStmt.setInt(2, idTeam);
			rs = preparedStmt.executeQuery();
			while (rs.next()) {
				PlayerVO playerVO = playerDAO.getPlayerById(rs.getInt(2));
				if (playerVO != null){
					scorers.add(new Scorer(rs.getInt(1), new Player(playerVO.getFirstName(), playerVO.getSurname(),
							playerVO.getAge(), playerVO.getDorsal(), playerVO.getPosition())));
				} else {
					scorers.add(new Scorer(rs.getInt(1), Player.getAnonymousPlayer()));
				}
			}
		} finally {
			super.closeConnection(preparedStmt, rs);
		}
		return scorers;
	}
	
	public ArrayList<Scorer> getTopScorers() throws SQLException {
		ArrayList<Scorer> topScorers = new ArrayList<>();
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = QueryBuilder.createSelect(getClass(), "IdPlayer, SUM(Score)") +
					" WHERE IdPlayer IS NOT NULL AND" +
					" IdGame IN (SELECT IdGame FROM " + QueryBuilder.getTableNameFromDAO(GameDAO.class) + " WHERE idSeason = " +
					"(SELECT Id FROM " + QueryBuilder.getTableNameFromDAO(SeasonDAO.class) + " WHERE IdLeague = ?))" +
					" GROUP BY IdPlayer ORDER BY Score DESC;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, idLeague);
			rs = preparedStmt.executeQuery();
			while (rs.next()) {
				PlayerVO playerVO = new PlayerDAO().getPlayerById(rs.getInt(1));
				if (playerVO != null){
					topScorers.add(new Scorer(rs.getInt(2), new Player(playerVO.getFirstName(), playerVO.getSurname(),
							playerVO.getAge(), playerVO.getDorsal(), playerVO.getPosition())));
				} else {
					topScorers.add(new Scorer(rs.getInt(2), Player.getAnonymousPlayer()));
				}
			}
		} finally {
			super.closeConnection(preparedStmt, rs);
		}
		return topScorers;
	}

	public boolean updateTeamGameScore(int idGame, int idTeam, int goals) throws SQLException {
		PreparedStatement preparedStmt = null;
		boolean updated;
		String goalsTableName = QueryBuilder.getTableNameFromDAO(getClass());
		try {
			String idGoal = getGameTeamGoalId(idGame, idTeam);
			String sql = "INSERT OR REPLACE INTO " + goalsTableName +
					" (Id, IdGame, IdTeam, Score) VALUES (" + idGoal + ", " +
					"COALESCE((SELECT IdGame FROM " + goalsTableName + " WHERE Id = ?), ?), " +
					"COALESCE((SELECT IdTeam FROM " + goalsTableName + " WHERE Id = ?), ?), " +
					"?);";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, idGame);
			preparedStmt.setInt(2, idGame);
			preparedStmt.setInt(3, idTeam);
			preparedStmt.setInt(4, idTeam);
			preparedStmt.setInt(5, goals);
			updated = preparedStmt.executeUpdate() == 1;
		} finally {
			super.closeConnection(preparedStmt);
		}
		return updated;
	}
	
	public boolean updateScorer(int idGame, int idTeam, int idPlayer, int goals) throws SQLException {
		boolean updated;
		PreparedStatement preparedStmt = null;
		String goalsTableName = QueryBuilder.getTableNameFromDAO(getClass());
		try {
			String idGoal = getGameTeamGoalId(idGame, idTeam);
			String sql = "INSERT OR REPLACE INTO " + goalsTableName +
					" (Id, IdGame, IdTeam, IdPlayer, Score) VALUES (" + idGoal + ", " +
					"COALESCE((SELECT IdGame FROM " + goalsTableName + " WHERE Id = ?), ?), " +
					"COALESCE((SELECT IdTeam FROM " + goalsTableName + " WHERE Id = ?), ?), " +
					"?, ?);";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, idGame);
			preparedStmt.setInt(2, idGame);
			preparedStmt.setInt(3, idTeam);
			preparedStmt.setInt(4, idTeam);
			preparedStmt.setInt(5, idPlayer);
			preparedStmt.setInt(6, goals);
			updated = preparedStmt.executeUpdate() == 1;
		} finally {
			super.closeConnection(preparedStmt);
		}
		return updated;
	}
	
	/**
	 * Gets the goal id if exists, if not it returns 'NULL'.
	 * @throws SQLException
	 */
	public String getGameTeamGoalId(int idGame, int idTeam) throws SQLException {
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		String idPerformance = "NULL";
		try {
			String sql = QueryBuilder.createSelect(getClass(), "Id") + " WHERE IdGame = ? AND IdTeam = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, idGame);
			preparedStmt.setInt(2, idTeam);
			rs = preparedStmt.executeQuery();
			if (rs.next()) idPerformance = Integer.toString(rs.getInt(1));
		} finally {
			super.closeConnection(preparedStmt, rs);
		}
		return idPerformance;
	}

	public boolean deleteScorer(int idGame, int playerId) throws SQLException {
		boolean deleted;
		PreparedStatement preparedStmt = null;
		try {
			String sql = "DELETE FROM " + QueryBuilder.getTableNameFromDAO(getClass()) +
					" WHERE IdGame = ? AND IdPlayer = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, idGame);
			preparedStmt.setInt(2, playerId);
			deleted = preparedStmt.executeUpdate() == 1;
		} finally {
			super.closeConnection(preparedStmt);
		}
		return deleted;
	}

}
