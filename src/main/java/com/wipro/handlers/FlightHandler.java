package com.wipro.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;

import com.wipro.dto.FlightDto;
import com.wipro.exceptions.FlightCannotBeSavedException;
import com.wipro.exceptions.FlightNotFoundException;
import com.wipro.exceptions.FlightValidationFailedException;
import com.wipro.mapper.DataMapper;
import com.wipro.model.Flight;
import com.wipro.repository.FlightRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class FlightHandler {
	private FlightRepository repository;

	public FlightHandler(FlightRepository repository) {
		this.repository = repository;
	}

	public HandlerFunction<ServerResponse> findAllFlights = (request) -> {
		Flux<FlightDto> dtoFlux = Flux.fromIterable(repository.findAll())
				.map(DataMapper::toFlightDto)
				.switchIfEmpty(Mono.error(() -> new FlightNotFoundException("No flights found")))
				.subscribeOn(Schedulers.boundedElastic());

		return ServerResponse.ok()
				.body(dtoFlux, FlightDto.class);
	};

	public Mono<ServerResponse> findById(ServerRequest request) {
		Long id = Long.parseLong(request.pathVariable("id"));

		return Mono.justOrEmpty(this.repository.findById(id))
				.map(DataMapper::toFlightDto)
				.switchIfEmpty(Mono.error(() -> new FlightNotFoundException("No flights with id " + id + " found")))
				.subscribeOn(Schedulers.boundedElastic())
				.flatMap(dto -> ServerResponse.ok()
						.bodyValue(dto));
	}

	public Mono<ServerResponse> createFlight(ServerRequest request) {
		Mono<Flight> toSaveFlight = request.bodyToMono(FlightDto.class)
				.flatMap(dto -> this.validate(dto))
				.map(DataMapper::toFlight);

		return toSaveFlight.map(this.repository::save)
				.map(DataMapper::toFlightDto)
				.switchIfEmpty(Mono.error(() -> new FlightCannotBeSavedException("Cannot save flight")))
				.flatMap(dto -> {
					UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUri(request.uri());
					uriBuilder.path("/" + dto.getId()
							.toString())
							.build();
					String uri = uriBuilder.toUriString();
					return ServerResponse.status(HttpStatus.CREATED)
							.header("location", uri)
							.build();
				})
				.subscribeOn(Schedulers.boundedElastic());

	}

	private Mono<FlightDto> validate(FlightDto flightDto) {
		var validator = new FlightDto.FlightDtoValidator();
		BeanPropertyBindingResult errors = new BeanPropertyBindingResult(flightDto, "flightDto");
		validator.validate(flightDto, errors);
		if (errors.hasErrors()) {
			throw new FlightValidationFailedException(errors.toString());
		}
		return Mono.just(flightDto);
	}
}
