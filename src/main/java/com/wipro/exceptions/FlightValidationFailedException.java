package com.wipro.exceptions;

import lombok.Getter;

public class FlightValidationFailedException extends RuntimeException {
	@Getter
	private final int errorCode = 103;

	public FlightValidationFailedException(String message) {
		super(message);
	}

}
