package com.jorocha.coopervote.services.exception;

public class UnableToVoteException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public UnableToVoteException() {
		super("Associado sem permissao para votar");
	}
	
}
