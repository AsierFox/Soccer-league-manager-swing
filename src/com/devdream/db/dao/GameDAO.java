package com.devdream.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.devdream.db.vo.GameVO;
import com.devdream.util.QueryBuilder;

public class GameDAO extends DAO {

	@Override
	public void createTable() throws SQLException {
		super.initTableCreation(GameVO.class);
	}

	/**
	 * Insert a game returning the Id of the game inserted.
	 * @param newGameVO
	 * @return The Id of the game inserted
	 * @throws SQLException
	 */
	public int insertSeasonGame(int idSeason, GameVO newGameVO) throws SQLException {
		int gameId = -1;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = QueryBuilder.createInsert(getClass(),
					new String[]{"IdSeason", "IdHomeTeam", "IdAwayTeam"},
					new String[]{
							Integer.toString(idSeason),
							Integer.toString(newGameVO.getIdHomeTeam()),
							Integer.toString(newGameVO.getIdAwayTeam())
						});
			preparedStmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStmt.executeUpdate();
			rs = preparedStmt.getGeneratedKeys();
            if(rs.next()) {
                gameId = rs.getInt(1);
            }
		}
		finally {
			super.closeConnection(preparedStmt, rs);
		}
		return gameId;
	}
	
	/**
	 * Gets the season game with the specific Id.
	 * @param id The Id of the game to search for
	 * @return
	 * @throws SQLException
	 */
	public GameVO getGameById(int id) throws SQLException {
		GameVO game = null;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = QueryBuilder.createSelect(getClass(), "*") + " WHERE Id = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, id);
			rs = preparedStmt.executeQuery();
			if (rs.next()) {
				game = new GameVO(rs.getInt("id"), rs.getInt("IdSeason"), rs.getInt("IdHomeTeam"), rs.getInt("IdAwayTeam"));
			}
		} finally {
			super.closeConnection(preparedStmt, rs);
		}
		return game;
	}

}
