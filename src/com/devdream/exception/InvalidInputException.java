package com.devdream.exception;

public class InvalidInputException extends Exception {
	private static final long serialVersionUID = 1734315658506076314L;
	
	private String errorMsg;
	
	public InvalidInputException(final String personalizedEntryMsg) {
		errorMsg = personalizedEntryMsg;
	}
	
	public InvalidInputException(final String personalizedEntryMsg, final String fields) {
		this(personalizedEntryMsg);
		errorMsg += " with field(s): " + fields + "!";
	}
	
	@Override
	public String getMessage() {
		return errorMsg;
	}

}
