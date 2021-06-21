package com.jorocha.coopervote.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jorocha.coopervote.domain.Voto;
import com.jorocha.coopervote.dto.VotoDTO;
import com.jorocha.coopervote.services.VotoService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value="/votos")
public class VotoResource {

	@Autowired
	private VotoService service;
	
	@ApiOperation(value = "Lista os votos de todas as pautas")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna uma lista de votos"),
		    @ApiResponse(code = 403, message = "Sem permissão para acessar a lista de votos"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})	
	@RequestMapping(method=RequestMethod.GET)
 	public ResponseEntity<List<VotoDTO>> findAll() {
		List<Voto> list = service.findAll();
		List<VotoDTO> listDto = list.stream().map(v -> new VotoDTO(v)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}	
	
	@ApiOperation(value = "Retorna o voto a partir de um idVoto")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Voto encontrado"),
		    @ApiResponse(code = 403, message = "Sem permissão para acessar o voto"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
 	public ResponseEntity<Voto> findById(@PathVariable String id) {
		Voto obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@ApiOperation(value = "Insere o voto em uma pauta")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Voto inserido"),
		    @ApiResponse(code = 403, message = "Sem permissão para inserir o voto"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})		
	@RequestMapping(method=RequestMethod.POST)
 	public ResponseEntity<Void> insert(@NonNull @NotEmpty @RequestParam(value = "idPauta") String idPauta, @NonNull @RequestBody VotoDTO votoDTO) {
		Voto voto = service.fromDTO(votoDTO);
		voto = service.insert(voto, idPauta);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(voto.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@ApiOperation(value = "Deleta o voto de uma pauta")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Voto deletado"),
		    @ApiResponse(code = 403, message = "Sem permissão para deletar o voto"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
 	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "Atualiza o voto de uma pauta")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Voto atualizado"),
		    @ApiResponse(code = 403, message = "Sem permissão para atualizar o voto"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
 	public ResponseEntity<Void> update(@RequestBody VotoDTO votoDTO, @PathVariable String idVoto) {
		Voto obj = service.fromDTO(votoDTO);
		obj.setId(idVoto);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}	
	
}
