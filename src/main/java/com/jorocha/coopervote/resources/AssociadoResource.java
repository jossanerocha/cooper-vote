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

@RestController
@RequestMapping(value="/associados")
public class AssociadoResource {
	
	@Autowired
	private AssociadoService service;
	
	@RequestMapping(method=RequestMethod.GET)
 	public ResponseEntity<List<AssociadoDTO>> findAll() {
		List<Associado> list = service.findAll();
		List<AssociadoDTO> listDto = list.stream().map(x -> new AssociadoDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	@GetMapping(value = "/{id}")
 	public ResponseEntity<AssociadoDTO> findById(@PathVariable String id) {
		Associado obj = service.findById(id);
		return ResponseEntity.ok().body(new AssociadoDTO(obj));
	}

	@RequestMapping(method=RequestMethod.POST)
 	public ResponseEntity<Void> insert(@RequestBody AssociadoDTO associadoDTO) throws OAuthSystemException, OAuthProblemException, IOException {
		Associado associado = service.fromDTO(associadoDTO);
		associado = service.insert(associado);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(associado.getId()).toUri();
		return ResponseEntity.created(uri).build();			
	}

	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
 	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
 	public ResponseEntity<Void> update(@RequestBody AssociadoDTO objDto, @PathVariable String id) throws OAuthSystemException, OAuthProblemException, IOException {
		Associado obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
}
