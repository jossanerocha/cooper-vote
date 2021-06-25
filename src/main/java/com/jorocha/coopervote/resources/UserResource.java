package com.jorocha.coopervote.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jorocha.coopervote.domain.User;
import com.jorocha.coopervote.dto.UserDTO;
import com.jorocha.coopervote.services.UserService;
import com.mongodb.lang.NonNull;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
	
	@Autowired
	private UserService service;

	@ApiOperation(value = "Retorna uma lista de usuários")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna a lista de usuários"),
		    @ApiResponse(code = 403, message = "Sem permissão para acessar a lista de usuários"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})	
	@RequestMapping(method=RequestMethod.GET)
 	public ResponseEntity<List<UserDTO>> findAll() {
		List<User> list = service.findAll();
		List<UserDTO> listDto = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@ApiOperation(value = "Retorna uma usuário")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna uma usuário"),
		    @ApiResponse(code = 403, message = "Sem permissão para acessar a lista de usuários"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})	
	@RequestMapping(value="/logar", method=RequestMethod.GET)
 	public ResponseEntity<UserDTO> logar(
 			@Valid @NonNull @RequestParam(value = "username" ) String username, 
 			@Valid @NonNull @RequestParam(value = "password") String password){		
		return ResponseEntity.ok().body(service.logar(username, password));
	}	

}