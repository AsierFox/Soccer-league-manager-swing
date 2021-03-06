package com.devdream.controller;

import java.sql.SQLException;

import com.devdream.db.dao.UserDAO;
import com.devdream.db.vo.UserVO;
import com.devdream.exception.InvalidInputException;
import com.devdream.exception.LoginFailedException;
import com.devdream.model.Team;
import com.devdream.model.User;
import com.devdream.util.StringHelper;

/**
 * This controller manages the login for the user, getting it and
 * redirecting to the main application.
 * 
 * @author Asier Gonzalez
 */
public class LoginController extends Controller {

	/**
	 * This method logins an user by its user name and password entered.
	 * @return User The logged user
	 * @throws InvalidInputException
	 * @throws LoginFailedException
	 * @throws SQLException
	 */
	public User login(String username, String password) throws InvalidInputException, LoginFailedException, SQLException {
		if (StringHelper.isStringNull(username) || StringHelper.isStringNull(password)) {
			throw new InvalidInputException("login", "username or password");
		}
		UserDAO userDAO = new UserDAO();
		if (!userDAO.checkLogin(username, password)) {
			throw new LoginFailedException();
		}
		UserVO userVO = userDAO.getUserByUsername(username);
		Team userTeam = super.getUserTeam(userVO.getUsername());
		User user = new User(userVO.getUsername(), userVO.getName(), userVO.getSurname(), userTeam);
		
		return super.setLoggedUser(user);
	}

}
