package com.b2w.starwars.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( HttpStatus.CONFLICT) 
public class PlanetaConflictException extends RuntimeException {

	private static final long serialVersionUID = 2671616735426328051L;

	public PlanetaConflictException(String mensage) {
		super(mensage);
	}

}