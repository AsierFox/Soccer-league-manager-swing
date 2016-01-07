package com.devdream.db.dao;

import java.sql.SQLException;

import com.devdream.db.vo.GoalVO;

public class GoalDAO extends DAO {

	@Override
	public void createTable() throws SQLException {
		super.initTableCreation(GoalVO.class);
	}

	public int getTeamScore() {
		int goals = 0;
		
		return goals;
	}

}
