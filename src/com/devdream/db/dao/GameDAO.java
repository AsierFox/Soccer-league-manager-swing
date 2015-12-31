package com.devdream.db.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.devdream.db.vo.GameVO;
import com.devdream.util.QueryBuilder;

public class GameDAO extends DAO {

	@Override
	public void createTable() throws SQLException {
		super.initTableCreation(GameVO.class);
	}

	/** Insert a new game.
	 * @throws SQLException
	 */
	public void insertGame(GameVO newGameVO) throws SQLException {
		PreparedStatement preparedStmt = null;
		try {
			String sql = QueryBuilder.createInsert(getClass(),
					new String[]{"IdHomeTeam", "IdAwayTeam"},
					new String[]{
							Integer.toString(newGameVO.getIdHomeTeam()),
							Integer.toString(newGameVO.getIdAwayTeam())
						});
			preparedStmt = getConnection().prepareStatement(sql);
			preparedStmt.executeUpdate();
		}
		finally {
			super.closeConnection(preparedStmt);
		}
	}
	
}
