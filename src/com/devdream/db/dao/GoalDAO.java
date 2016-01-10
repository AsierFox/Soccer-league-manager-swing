package com.devdream.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.devdream.db.vo.GoalVO;
import com.devdream.db.vo.PlayerVO;
import com.devdream.model.Player;
import com.devdream.model.Scorer;
import com.devdream.util.QueryBuilder;

public class GoalDAO extends DAO {

	@Override
	public void createTable() throws SQLException {
		super.initTableCreation(GoalVO.class);
	}

	public int getTeamGoals(int idGame, int idTeam) throws SQLException {
		int goals = 0;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			LeagueDAO leagueDAO = new LeagueDAO();
//			leagueDAO.getCurrentLeague().getId() TODO Get the scores of the LEAGUE
			String sql = "SELECT Score FROM " + QueryBuilder.getTableNameFromDAO(getClass())
					+ " WHERE IdGame = ? AND IdTeam = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, idGame);
			preparedStmt.setInt(2, idTeam);
			rs = preparedStmt.executeQuery();
			while (rs.next()) {
				goals += rs.getInt(1);
			}
		}
		finally {
			super.closeConnection(preparedStmt, rs);
		}
		return goals;
	}
	
	public int getAllTeamGoals(int idTeam) throws SQLException {
		int goals = 0;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT Score FROM " + QueryBuilder.getTableNameFromDAO(getClass()) + " WHERE IdTeam = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, idTeam);
			rs = preparedStmt.executeQuery();
			while (rs.next()) {
				goals += rs.getInt(1);
			}
		}
		finally {
			super.closeConnection(preparedStmt, rs);
		}
		return goals;
	}

	public ArrayList<Scorer> getScorers(int idGame, int idTeam) throws SQLException {
		ArrayList<Scorer> scorers = new ArrayList<>();
		PlayerDAO playerDAO = new PlayerDAO();
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT Score, IdPlayer FROM " + QueryBuilder.getTableNameFromDAO(getClass())
					+ " WHERE IdGame = ? AND IdTeam = ?;";
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
		PlayerDAO playerDAO = new PlayerDAO();
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT IdPlayer, SUM(Score) TotalScore FROM " + QueryBuilder.getTableNameFromDAO(getClass())
					+ " GROUP BY IdPlayer ORDER BY Score DESC;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			rs = preparedStmt.executeQuery();
			while (rs.next()) {
				PlayerVO playerVO = playerDAO.getPlayerById(rs.getInt(1));
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

}
