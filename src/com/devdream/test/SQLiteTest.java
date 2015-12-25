package com.devdream.test;

import java.sql.SQLException;
import java.util.ArrayList;

import com.devdream.db.dao.PlayerDAO;
import com.devdream.db.dao.TeamDAO;
import com.devdream.db.vo.PlayerVO;
import com.devdream.db.vo.TeamVO;

/**
 * Class to test the connections and operations over the database.
 * 
 * @author Asier Gonzalez
 */
@SuppressWarnings("unused")
public class SQLiteTest {

	public static void main(String[] args) throws SQLException {
//		createTables();
//		insertValues();
	}
	
	private static void createTables() throws SQLException {
		TeamDAO teamDAO = new TeamDAO();
		if (!teamDAO.tableExists()) {
			teamDAO.createTable();
		}

		PlayerDAO playerDAO = new PlayerDAO();
		if (!playerDAO.tableExists()) {
			playerDAO.createTable();
		}
	}
	
	private static void insertValues() throws SQLException {
		TeamDAO teamDAO = new TeamDAO();
		teamDAO.insertTeam(new TeamVO("Test", "TT", 2015, 0, "Amurrio", "testlogo.png"));
		teamDAO.insertTeam(new TeamVO("NoPlayers", "NP", 2012, 2, "Bilbao", "testlogo.png"));
		
		PlayerDAO playerDAO = new PlayerDAO();
		playerDAO.insertPlayer(new PlayerVO(1, "Asier", "Gonzalez", "Forward", 20));
		playerDAO.insertPlayer(new PlayerVO(1, "Andoni", "ASds", "Defence", 19));
	}
	
	private static void tableExists() {
		TeamDAO teamDAO = new TeamDAO();
		System.out.println("Teams table? " + teamDAO.tableExists());
		
		PlayerDAO playerDAO = new PlayerDAO();
		System.out.println("Players table? " + teamDAO.tableExists());
	}
	
	private static void selectValues() throws SQLException {
		TeamDAO teamDAO = new TeamDAO();
		ArrayList<TeamVO> teams = teamDAO.getAllTeams();
		System.out.println("SELECT ALL TEAMS= " + teams.toString());
		
		System.out.println("Team ID with players: " + teamDAO.getTeamIdWithPlayers());
		
		System.out.println("Get all team by Id= " + teamDAO.getTeamById(2));
	}

}
