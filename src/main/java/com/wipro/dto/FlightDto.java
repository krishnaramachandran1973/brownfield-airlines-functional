package com.wipro.dto;

import java.time.LocalDateTime;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.wipro.model.FlightType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightDto {

	private Long id;
	private String flightNumber;
	private String departureAirport;
	private String destinationAirport;
	private LocalDateTime departureTime;
	private LocalDateTime arrivalTime;
	private int availableSeats;
	@Default
	private FareDto fareDto = new FareDto();
	private FlightType type;

	public static class FlightDtoValidator implements Validator {

		@Override
		public boolean supports(Class<?> clazz) {
			return FlightDto.class.isAssignableFrom(clazz);
		}

		@Override
		public void validate(Object target, Errors errors) {
			ValidationUtils.rejectIfEmpty(errors, "departureAirport", "required", new Object[] { "departureAirport" },
					"departureAirport is required");
			ValidationUtils.rejectIfEmpty(errors, "destinationAirport", "required",
					new Object[] { "destinationAirport" }, "destinationAirport is required");

		}

	}
}
