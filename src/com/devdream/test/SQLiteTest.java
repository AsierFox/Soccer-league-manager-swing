package com.devdream.test;

import java.sql.SQLException;
import java.util.ArrayList;

import com.devdream.db.dao.GameDAO;
import com.devdream.db.dao.GoalDAO;
import com.devdream.db.dao.LeagueDAO;
import com.devdream.db.dao.PerformanceDAO;
import com.devdream.db.dao.PlayerDAO;
import com.devdream.db.dao.SanctionDAO;
import com.devdream.db.dao.SanctionTypeDAO;
import com.devdream.db.dao.SeasonDAO;
import com.devdream.db.dao.TeamDAO;
import com.devdream.db.dao.UserDAO;
import com.devdream.db.vo.GameVO;
import com.devdream.db.vo.LeagueVO;
import com.devdream.db.vo.PlayerVO;
import com.devdream.db.vo.SeasonVO;
import com.devdream.db.vo.TeamVO;
import com.devdream.db.vo.UserVO;
import com.devdream.exception.ItemAlreadyException;
import com.devdream.util.DateHelper;

/**
 * Class to test the connections and operations over the database.
 * 
 * @author Asier Gonzalez
 */
@SuppressWarnings("unused")
public class SQLiteTest {

	public static void main(String[] args) throws SQLException, ItemAlreadyException {
//		createTables();
//		insertValues();
//		selectValues();
		System.out.println("Ended");
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
		
		UserDAO userDAO = new UserDAO();
		if (!userDAO.tableExists()) {
			userDAO.createTable();
		}
		
		LeagueDAO leagueDAO = new LeagueDAO();
		if (!leagueDAO.tableExists()) {
			leagueDAO.createTable();
		}
		
		SeasonDAO seasonDAO = new SeasonDAO();
		if (!seasonDAO.tableExists()) {
			seasonDAO.createTable();
		}
		
		GameDAO gameDAO = new GameDAO();
		if (!gameDAO.tableExists()) {
			gameDAO.createTable();
		}
		
		PerformanceDAO performanceDAO = new PerformanceDAO();
		if (!performanceDAO.tableExists()) {
			performanceDAO.createTable();
		}
		
		GoalDAO goalDAO = new GoalDAO();
		if (!goalDAO.tableExists()) {
			goalDAO.createTable();
		}
		
		SanctionTypeDAO sanctionTypeDAO = new SanctionTypeDAO();
		if (!sanctionTypeDAO.tableExists()) {
			sanctionTypeDAO.createTable();
		}
		
		SanctionDAO sanctionDAO = new SanctionDAO();
		if (!sanctionDAO.tableExists()) {
			sanctionDAO.createTable();
		}
	}
	
	private static void insertValues() throws SQLException, ItemAlreadyException {
//		TeamDAO teamDAO = new TeamDAO();
//		teamDAO.insertTeam(new TeamVO("Test", "TT", 2015, 0, "Amurrio", "team-default.png"));
//		teamDAO.insertTeam(new TeamVO("NoPlayers", "NP", 2012, 2, "Bilbao", "team-default.png"));
//		teamDAO.insertTeam(new TeamVO("HomeTeam", "HT", 2011, 0, "Hawaii", "team-default.png"));
//		teamDAO.insertTeam(new TeamVO("AwayTeam", "AT", 2012, 0, "Los Angeles", "team-default.png"));
//		
//		PlayerDAO playerDAO = new PlayerDAO();
//		playerDAO.insertPlayer(new PlayerVO(1, "Asier", "Gonzalez", 32, 10, "Forward"));
//		playerDAO.insertPlayer(new PlayerVO(1, "Andoni", "ASds", 28, 6, "Defence"));

		UserDAO userDAO = new UserDAO();
		userDAO.insertUser(new UserVO(1, "mikel", "123", "Mikel", "Linares"));
		
//		LeagueDAO leagueDAO = new LeagueDAO();
//		leagueDAO.insertLeague(new LeagueVO("25/10/2015", "31/12/2015", "MyLeague", "League description", 1));
//		
//		GameDAO gameDAO = new GameDAO();
//		gameDAO.insertGame(new GameVO(3, 4));
//		
//		SeasonDAO seasonDAO = new SeasonDAO();
//		seasonDAO.insertSeason(new SeasonVO(1, 3, "28/12/2015"));
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
		
		System.out.println("NUMBER OF OPPONENT TEAMS: " + teamDAO.getOpponentsCount("Test"));
		
		String name = "Test";
		System.out.println("TEAM EXISTS WITH NAME [" + name + "]? " + teamDAO.existsTeamName(name));
		System.out.println("GET TEAM BY NAME: " + teamDAO.getTeamByName(name));
		System.out.println("GET TEAM Id BY NAME: " + teamDAO.getTeamIdByName(name));
		
		UserDAO userDAO = new UserDAO();
		String username = "mikel", pass = "123";
		System.out.println("USER LOGIN? " + userDAO.checkLogin(username, pass));
		System.out.println("TEAM OF USER: " + teamDAO.getUserTeam(username));
		System.out.println("USER BY USERNAME: " + userDAO.getUserByUsername(username));
		
		PlayerDAO playerDAO = new PlayerDAO();
		int idTeam = 1;
		System.out.println("GET PLAYERS OF TEAM ID " + idTeam + ": " + playerDAO.getTeamPlayers(idTeam));
	}

}
