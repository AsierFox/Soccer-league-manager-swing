package com.devdream.exception;

/**
 * This exception is thrown when an operation is cancelled.
 * 
 * @author Asier Gonzalez
 */
public class OperationCancelledException extends Exception {
	private static final long serialVersionUID = 2623691761146943884L;
	
	private final String ERROR_MSG;
	
	public OperationCancelledException() {
		ERROR_MSG = "Operation cancelled!";
	}
	
	public OperationCancelledException(final String ERROR_MSG) {
		this.ERROR_MSG = ERROR_MSG;
	}
	
	@Override
	public String getMessage() {
		return ERROR_MSG;
	}

}
