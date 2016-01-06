package com.devdream.validator;

import java.sql.SQLException;

import com.devdream.db.dao.PlayerDAO;
import com.devdream.exception.InvalidInputException;
import com.devdream.exception.ItemAlreadyException;
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
	public void validate() throws InvalidInputException, SQLException, ItemAlreadyException {
		if (StringHelper.isStringNull(firstName) || StringHelper.isStringNull(surname) || StringHelper.isStringNull(position)) {
			throw new InvalidInputException("You must fill all the fields");
		}
		if (MathHelper.isNumeric(firstName) || MathHelper.isNumeric(surname)) {
			throw new InvalidInputException("The names can't be integers!");
		}
		if (age < 18 || age > 70) {
			throw new InvalidInputException("The age is not valid!");
		}
		if (dorsal < Player.MIN_DORSAL_VALUE || dorsal > Player.MAX_DORSAL_VALUE) {
			throw new InvalidInputException("The dorsal must be between " + Player.MIN_DORSAL_VALUE + " and "
					+ Player.MAX_DORSAL_VALUE + "!");
		}
		PlayerDAO playerDAO = new PlayerDAO();
		if (playerDAO.existsPlayerDorsal(dorsal)) {
			throw new ItemAlreadyException("player", "dorsal", Integer.toString(dorsal));
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
