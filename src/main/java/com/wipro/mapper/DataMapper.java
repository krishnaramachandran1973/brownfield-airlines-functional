package com.wipro.mapper;

import com.wipro.dto.FareDto;
import com.wipro.dto.FlightDto;
import com.wipro.model.Fare;
import com.wipro.model.Flight;

public class DataMapper {
	public static FlightDto toFlightDto(Flight flight) {
		FareDto fareDto = FareDto.builder()
				.id(flight.getId())
				.amount(flight.getFare()
						.getAmount())
				.currency(flight.getFare()
						.getCurrency())
				.build();
		return FlightDto.builder()
				.id(flight.getId())
				.flightNumber(flight.getFlightNumber())
				.departureAirport(flight.getDepartureAirport())
				.departureTime(flight.getDepartureTime())
				.arrivalTime(flight.getArrivalTime())
				.destinationAirport(flight.getDestinationAirport())
				.availableSeats(flight.getAvailableSeats())
				.fareDto(fareDto)
				.type(flight.getType())
				.build();
	}

	public static Flight toFlight(FlightDto flightDto) {
		Fare fare = Fare.builder()
				.id(flightDto.getId())
				.amount(flightDto.getFareDto()
						.getAmount())
				.currency(flightDto.getFareDto()
						.getCurrency())
				.build();

		return Flight.builder()
				.flightNumber(flightDto.getFlightNumber())
				.departureAirport(flightDto.getDepartureAirport())
				.destinationAirport(flightDto.getDestinationAirport())
				.departureTime(flightDto.getDepartureTime())
				.arrivalTime(flightDto.getArrivalTime())
				.type(flightDto.getType())
				.availableSeats(flightDto.getAvailableSeats())
				.fare(fare)
				.build();
	}
}
