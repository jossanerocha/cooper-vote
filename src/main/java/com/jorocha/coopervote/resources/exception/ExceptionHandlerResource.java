package com.jorocha.coopervote.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jorocha.coopervote.services.exception.AssociadoException;
import com.jorocha.coopervote.services.exception.CpfInvalidoException;
import com.jorocha.coopervote.services.exception.LoginException;
import com.jorocha.coopervote.services.exception.ObjectNotFoundException;
import com.jorocha.coopervote.services.exception.PautaFechadaException;
import com.jorocha.coopervote.services.exception.PautaNotFoundException;
import com.jorocha.coopervote.services.exception.UnableToVoteException;
import com.jorocha.coopervote.services.exception.VotoException;

@ControllerAdvice
public class ExceptionHandlerResource {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<Error> objectNotFound(ObjectNotFoundException objNotFound, HttpServletRequest request) {		
		HttpStatus status = HttpStatus.NOT_FOUND;
		Error err = new Error(System.currentTimeMillis(), status.value(), "nao encontrado", objNotFound.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(CpfInvalidoException.class)
	public ResponseEntity<Error> cpfInvalidoException(CpfInvalidoException cpfInvalido, HttpServletRequest request) {		
		HttpStatus status = HttpStatus.NOT_FOUND;
		Error err = new Error(System.currentTimeMillis(), status.value(), "nao encontrado", cpfInvalido.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(UnableToVoteException.class)
	public ResponseEntity<Error> cpfInvalidoException(UnableToVoteException unableToVote, HttpServletRequest request) {		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Error err = new Error(System.currentTimeMillis(), status.value(), "CPF sem permiss�o", unableToVote.getMessage(),request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(PautaNotFoundException.class)
	public ResponseEntity<Error> pautaNotFound(PautaNotFoundException objNotFound, HttpServletRequest request) {		
		HttpStatus status = HttpStatus.NOT_FOUND;
		Error err = new Error(System.currentTimeMillis(), status.value(), "nao encontrado", objNotFound.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(PautaFechadaException.class)
	public ResponseEntity<Error> pautaFechada(PautaFechadaException pautaFechada, HttpServletRequest request) {		
		HttpStatus status = HttpStatus.FORBIDDEN;
		Error err = new Error(System.currentTimeMillis(), status.value(), "Pauta fechada", pautaFechada.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}	
	
	@ExceptionHandler(VotoException.class)
	public ResponseEntity<Error> pautaFechada(VotoException votoInvalido, HttpServletRequest request) {		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Error err = new Error(System.currentTimeMillis(), status.value(), "Voto invalido", votoInvalido.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}	
	
	@ExceptionHandler(AssociadoException.class)
	public ResponseEntity<Error> associadoNaoCasdastrado(AssociadoException associado, HttpServletRequest request) {		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Error err = new Error(System.currentTimeMillis(), status.value(), "Associado nao cadastrado", associado.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}	
	
	@ExceptionHandler(LoginException.class)
	public ResponseEntity<Error> login(LoginException login, HttpServletRequest request) {		
		HttpStatus status = HttpStatus.NOT_FOUND;
		Error err = new Error(System.currentTimeMillis(), status.value(), "Falha no login", login.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}		
}
