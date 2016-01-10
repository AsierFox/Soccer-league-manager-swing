package com.devdream.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.devdream.db.vo.UserVO;
import com.devdream.exception.RecordAlreadyException;
import com.devdream.util.QueryBuilder;

public class UserDAO extends DAO {

	@Override
	public void createTable() throws SQLException {
		super.initTableCreation(UserVO.class);
	}
	
	/**
	 * Inserts a new user to the database.
	 * @param newTeam The new Team Value Object
	 * @throws SQLException
	 * @throws TeamAlreadyExistsException 
	 */
	public void insertUser(UserVO newUser) throws SQLException, RecordAlreadyException {
		if (existsUserUsername(newUser.getName())) {
			throw new RecordAlreadyException("user", "username", newUser.getUsername());
		}
		PreparedStatement preparedStmt = null;
		try {
			String sql = QueryBuilder.createInsert(getClass(),
					new String[]{"IdTeam", "Username", "Password", "Name", "Surname"},
					new String[]{
							Integer.toString(newUser.getIdTeam()),
							newUser.getUsername(),
							newUser.getPassword(),
							newUser.getName(),
							newUser.getSurname()
						});
			preparedStmt = getConnection().prepareStatement(sql);
			preparedStmt.executeUpdate();
		}
		finally {
			super.closeConnection(preparedStmt);
		}
	}
	
	/**
	 * Checks if an user already exists with the user_name.
	 * @param username
	 * @return True if it already exists
	 * @throws SQLException 
	 */
	public boolean existsUserUsername(String username) throws SQLException {
		boolean exists = false;
		PreparedStatement preparedStmt = null;
		try {
			String sql = "SELECT 1 FROM " + QueryBuilder.getTableNameFromDAO(getClass())
					+ " WHERE Username LIKE ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setString(1, username);
			exists = preparedStmt.executeQuery().next();
		}
		finally {
			super.closeConnection(preparedStmt);
		}
		return exists;
	}
	
	/**
	 * Checks the information of the user to login in to application.
	 * @param username The user_name name of the User
	 * @param password The password of the User
	 * @return True if the account matches correctly
	 * @throws SQLException
	 */
	public boolean checkLogin(String username, String password) throws SQLException {
		boolean success = false;
		PreparedStatement preparedStmt = null;
		try {
			String sql = "SELECT 1 FROM " + QueryBuilder.getTableNameFromDAO(getClass())
					+ " WHERE Username = ? AND Password = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setString(1, username);
			preparedStmt.setString(2, password);
			success = preparedStmt.executeQuery().next();
		}
		finally {
			super.closeConnection(preparedStmt);
		}
		return success;
	}
	
	public UserVO getUserByUsername(String username) throws SQLException {
		UserVO user = null;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = QueryBuilder.createSelect(getClass(), "*") + " WHERE Username = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setString(1, username);
			rs = preparedStmt.executeQuery();
			if (rs.next()) {
				user = new UserVO(rs.getInt("IdTeam"), rs.getString("Username"),
						rs.getString("Name"), rs.getString("Surname"));
			}
		}
		finally {
			super.closeConnection(preparedStmt, rs);
		}
		return user;
	}

	public int getUserTeamId(String username) throws SQLException {
		int userTeamId = -1;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = QueryBuilder.createSelect(getClass(), "IdTeam") + " WHERE Username = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setString(1, username);
			rs = preparedStmt.executeQuery();
			if (rs.next()) {
				userTeamId = rs.getInt(1);
			}
		}
		finally {
			super.closeConnection(preparedStmt, rs);
		}
		return userTeamId;
	}

	public void addTeam(String username, int teamId) throws SQLException {
		PreparedStatement preparedStmt = null;
		try {
			String sql = "UPDATE Users SET IdTeam = ? WHERE Username = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, teamId);
			preparedStmt.setString(2, username);
			preparedStmt.executeUpdate();
		}
		finally {
			super.closeConnection(preparedStmt);
		}
	}

}
