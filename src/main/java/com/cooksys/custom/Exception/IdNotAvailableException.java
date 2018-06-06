package com.cooksys.custom.Exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
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
