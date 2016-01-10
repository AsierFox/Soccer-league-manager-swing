package com.devdream.db.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.devdream.db.DBConnectionManager;
import com.devdream.db.vo.UserVO;
import com.devdream.exception.RecordAlreadyException;
import com.devdream.util.QueryBuilder;

public abstract class DAO extends DBConnectionManager {

	/** Checks if the table exists. */
	public boolean tableExists() {
		boolean exists = true;
		try {
			String sql = "SELECT 1 FROM " + QueryBuilder.getTableNameFromDAO(getClass()) + " LIMIT 1;";
			super.getConnection().prepareStatement(sql);
		} catch(SQLException err) {
			exists = false;
		}
		finally {
			super.closeConnection();
		}
		return exists;
	}
	
	/**
	 * Runs the process to create the database. 
	 * @throws SQLException
	 */
	protected void initTableCreation(final Class <?> VAO) throws SQLException {
		PreparedStatement preparedStmt = null;
		try {
			String sql = QueryBuilder.createTable(VAO);
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.executeUpdate();
		}
		finally {
			super.closeConnection(preparedStmt);
		}
	}
	
	/**
	 * Creates the database table.
	 * @throws SQLException
	 */
	public abstract void createTable() throws SQLException;
	
	/** Creates all the application tables. */
	public static void createTables() throws SQLException {
		TeamDAO teamDAO = new TeamDAO();
		if (!teamDAO.tableExists()) {
			teamDAO.createTable();
		}
		
		PlayerDAO playerDAO = new PlayerDAO();
		if (!playerDAO.tableExists()) {
			playerDAO.createTable();
		}
		
		UserDAO userDAO = new UserDAO();
		if (!userDAO.tableExists()) {
			userDAO.createTable();
			try {
				userDAO.insertUser(new UserVO(1, "mikel", "123", "Mikel", "Linares"));
			} catch (RecordAlreadyException e) {}
		}
		
		LeagueDAO leagueDAO = new LeagueDAO();
		if (!leagueDAO.tableExists()) {
			leagueDAO.createTable();
		}
		
		SeasonDAO seasonDAO = new SeasonDAO();
		if (!seasonDAO.tableExists()) {
			seasonDAO.createTable();
		}
		
		GameDAO gameDAO = new GameDAO();
		if (!gameDAO.tableExists()) {
			gameDAO.createTable();
		}
		
		PerformanceDAO performanceDAO = new PerformanceDAO();
		if (!performanceDAO.tableExists()) {
			performanceDAO.createTable();
		}
		
		GoalDAO goalDAO = new GoalDAO();
		if (!goalDAO.tableExists()) {
			goalDAO.createTable();
		}
		
		SanctionTypeDAO sanctionTypeDAO = new SanctionTypeDAO();
		if (!sanctionTypeDAO.tableExists()) {
			sanctionTypeDAO.createTable();
		}
		
		SanctionDAO sanctionDAO = new SanctionDAO();
		if (!sanctionDAO.tableExists()) {
			sanctionDAO.createTable();
		}
	}

}
