package com.jorocha.coopervote.resources;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jorocha.coopervote.domain.Associado;
import com.jorocha.coopervote.dto.AssociadoDTO;
import com.jorocha.coopervote.services.AssociadoService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value="/associados")
public class AssociadoResource {
	
	@Autowired
	private AssociadoService service;
	
	@ApiOperation(value = "Retorna uma lista de associados")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna a lista de associados"),
		    @ApiResponse(code = 403, message = "Sem permissão para acessar a lista de associados"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})	
	@RequestMapping(method=RequestMethod.GET)
 	public ResponseEntity<List<AssociadoDTO>> findAll() {
		List<Associado> list = service.findAll();
		List<AssociadoDTO> listDto = list.stream().map(x -> new AssociadoDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	@ApiOperation(value = "Retorna um associado a partir de um idAssociado")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Pauta encontrada"),
		    @ApiResponse(code = 403, message = "Sem permissão para acessar a pauta"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})	
	@GetMapping(value = "/{id}")
 	public ResponseEntity<AssociadoDTO> findById(@PathVariable String id) {
		Associado obj = service.findById(id);
		return ResponseEntity.ok().body(new AssociadoDTO(obj));
	}

	@ApiOperation(value = "Insere um associado")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Associado inserido"),
		    @ApiResponse(code = 403, message = "Sem permissão para inserir um associado"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})		
	@RequestMapping(method=RequestMethod.POST)
 	public ResponseEntity<Void> insert(@RequestBody AssociadoDTO associadoDTO) throws OAuthSystemException, OAuthProblemException, IOException {
		Associado associado = service.fromDTO(associadoDTO);
		associado = service.insert(associado);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(associado.getId()).toUri();
		return ResponseEntity.created(uri).build();			
	}

	@ApiOperation(value = "Deleta um associado")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Associado deletado"),
		    @ApiResponse(code = 403, message = "Sem permissão para deletar um associado"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
 	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "Atualiza os dados de um associado")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Associado atualizado"),
		    @ApiResponse(code = 403, message = "Sem permissão para atualizar um associado"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
 	public ResponseEntity<Void> update(@RequestBody AssociadoDTO associadoDTO, @PathVariable String idAssociado) throws OAuthSystemException, OAuthProblemException, IOException {
		Associado obj = service.fromDTO(associadoDTO);
		obj.setId(idAssociado);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
}
