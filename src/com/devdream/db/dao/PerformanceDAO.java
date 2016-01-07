package com.devdream.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.devdream.db.vo.PerformanceVO;

public class PerformanceDAO extends DAO {

	private int idTeam;
	private int idGame;
	
	public PerformanceDAO() {
		idTeam = -1;
		idGame = -1;
	}
	
	public PerformanceDAO(int idTeam, int idGame) {
		this.idTeam = idTeam;
		this.idGame = idGame;
	}
	
	@Override
	public void createTable() throws SQLException {
		super.initTableCreation(PerformanceVO.class);
	}
	
	public int getGoals() throws SQLException {
		int goals = 0;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT Goals FROM Performances WHERE IdTeam = ? AND IdGame = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, idTeam);
			preparedStmt.setInt(2, idGame);
			rs = preparedStmt.executeQuery();
			if (rs.next()) {
				goals = rs.getInt(1);
			}
		}
		finally {
			super.closeConnection(preparedStmt, rs);
		}
		return goals;
	}
	
	public float getPossession() throws SQLException {
		float possession = 0;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT Possession FROM Performances WHERE IdTeam = ? AND IdGame = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, idTeam);
			preparedStmt.setInt(2, idGame);
			rs = preparedStmt.executeQuery();
			if (rs.next()) {
				possession = rs.getFloat(1);
			}
		}
		finally {
			super.closeConnection(preparedStmt, rs);
		}
		return possession;
	}
	
	public int getShots() throws SQLException {
		int shots = 0;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT Shots FROM Performances WHERE IdTeam = ? AND IdGame = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, idTeam);
			preparedStmt.setInt(2, idGame);
			rs = preparedStmt.executeQuery();
			if (rs.next()) {
				shots = rs.getInt(1);
			}
		}
		finally {
			super.closeConnection(preparedStmt, rs);
		}
		return shots;
	}

	public int getPasses() throws SQLException {
		int passes = 0;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT Passes FROM Performances WHERE IdTeam = ? AND IdGame = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, idTeam);
			preparedStmt.setInt(2, idGame);
			rs = preparedStmt.executeQuery();
			if (rs.next()) {
				passes = rs.getInt(1);
			}
		}
		finally {
			super.closeConnection(preparedStmt, rs);
		}
		return passes;
	}
	
	public int getFouls() throws SQLException {
		int fouls = 0;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT Fouls FROM Performances WHERE IdTeam = ? AND IdGame = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, idTeam);
			preparedStmt.setInt(2, idGame);
			rs = preparedStmt.executeQuery();
			if (rs.next()) {
				fouls = rs.getInt(1);
			}
		}
		finally {
			super.closeConnection(preparedStmt, rs);
		}
		return fouls;
	}
	
	public int getOffsides() throws SQLException {
		int offsides = 0;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT Offsides FROM Performances WHERE IdTeam = ? AND IdGame = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, idTeam);
			preparedStmt.setInt(2, idGame);
			rs = preparedStmt.executeQuery();
			if (rs.next()) {
				offsides = rs.getInt(1);
			}
		}
		finally {
			super.closeConnection(preparedStmt, rs);
		}
		return offsides;
	}
	
	public int getCorners() throws SQLException {
		int corners = 0;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT Corners FROM Performances WHERE IdTeam = ? AND IdGame = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, idTeam);
			preparedStmt.setInt(2, idGame);
			rs = preparedStmt.executeQuery();
			if (rs.next()) {
				corners = rs.getInt(1);
			}
		}
		finally {
			super.closeConnection(preparedStmt, rs);
		}
		return corners;
	}

}
