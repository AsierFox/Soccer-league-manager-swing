package com.devdream.db.dao;

import java.sql.SQLException;

import com.devdream.db.vo.SeasonVO;

public class SeasonDAO extends DAO {

	@Override
	public void createTable() throws SQLException {
		super.initTableCreation(SeasonVO.class);
	}
	
}
