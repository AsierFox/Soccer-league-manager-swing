package com.devdream.exception;

public class ItemAlreadyException extends Exception {
	private static final long serialVersionUID = 7654445572405226504L;
	
	private static String EXISTS_MSG;
	
	public ItemAlreadyException(String itemName, String repeatedFieldName, String value) {
		EXISTS_MSG = "The " + itemName + " with the " + repeatedFieldName + " '" + value + "' already exists.";
	}
	
	@Override
	public String getMessage() {
		return EXISTS_MSG;
	}

}
