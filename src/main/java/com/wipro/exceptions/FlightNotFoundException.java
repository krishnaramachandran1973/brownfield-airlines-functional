package com.wipro.exceptions;

import lombok.Getter;

public class FlightNotFoundException extends RuntimeException {
	@Getter
	private final int errorCode = 100;

	public FlightNotFoundException(String message) {
		super(message);
	}

}
