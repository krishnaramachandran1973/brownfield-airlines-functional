package com.wipro.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.support.WebExchangeBindException;

import com.wipro.errors.FlightError;

public class Util {
	public static FlightError buildError(WebExchangeBindException ex, String message) {
		List<String> fes = new ArrayList<>();

		ex.getFieldErrors()
				.forEach(fe -> fes.add(fe.getField() + " " + fe.getDefaultMessage()));

		FlightError fe = FlightError.builder()
				.errorCode(103)
				.errorMessage(message + " " + ex.getErrorCount() + " errors")
				.errors(fes)
				.build();
		return fe;
	}

}
