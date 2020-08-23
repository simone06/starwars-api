package com.b2w.starwars.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( HttpStatus.NOT_FOUND) 
public class SwApiPlanetaResponseNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -6072017373941387042L;

	public SwApiPlanetaResponseNotFoundException(String mensage) {
		super(mensage);
	}

}