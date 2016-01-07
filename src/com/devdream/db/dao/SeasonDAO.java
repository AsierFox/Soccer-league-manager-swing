package com.devdream.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.devdream.db.vo.SeasonVO;
import com.devdream.util.QueryBuilder;

public class SeasonDAO extends DAO {

	@Override
	public void createTable() throws SQLException {
		super.initTableCreation(SeasonVO.class);
	}
	
	/**
	 * Inserts a new season.
	 * @param Season Value Object
	 * @throws SQLException
	 */
	public void insertSeason(SeasonVO newSeasonVO) throws SQLException {
		PreparedStatement preparedStmt = null;
		try {
			String sql = QueryBuilder.createInsert(getClass(),
					new String[]{"IdLeague", "IdGame", "Date"},
					new String[]{
							Integer.toString(newSeasonVO.getIdLeague()),
							Integer.toString(newSeasonVO.getIdGame()),
							newSeasonVO.getDate()
						});
			preparedStmt = getConnection().prepareStatement(sql);
			preparedStmt.executeUpdate();
		}
		finally {
			super.closeConnection(preparedStmt);
		}
	}
	
	public ArrayList<SeasonVO> getLeagueSeasonsGameAndDate(int idLeague) throws SQLException {
		ArrayList<SeasonVO> seasonsVO = new ArrayList<>();
		//
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT s.IdGame, s.Date FROM Leagues l INNER JOIN Seasons s ON s.IdLeague = ?;";
			preparedStmt = getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, idLeague);
			rs = preparedStmt.executeQuery();
			while (rs.next()) {
				seasonsVO.add(new SeasonVO(rs.getInt("IdGame"), rs.getString("Date")));
			}
		}
		finally {
			super.closeConnection(preparedStmt, rs);
		}
		return seasonsVO;
	}

}
