package com.devdream.db.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.devdream.db.DBConnectionManager;
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
	
}
