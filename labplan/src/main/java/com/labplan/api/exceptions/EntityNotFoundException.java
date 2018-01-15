package com.labplan.api.exceptions;

public class EntityNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -7343683268510879223L;
	
	@Override
	public String getMessage() {
		return "The entity could not be found.";
	}
}
