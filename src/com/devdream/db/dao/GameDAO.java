package com.devdream.db.dao;

import java.sql.SQLException;

import com.devdream.db.vo.GameVO;

public class GameDAO extends DAO {

	@Override
	public void createTable() throws SQLException {
		super.initTableCreation(GameVO.class);
	}
}
