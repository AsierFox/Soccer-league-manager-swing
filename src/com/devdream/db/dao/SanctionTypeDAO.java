package com.devdream.db.dao;

import java.sql.SQLException;

import com.devdream.db.vo.SanctionTypeVO;

public class SanctionTypeDAO extends DAO {

	@Override
	public void createTable() throws SQLException {
		super.initTableCreation(SanctionTypeVO.class);
	}

}
