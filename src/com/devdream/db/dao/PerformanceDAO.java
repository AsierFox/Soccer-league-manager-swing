package com.devdream.db.dao;

import java.sql.SQLException;

import com.devdream.db.vo.PerformanceVO;

public class PerformanceDAO extends DAO {

	@Override
	public void createTable() throws SQLException {
		super.initTableCreation(PerformanceVO.class);
	}
}
