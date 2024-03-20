package com.wipro.errors;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
public class FlightError {
	@Getter
	private final int errorCode;
	@Getter
	private final String errorMessage;
	@Default
	@Getter
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<String> errors = new ArrayList<>();

	public void addValidationError(String error) {
		errors.add(error);
	}
}
