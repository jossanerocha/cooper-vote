package com.jorocha.coopervote.services.exception;

public class PautaNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public PautaNotFoundException(String message) {
		super(message);
	}
	
}
