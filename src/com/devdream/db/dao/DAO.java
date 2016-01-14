package com.devdream.db.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.devdream.db.DBConnectionManager;
import com.devdream.db.vo.UserVO;
import com.devdream.exception.RecordAlreadyException;
import com.devdream.util.QueryBuilder;

/**
 * The abstract class for all the DAO objects, this class
 * creates the table for all DAO objects and checks if they
 * are created on the database.
 * 
 * To delete database:
 * PRAGMA writable_schema = 1;
 * delete from sqlite_master where type = 'table';
 * PRAGMA writable_schema = 0;
 * 
 * @author Asier Gonzalez
 */
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
	public static void checkTables() throws SQLException {
		TeamDAO teamDAO = new TeamDAO();
		PlayerDAO playerDAO = new PlayerDAO();
		UserDAO userDAO = new UserDAO();
		LeagueDAO leagueDAO = new LeagueDAO();
		GameDAO gameDAO = new GameDAO();
		SeasonDAO seasonDAO = new SeasonDAO();
		GoalDAO goalDAO = new GoalDAO();
		PerformanceDAO performanceDAO = new PerformanceDAO();
		SanctionTypeDAO sanctionTypeDAO = new SanctionTypeDAO();
		SanctionDAO sanctionDAO = new SanctionDAO();
		
		if (!teamDAO.tableExists()) {
			teamDAO.createTable();
		}
		if (!playerDAO.tableExists()) {
			playerDAO.createTable();
		}
		if (!userDAO.tableExists()) {
			userDAO.createTable();
			try {
				userDAO.insertUser(new UserVO(1, "mikel", "123", "Mikel", "Linares"));
			} catch (RecordAlreadyException e) {}
		}
		if (!leagueDAO.tableExists()) {
			leagueDAO.createTable();
		}
		if (!seasonDAO.tableExists()) {
			seasonDAO.createTable();
		}
		if (!gameDAO.tableExists()) {
			gameDAO.createTable();
		}
		if (!performanceDAO.tableExists()) {
			performanceDAO.createTable();
		}
		if (!goalDAO.tableExists()) {
			goalDAO.createTable();
		}
		if (!sanctionTypeDAO.tableExists()) {
			sanctionTypeDAO.createTable();
			sanctionTypeDAO.insertSanctionType("Yellow card");
			sanctionTypeDAO.insertSanctionType("Red card");
		}
		if (!sanctionDAO.tableExists()) {
			sanctionDAO.createTable();
		}
	}

}
