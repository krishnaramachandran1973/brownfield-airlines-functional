package com.wipro.exceptions;

import lombok.Getter;

public class FlightCannotBeSavedException extends RuntimeException {
	@Getter
	private final int errorCode = 101;

	public FlightCannotBeSavedException(String message) {
		super(message);
	}

}
