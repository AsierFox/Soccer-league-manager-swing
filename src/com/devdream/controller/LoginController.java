package com.devdream.controller;

import java.sql.SQLException;

import com.devdream.db.dao.TeamDAO;
import com.devdream.db.dao.UserDAO;
import com.devdream.db.vo.TeamVO;
import com.devdream.db.vo.UserVO;
import com.devdream.exception.InvalidInputException;
import com.devdream.exception.LoginFailedException;
import com.devdream.model.Team;
import com.devdream.model.User;
import com.devdream.util.StringHelper;

public class LoginController extends Controller {
	
	public LoginController() {}

	public User login(String username, String password) throws InvalidInputException, LoginFailedException, SQLException {
		if (StringHelper.isStringNull(username) || StringHelper.isStringNull(password)) {
			throw new InvalidInputException("login", "username or password");
		}
		UserDAO userDAO = new UserDAO();
		if (!userDAO.checkLogin(username, password)) {
			throw new LoginFailedException();
		}
		TeamDAO teamDAO = new TeamDAO();
		TeamVO userTeamVO = teamDAO.getUserTeam(username);
		UserVO userVO = userDAO.getUserByUsername(username);
		
		Team userTeam = null;
		if (userTeamVO != null) {
			userTeam = new Team(userTeamVO.getName(), userTeamVO.getShortName(), userTeamVO.getFoundedYear(),
					userTeamVO.getAchievements(), userTeamVO.getLocation(), userTeamVO.getLogo());
		}
		User user = new User(userVO.getUsername(), userVO.getName(), userVO.getSurname(), userTeam);
		
		return super.setLoggedUser(user);
	}
	
}
