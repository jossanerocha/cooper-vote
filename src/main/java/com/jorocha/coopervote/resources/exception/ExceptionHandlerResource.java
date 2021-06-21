package com.jorocha.coopervote.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jorocha.coopervote.services.exception.CpfInvalidoException;
import com.jorocha.coopervote.services.exception.ObjectNotFoundException;
import com.jorocha.coopervote.services.exception.PautaFechadaException;
import com.jorocha.coopervote.services.exception.PautaNotFoundException;
import com.jorocha.coopervote.services.exception.UnableToVoteException;

@ControllerAdvice
public class ExceptionHandlerResource {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<Error> objectNotFound(ObjectNotFoundException objNotFound, HttpServletRequest request) {		
		HttpStatus status = HttpStatus.NOT_FOUND;
		Error err = new Error(System.currentTimeMillis(), status.value(), "Não encontrado", objNotFound.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(CpfInvalidoException.class)
	public ResponseEntity<Error> cpfInvalidoException(CpfInvalidoException cpfInvalido, HttpServletRequest request) {		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Error err = new Error(System.currentTimeMillis(), status.value(), cpfInvalido.getMessage(), "Dígito verificador inválido", request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(UnableToVoteException.class)
	public ResponseEntity<Error> cpfInvalidoException(UnableToVoteException unableToVote, HttpServletRequest request) {		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Error err = new Error(System.currentTimeMillis(), status.value(), "CPF sem permissão", unableToVote.getMessage(),request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(PautaNotFoundException.class)
	public ResponseEntity<Error> pautaNotFound(PautaNotFoundException objNotFound, HttpServletRequest request) {		
		HttpStatus status = HttpStatus.NOT_FOUND;
		Error err = new Error(System.currentTimeMillis(), status.value(), "Não encontrado", objNotFound.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(PautaFechadaException.class)
	public ResponseEntity<Error> pautaFechada(PautaFechadaException pautaFechada, HttpServletRequest request) {		
		HttpStatus status = HttpStatus.FORBIDDEN;
		Error err = new Error(System.currentTimeMillis(), status.value(), "Pauta fechada", pautaFechada.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}	
}
