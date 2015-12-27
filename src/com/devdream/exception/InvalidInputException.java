package com.devdream.exception;

public class InvalidInputException extends Exception {
	private static final long serialVersionUID = 1734315658506076314L;

	private String errorMsg;
	
	public InvalidInputException(final String insideScopeName) {
		errorMsg = "Error in " + insideScopeName;
	}
	
	public InvalidInputException(final String insideScopeName, final String fields) {
		this(insideScopeName);
		errorMsg += " with field(s): " + fields + "!";
	}
	
	@Override
	public String getMessage() {
		return errorMsg;
	}
	
}
