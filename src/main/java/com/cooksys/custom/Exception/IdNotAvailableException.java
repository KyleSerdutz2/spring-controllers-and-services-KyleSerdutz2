package com.cooksys.custom.Exception;

public class IdNotAvailableException extends Exception {
	
	private long id;

	public IdNotAvailableException(long id) {
		this.id = id;
	}
	
	@Override
	public String getMessage() {
		return id + " is already in use!";
	}
}
