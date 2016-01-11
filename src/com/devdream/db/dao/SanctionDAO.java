package com.devdream.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.devdream.db.vo.PlayerVO;
import com.devdream.db.vo.SanctionTypeVO;
import com.devdream.db.vo.SanctionVO;
import com.devdream.model.Player;
import com.devdream.model.Sanction;
import com.devdream.model.Sanctioned;
import com.devdream.util.QueryBuilder;

public class SanctionDAO extends DAO {

	@Override
	public void createTable() throws SQLException {
		super.initTableCreation(SanctionVO.class);
	}

	public ArrayList<Sanctioned> getGameSanctions(int idGame) throws SQLException {
		ArrayList<Sanctioned> sanctions = new ArrayList<>();
		PlayerDAO playerDAO = new PlayerDAO();
		SanctionTypeDAO sanctionTypeDAO = new SanctionTypeDAO();
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		try {
			String sql =  QueryBuilder.createSelect(getClass(), "IdPlayer, IdSanctionType") + " WHERE IdGame = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, idGame);
			rs = preparedStmt.executeQuery();
			while (rs.next()) {
				PlayerVO playerVO = playerDAO.getPlayerById(rs.getInt(1));
				SanctionTypeVO sanctionTypeVO = sanctionTypeDAO.getSanctionNameById(rs.getInt(2));
				if (playerVO != null) {
					sanctions.add(new Sanctioned(new Player(playerVO.getFirstName(), playerVO.getSurname(),
							playerVO.getAge(), playerVO.getDorsal(), playerVO.getPosition()),
							new Sanction(sanctionTypeVO.getType())));
				} else {
					sanctions.add(new Sanctioned(Player.getAnonymousPlayer(), new Sanction(sanctionTypeVO.getType())));
				}
			}
		} finally {
			super.closeConnection(preparedStmt, rs);
		}
		return sanctions;
	}

	public boolean insertSanction(int idGame, int idPlayer, int idSanction) throws SQLException {
		boolean inserted;
		PreparedStatement preparedStmt = null;
		try {
			String sql = "INSERT INTO " + QueryBuilder.getTableNameFromDAO(getClass()) +
					"(IdGame, IdPlayer, IdSanctionType) VALUES (?, ?, ?);";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, idGame);
			preparedStmt.setInt(2, idPlayer);
			preparedStmt.setInt(3, idSanction);
			inserted = preparedStmt.executeUpdate() == 1;
		} finally {
			super.closeConnection(preparedStmt);
		}
		return inserted;
	}

	public boolean deleteSanctioned(int idGame, int playerId, int sanctionId) throws SQLException {
		boolean deleted;
		PreparedStatement preparedStmt = null;
		try {
			String sql = "DELETE FROM " + QueryBuilder.getTableNameFromDAO(getClass()) +
					" WHERE IdGame = ? AND IdPlayer = ? AND IdSanctionType = ?;";
			preparedStmt = super.getConnection().prepareStatement(sql);
			preparedStmt.setInt(1, idGame);
			preparedStmt.setInt(2, playerId);
			preparedStmt.setInt(3, sanctionId);
			deleted = preparedStmt.executeUpdate() == 1;
		} finally {
			super.closeConnection(preparedStmt);
		}
		return deleted;
	}

}
