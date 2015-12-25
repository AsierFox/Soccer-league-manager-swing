package com.devdream.db.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.devdream.db.vo.PlayerVO;
import com.devdream.util.QueryBuilder;

public class PlayerDAO extends DAO {

	public void createTable() throws SQLException {
		PreparedStatement preparedStmt = null;
		try {
			String sql = QueryBuilder.createTable(PlayerVO.class);
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.executeUpdate();
		}
		finally {
			super.closeConnection(preparedStmt);
		}
	}
	
	/**
	 * Inserts a new team to the databases.
	 * @param newTeam The new Team Value Object
	 * @throws SQLException
	 */
	public void insertPlayer(PlayerVO newPlayer) throws SQLException {
		PreparedStatement preparedStmt = null;
		try {
			String sql = QueryBuilder.createInsert(getClass(),
					new String[]{"IdTeam", "FirstName", "Surname", "Position", "Age"},
					new String[]{
							Integer.toString(newPlayer.getIdTeam()),
							newPlayer.getFirstName(),
							newPlayer.getSurname(),
							newPlayer.getPosition(),
							Integer.toString(newPlayer.getAge())
						});
			preparedStmt = getConnection().prepareStatement(sql);
			preparedStmt.executeUpdate();
		}
		finally {
			super.closeConnection(preparedStmt);
		}
	}
	
}
