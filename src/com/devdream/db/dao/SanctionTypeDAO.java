package com.devdream.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.devdream.db.vo.SanctionTypeVO;
import com.devdream.model.Sanction;
import com.devdream.util.QueryBuilder;

public class SanctionTypeDAO extends DAO {

	@Override
	public void createTable() throws SQLException {
		super.initTableCreation(SanctionTypeVO.class);
	}

	public void insertSanctionType(String sanctionName) throws SQLException {
		PreparedStatement preparedStmt = null;
		try {
			String sql = "INSERT INTO " + QueryBuilder.getTableNameFromDAO(getClass()) +
					" (Type) VALUES (?);";
			preparedStmt = getConnection().prepareStatement(sql);
			preparedStmt.setString(1, sanctionName);
			preparedStmt.executeUpdate();
		} finally {
			super.closeConnection(preparedStmt);
		}
	}
	
	public SanctionTypeVO getSanctionNameById(int id) throws SQLException {
		SanctionTypeVO sanctionTypeVO = null;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = QueryBuilder.createSelect(getClass(), "*") + " WHERE Id = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, id);
			rs = preparedStmt.executeQuery();
			if (rs.next()) {
				sanctionTypeVO = new SanctionTypeVO(rs.getInt("Id"), rs.getString("Type"));
			}
		} finally {
			super.closeConnection(preparedStmt, rs);
		}
		return sanctionTypeVO;
	}

	public ArrayList<Sanction> getAllSanctionTypes() throws SQLException {
		ArrayList<Sanction> sactionTypes = new ArrayList<>();
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = QueryBuilder.createSelect(getClass(), "*");
			preparedStmt = super.getConnection().prepareStatement(sql);
			rs = preparedStmt.executeQuery();
			while (rs.next()) {
				sactionTypes.add(new Sanction(rs.getString("Type")));
			}
		} finally {
			super.closeConnection(preparedStmt, rs);
		}
		return sactionTypes;
	}

	public int getIdBySanctionType(String sanctionType) throws SQLException {
		int idSanction = 0;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql = QueryBuilder.createSelect(getClass(), "Id") + " WHERE Type = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setString(1, sanctionType);
			rs = preparedStmt.executeQuery();
			if (rs.next()) {
				idSanction = rs.getInt(1);
			}
		} finally {
			super.closeConnection(preparedStmt, rs);
		}
		return idSanction;
	}
}
