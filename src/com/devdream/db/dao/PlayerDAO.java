package com.devdream.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.devdream.db.vo.PlayerVO;
import com.devdream.model.Player;
import com.devdream.util.QueryBuilder;

public class PlayerDAO extends DAO {

	@Override
	public void createTable() throws SQLException {
		super.initTableCreation(PlayerVO.class);
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
					new String[]{"IdTeam", "FirstName", "Surname", "Age", "Dorsal", "Position"},
					new String[]{
							Integer.toString(newPlayer.getIdTeam()),
							newPlayer.getFirstName(),
							newPlayer.getSurname(),
							Integer.toString(newPlayer.getAge()),
							Integer.toString(newPlayer.getDorsal()),
							newPlayer.getPosition()
						});
			preparedStmt = getConnection().prepareStatement(sql);
			preparedStmt.executeUpdate();
		}
		finally {
			super.closeConnection(preparedStmt);
		}
	}
	
	/**
	 * Gets the player with the specific Id.
	 * @param id The Id of the player to search for
	 * @return
	 * @throws SQLException
	 */
	public PlayerVO getPlayerById(int id) throws SQLException {
		PlayerVO player = null;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = QueryBuilder.createSelect(getClass(), "*") + " WHERE Id = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, id);
			rs = preparedStmt.executeQuery();
			if (rs.next()) {
				player = new PlayerVO(rs.getString("FirstName"), rs.getString("Surname"), rs.getInt("Age"),
						rs.getInt("Dorsal"), rs.getString("Position"));
			}
		}
		finally {
			super.closeConnection(preparedStmt, rs);
		}
		return player;
	}
	
	/**
	 * Gets all the team players of a team passing the id of the target team.
	 * @param idTeam Id of the Team
	 * @return All the Player Value Objects in an ArrayList
	 * @throws SQLException
	 */
	public HashMap<Integer, Player> getTeamPlayers(int idTeam) throws SQLException {
		HashMap<Integer, Player> players = new HashMap<>();
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = QueryBuilder.createSelect(getClass(), "*") + " WHERE IdTeam = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, idTeam);
			rs = preparedStmt.executeQuery();
			while (rs.next()) {
				int dorsal = rs.getInt("Dorsal");
				players.put(dorsal, new Player(rs.getString("FirstName"), rs.getString("Surname"), rs.getInt("Age"),
						dorsal, rs.getString("Position")));
			}
		}
		finally {
			super.closeConnection(preparedStmt, rs);
		}
		return players;
	}
	
	public boolean existsPlayerDorsal(int dorsal) throws SQLException {
		boolean exists = false;
		PreparedStatement preparedStmt = null;
		try {
			String sql = "SELECT 1 FROM " + QueryBuilder.getTableNameFromDAO(getClass())
					+ " WHERE Dorsal = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, dorsal);
			exists = preparedStmt.executeQuery().next();
		}
		finally {
			super.closeConnection(preparedStmt);
		}
		return exists;
	}
	
	/**
	 * Modifies a player from the database, if the player does not exists, it creates a new one.
	 * @param playerVO The new player Value Object
	 * @param oldDorsal The dorsal of the player to modify
	 * @return If the player has been modified or not
	 * @throws SQLException
	 */
	public boolean modifyPlayer(PlayerVO playerVO, int oldDorsal) throws SQLException {
		boolean modified;
		PreparedStatement preparedStmt = null;
		String playersTableName = QueryBuilder.getTableNameFromDAO(getClass());
		try {
			String sql = "INSERT OR REPLACE INTO " + playersTableName +
					" (Id, IdTeam, FirstName, Surname, Age, Dorsal, Position) VALUES (" +
					"COALESCE((SELECT Id FROM " + playersTableName + " WHERE Dorsal = ?), NULL), " +
					"(SELECT IdTeam FROM " + playersTableName + " WHERE Dorsal = ?), " +
					"?, ?, ?, ?, ?);";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, playerVO.getDorsal());
			preparedStmt.setInt(2, oldDorsal);
			preparedStmt.setString(3, playerVO.getFirstName());
			preparedStmt.setString(4, playerVO.getSurname());
			preparedStmt.setInt(5, playerVO.getAge());
			preparedStmt.setInt(6, playerVO.getDorsal());
			preparedStmt.setString(7, playerVO.getPosition());
			modified = preparedStmt.executeUpdate() == 1;
		}
		finally {
			super.closeConnection(preparedStmt);
		}
		if (playerVO.getDorsal() != oldDorsal) { // If the player has a new dorsal, delete the old one
			deletePlayer(oldDorsal);
		}
		return modified;
	}
	
	/**
	 * Deletes a player by the dorsal.
	 * @param byDorsal The dorsal of the player
	 * @return If it was deleted
	 * @throws SQLException
	 */
	public boolean deletePlayer(int byDorsal) throws SQLException {
		boolean deleted;
		PreparedStatement preparedStmt = null;
		try {
			String sql = "DELETE FROM " + QueryBuilder.getTableNameFromDAO(getClass()) + " WHERE Dorsal = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, byDorsal);
			deleted = preparedStmt.executeUpdate() == 1;
		} finally {
			super.closeConnection(preparedStmt);
		}
		return deleted;
	}

}
