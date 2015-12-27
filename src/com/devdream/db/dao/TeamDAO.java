package com.devdream.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.devdream.db.vo.TeamVO;
import com.devdream.exception.TeamAlreadyExistsException;
import com.devdream.util.QueryBuilder;

public class TeamDAO extends DAO {

	public void createTable() throws SQLException {
		PreparedStatement preparedStmt = null;
		try {
			String sql = QueryBuilder.createTable(TeamVO.class);
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.executeUpdate();
		}
		finally {
			super.closeConnection(preparedStmt);
		}
	}
	
	/**
	 * 	Get the team with players.
	 * @return Returns -1 if there is not any team have not any players
	 * @throws SQLException
	 */
	public int getTeamIdWithPlayers() throws SQLException {
		int teamId = -1;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT t.Id FROM Teams t JOIN Players p ON t.Id = p.IdTeam;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			rs = preparedStmt.executeQuery();
			if (rs.next()) {
				teamId = rs.getInt(1);
			}
		}
		finally {
			super.closeConnection(preparedStmt, rs);
		}
		return teamId;
	}
	
	/**
	 * Checks if a team exists with the team name.
	 * @param teamName
	 * @return True if it already exists
	 * @throws SQLException 
	 */
	public boolean existsTeamName(String teamName) throws SQLException {
		boolean exists = false;
		PreparedStatement preparedStmt = null;
		try {
			String sql = "SELECT 1 FROM " + QueryBuilder.getTableNameFromDAO(getClass())
					+ " WHERE Name LIKE ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setString(1, teamName);
			exists = preparedStmt.executeQuery().next();
		}
		finally {
			super.closeConnection(preparedStmt);
		}
		return exists;
	}
	
	/**
	 * Gets the team with the specific Id.
	 * @param id The Id of the team to search for
	 * @return
	 * @throws SQLException
	 */
	public TeamVO getTeamById(int id) throws SQLException {
		TeamVO team = null;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = QueryBuilder.createSelect(getClass(), "*") + " WHERE Id = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, id);
			rs = preparedStmt.executeQuery();
			if (rs.next()) {
				team = getFullTeamVO(rs);
			}
		}
		finally {
			super.closeConnection(preparedStmt, rs);
		}
		return team;
	}

	/**
	 * Gets all the teams from the database.
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<TeamVO> getAllTeams() throws SQLException {
		ArrayList<TeamVO> teams = new ArrayList<>();
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = QueryBuilder.createSelect(getClass(), "*");
			preparedStmt = getConnection().prepareStatement(sql);
			rs = preparedStmt.executeQuery();
			while (rs.next()) {
				teams.add(getFullTeamVO(rs));
			}
		}
		finally {
			super.closeConnection(preparedStmt, rs);
		}
		return teams;
	}
	
	/**
	 * Inserts a new team to the database.
	 * @param newTeam The new Team Value Object
	 * @throws SQLException
	 * @throws TeamAlreadyExistsException 
	 */
	public void insertTeam(TeamVO newTeam) throws SQLException, TeamAlreadyExistsException {
		if (existsTeamName(newTeam.getName())) {
			throw new TeamAlreadyExistsException(newTeam.getName());
		}
		PreparedStatement preparedStmt = null;
		try {
			String sql = QueryBuilder.createInsert(getClass(),
					new String[]{"Name", "ShortName", "FoundedYear", "Achievements", "Location", "Logo"},
					new String[]{
							newTeam.getName(),
							newTeam.getShortName(),
							Integer.toString(newTeam.getFoundedYear()),
							Integer.toString(newTeam.getAchievements()),
							newTeam.getLocation(),
							newTeam.getLogo()
						});
			preparedStmt = getConnection().prepareStatement(sql);
			preparedStmt.executeUpdate();
		}
		finally {
			super.closeConnection(preparedStmt);
		}
	}
	
	public TeamVO getUserTeam(String username) throws SQLException {
		TeamVO team = null;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = QueryBuilder.createSelect(TeamDAO.class, "Teams.*") + " WHERE Teams.Id = "
					+ "(SELECT Users.IdTeam FROM Users WHERE Users.IdTeam = Teams.Id AND Users.username = ?);";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setString(1, username);
			rs = preparedStmt.executeQuery();
			if (rs.next()) {
				team = TeamDAO.getFullTeamVO(rs);
			}
		}
		finally {
			super.closeConnection(preparedStmt, rs);
		}
		return team;
	}
	
	/**
	 * Gets the instance of a TeamVO from the ResultSet.
	 * @param rs The ResultSet of the query
	 * @return The Team Value Object
	 * @throws SQLException
	 */
	protected static TeamVO getFullTeamVO(ResultSet rs) throws SQLException {
		return new TeamVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getString(7));
	}
	
}
