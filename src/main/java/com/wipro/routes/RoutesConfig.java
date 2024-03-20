package com.wipro.routes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.WebExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.errors.FlightError;
import com.wipro.exceptions.FlightNotFoundException;
import com.wipro.exceptions.FlightValidationFailedException;
import com.wipro.handlers.FlightHandler;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Configuration
public class RoutesConfig {
	private final FlightHandler handler;
	private final ObjectMapper mapper;

	@Bean
	public RouterFunction<ServerResponse> routes() {
		return RouterFunctions.route()
				.GET("/api/flights/{id}", handler::findById)
				.GET("/api/flights", handler.findAllFlights)
				.POST("/api/flights", handler::createFlight)
				.build();
	}

	@Bean
	@Order(-2)
	public WebExceptionHandler exceptionHandler() {
		return (exchange, ex) -> {
			FlightError flightError = null;
			String json = null;
			if (ex instanceof FlightNotFoundException fnfe) {
				flightError = FlightError.builder()
						.errorCode(fnfe.getErrorCode())
						.errorMessage(fnfe.getMessage())
						.build();
			} else if (ex instanceof FlightValidationFailedException fvfe) {
				flightError = FlightError.builder()
						.errorCode(fvfe.getErrorCode())
						.errorMessage(fvfe.getMessage())
						.build();
			}
			try {
				json = mapper.writeValueAsString(flightError);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

			exchange.getResponse()
					.setStatusCode(HttpStatus.BAD_REQUEST);
			return exchange.getResponse()
					.writeWith(Mono.just(exchange.getResponse()
							.bufferFactory()
							.wrap(json.getBytes())));
		};
	}

}
