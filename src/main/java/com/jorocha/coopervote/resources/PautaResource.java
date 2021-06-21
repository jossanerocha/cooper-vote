package com.jorocha.coopervote.resources;

import java.net.URI;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jorocha.coopervote.domain.Pauta;
import com.jorocha.coopervote.dto.PautaDTO;
import com.jorocha.coopervote.resources.util.Data;
import com.jorocha.coopervote.resources.util.URL;
import com.jorocha.coopervote.services.PautaService;

@RestController
@RequestMapping(value="/pautas")
public class PautaResource {

	@Autowired
	private PautaService service;
	
	@RequestMapping(method=RequestMethod.GET)
 	public ResponseEntity<List<PautaDTO>> findAll() {
		List<Pauta> list = service.findAll();
		List<PautaDTO> listDto = list.stream().map(p -> new PautaDTO(p)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}	

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
 	public ResponseEntity<Pauta> findById(@PathVariable String id) {
		Pauta pauta = service.findById(id);
		return ResponseEntity.ok().body(pauta);
	}
	
	@RequestMapping(value="/abrirSessao", method=RequestMethod.GET)
 	public ResponseEntity<Pauta> abrirSessao(
 			@RequestParam(value = "id" ) String id, 
 			@RequestParam(value = "numMinutos", defaultValue = "1") Integer numMinutos){
		Pauta pauta = service.abrirSessao(id, numMinutos);
		return ResponseEntity.ok().body(pauta);
	}	
	
	@RequestMapping(method=RequestMethod.POST)
 	public ResponseEntity<Void> insert(@RequestBody PautaDTO pautaDTO) {
		Pauta pauta = service.fromDTO(pautaDTO);
		pauta = service.insert(pauta);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pauta.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
 	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
 	public ResponseEntity<Void> update(@RequestBody PautaDTO objDto, @PathVariable String id) {
		Pauta pauta = service.fromDTO(objDto);
		pauta.setId(id);
		pauta = service.update(pauta);
		return ResponseEntity.noContent().build();
	}	
	
	@RequestMapping(value="/findByTitle", method=RequestMethod.GET)
 	public ResponseEntity<List<Pauta>> findByTitle(@RequestParam(value="titulo", defaultValue="") String text) {
		text = URL.decode(text);
		List<Pauta> list = service.findByTitulo(text);
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(value = "/findByTextoAndData", method = RequestMethod.GET)
	public ResponseEntity<List<Pauta>> findByTextoAndData(
			@RequestParam(value = "text", defaultValue = "") String text,
			@RequestParam(value = "minDate", defaultValue = "") String minDate,
			@RequestParam(value = "maxDate", defaultValue = "") String maxDate) throws ParseException {
		text = URL.decode(text);
		Date min = Data.stringToDate(minDate);
		Date max = Data.stringToDate(maxDate);
		List<Pauta> list = service.findByTituloOrDescricaoAndData(text, min, max);
		return ResponseEntity.ok().body(list);
	}
}
