package com.devdream.exception;

public class NotTableItemSelectedException extends Exception {
	private static final long serialVersionUID = -1404798192984013060L;
	
	private static String NOT_ITEM_SELECTED;
	
	public NotTableItemSelectedException(final String itemName) {
		NOT_ITEM_SELECTED = "There is no " + itemName + " selected!";
	}
	
	@Override
	public String getMessage() {
		return NOT_ITEM_SELECTED;
	}

}
