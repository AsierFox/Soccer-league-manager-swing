package com.devdream.validator;

import java.sql.SQLException;

import com.devdream.db.dao.PlayerDAO;
import com.devdream.exception.InvalidInputException;
import com.devdream.exception.RecordAlreadyException;
import com.devdream.model.Player;
import com.devdream.util.MathHelper;
import com.devdream.util.StringHelper;

/**
 * Validates a Player data.
 * 
 * @author Asier Gonzalez
 */
public class PlayerValidator {

	//
	// Attributes
	private String firstName;
	private String surname;
	private int age;
	private String position;
	private int dorsal;
	
	//
	// Constructors
	public PlayerValidator(String firstName, String surname, int age, String position, int dorsal) {
		this.firstName = firstName;
		this.surname = surname;
		this.age = age;
		this.position = position;
		this.dorsal = dorsal;
	}
	
	//
	// Methods
	public void validate() throws InvalidInputException, SQLException, RecordAlreadyException {
		validateText();
		validateNumber();
		validateAge();
		validateDorsal();
		validateRepeatedDorsal();
	}
	
	public void validateText() throws InvalidInputException {
		if (StringHelper.isStringNull(firstName) || StringHelper.isStringNull(surname) || StringHelper.isStringNull(position)) {
			throw new InvalidInputException("You must fill all the fields");
		}
	}
	
	public void validateNumber() throws InvalidInputException {
		if (MathHelper.isNumeric(firstName) || MathHelper.isNumeric(surname)) {
			throw new InvalidInputException("The names can't be integers!");
		}
	}
	
	public void validateAge() throws InvalidInputException {
		if (age < 18 || age > 70) {
			throw new InvalidInputException("The age is not valid!");
		}
	}
	
	public void validateDorsal() throws InvalidInputException {
		if (dorsal < Player.MIN_DORSAL_VALUE || dorsal > Player.MAX_DORSAL_VALUE) {
			throw new InvalidInputException("The dorsal must be between " + Player.MIN_DORSAL_VALUE + " and "
					+ Player.MAX_DORSAL_VALUE + "!");
		}
	}
	
	/**
	 * Validates all the repeated dorsal.
	 * @throws SQLException
	 * @throws RecordAlreadyException
	 */
	public void validateRepeatedDorsal() throws SQLException, RecordAlreadyException {
		PlayerDAO playerDAO = new PlayerDAO();
		if (playerDAO.existsPlayerDorsal(dorsal)) {
			throw new RecordAlreadyException("player", "dorsal", Integer.toString(dorsal));
		}
	}
	
	/**
	 * Validates the repeated dorsal but the passed.
	 * @throws SQLException
	 * @throws RecordAlreadyException
	 */
	public void validateRepeatedDorsal(int skipDorsal) throws SQLException, RecordAlreadyException {
		PlayerDAO playerDAO = new PlayerDAO();
		if (skipDorsal != dorsal && playerDAO.existsPlayerDorsal(dorsal)) {
			throw new RecordAlreadyException("player", "dorsal", Integer.toString(dorsal));
		}
	}
	
	//
	// Getters and setters
	public String getFirstName() {
		return firstName;
	}
	public String getSurname() {
		return surname;
	}
	public int getAge() {
		return age;
	}
	public String getPosition() {
		return position;
	}
	public int getDorsal() {
		return dorsal;
	}
	
}
