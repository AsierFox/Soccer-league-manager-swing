package com.devdream.exception;

import java.sql.SQLException;

public class DBConnectionException extends SQLException {
	private static final long serialVersionUID = 2286702085594204935L;
	
	private static final String ERROR_MSG = "Unable to connect to the database.";
	
	@Override
	public String getMessage() {
		return ERROR_MSG;
	}

}
