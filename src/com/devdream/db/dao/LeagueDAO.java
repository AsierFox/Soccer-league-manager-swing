package com.devdream.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.devdream.db.vo.LeagueVO;
import com.devdream.util.DateHelper;
import com.devdream.util.QueryBuilder;

public class LeagueDAO extends DAO {

	@Override
	public void createTable() throws SQLException {
		super.initTableCreation(LeagueVO.class);
	}
	
	/** Insert a new league.
	 * WARN: Check if is NOT a league underway before!
	 * @return The underway league value object
	 * @throws SQLException
	 */
	public void insertLeague(LeagueVO newLeagueVO) throws SQLException {
		PreparedStatement preparedStmt = null;
		try {
			String sql = QueryBuilder.createInsert(getClass(),
					new String[]{"StartDate", "EndDate", "Name", "Description", "NumSeasons"},
					new String[]{
							newLeagueVO.getStartDate(),
							newLeagueVO.getEndDate(),
							newLeagueVO.getName(),
							newLeagueVO.getDescription(),
							Integer.toString(newLeagueVO.getNumSeasons())
						});
			preparedStmt = getConnection().prepareStatement(sql);
			preparedStmt.executeUpdate();
		}
		finally {
			super.closeConnection(preparedStmt);
		}
	}
	
	/**
	 * Checks if it is a league currently underway.
	 * @return True if there is one
	 * @throws SQLException
	 */
	public boolean isLeagueUnderway() throws SQLException {
		boolean leagueUnderway = false;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = QueryBuilder.createSelect(getClass(), "EndDate");
			preparedStmt = super.getConnection().prepareStatement(sql);
			rs = preparedStmt.executeQuery();
			while (rs.next() && !leagueUnderway) {
				if (!DateHelper.hasDatePeriodPassed(rs.getString("EndDate"))) {
					leagueUnderway = true;
				}
			}
		}
		finally {
			super.closeConnection(preparedStmt, rs);
		}
		return leagueUnderway;
	}
	
	/**
	 * Gets the underway league.
	 * WARN: Check if is a league underway before!
	 * @return The underway league value object
	 * @throws SQLException
	 */
	public LeagueVO getCurrentLeague() throws SQLException {
		LeagueVO leagueVO = null;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = QueryBuilder.createSelect(getClass(), "*");
			preparedStmt = super.getConnection().prepareStatement(sql);
			rs = preparedStmt.executeQuery();
			while (rs.next()) {
				if (!DateHelper.hasDatePeriodPassed(rs.getString("EndDate"))) {
					leagueVO = new LeagueVO(rs.getInt("Id"), rs.getString("StartDate"), rs.getString("EndDate"),
							rs.getString("Name"), rs.getString("Description"), rs.getInt("NumSeasons"));
					break; // Founded the underway league
				}
			}
		}
		finally {
			super.closeConnection(preparedStmt, rs);
		}
		return leagueVO;
	}

}
