package com.devdream.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import com.devdream.db.dao.GoalDAO;
import com.devdream.db.dao.PlayerDAO;
import com.devdream.db.vo.PlayerVO;
import com.devdream.exception.InvalidInputException;
import com.devdream.exception.RecordAlreadyException;
import com.devdream.model.Scorer;
import com.devdream.validator.PlayerValidator;

public class PlayerController extends Controller {

	/**
	 * Checks and submits a new player.
	 * @return The submitted player
	 * @throws RecordAlreadyException 
	 * @throws SQLException 
	 * @throws InvalidInputException 
	 */
	public void submitPlayer(int teamId, String firstName, String surname, int age, String position, int dorsal)
			throws InvalidInputException, SQLException, RecordAlreadyException
	{
		PlayerValidator playerValidator = new PlayerValidator(firstName, surname, age, position, dorsal);
		playerValidator.validate();
		playerValidator.validateRepeatedDorsal();
		PlayerDAO playerDAO = new PlayerDAO();
		playerDAO.insertPlayer(new PlayerVO(teamId, firstName, surname, age, dorsal, position));
	}
	
	/**
	 * Updates a player
	 * @throws InvalidInputException
	 * @throws SQLException
	 * @throws RecordAlreadyException
	 */
	public void updatePlayer(String firstName, String surname, int age, String position, int dorsal, int playerDorsal)
			throws InvalidInputException, SQLException, RecordAlreadyException
	{
		PlayerValidator playerValidator = new PlayerValidator(firstName, surname, age, position, dorsal);
		playerValidator.validateText();
		playerValidator.validateNumber();
		playerValidator.validateAge();
		playerValidator.validateDorsal();
		playerValidator.validateRepeatedDorsal(playerDorsal);
		PlayerDAO playerDAO = new PlayerDAO();
		playerDAO.modifyPlayer(new PlayerVO(firstName, surname, age, dorsal, position), playerDorsal);
	}
	
	/**
	 * Deletes a player from a team
	 * @param playerDorsal The dorsal of the player
	 * @throws SQLException
	 */
	public void deleteTeamPlayer(int playerDorsal) throws SQLException {
		PlayerDAO playerDAO = new PlayerDAO();
		playerDAO.deletePlayer(playerDorsal);
	}
	
	/** Returns the top scorers. */
	public ArrayList<Scorer> getTopScorers() {
		GoalDAO goalDAO = new GoalDAO();
		try {
			return goalDAO.getTopScorers();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

}
