package com.devdream.db.dao;

import java.sql.SQLException;

import com.devdream.db.vo.SanctionVO;

public class SanctionDAO extends DAO {

	@Override
	public void createTable() throws SQLException {
		super.initTableCreation(SanctionVO.class);
	}

}
