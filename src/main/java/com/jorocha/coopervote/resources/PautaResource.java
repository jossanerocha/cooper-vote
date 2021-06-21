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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value="/pautas")
public class PautaResource {

	@Autowired
	private PautaService service;
	
	@ApiOperation(value = "Lista de pautas")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna a lista de pautas"),
		    @ApiResponse(code = 403, message = "Sem permiss�o para acessar a lista de pautas"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exce��o"),
	})	
	@RequestMapping(method=RequestMethod.GET)
 	public ResponseEntity<List<PautaDTO>> findAll() {
		List<Pauta> list = service.findAll();
		List<PautaDTO> listDto = list.stream().map(p -> new PautaDTO(p)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}	

	@ApiOperation(value = "Retorna a pauta a partir de um idPauta")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Pauta encontrada"),
		    @ApiResponse(code = 403, message = "Sem permiss�o para acessar a pauta"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exce��o"),
	})
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
 	public ResponseEntity<Pauta> findById(@PathVariable String id) {
		Pauta pauta = service.findById(id);
		return ResponseEntity.ok().body(pauta);
	}
	
	@ApiOperation(value = "Abre a sess�o de uma pauta")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Sess�o aberta"),
		    @ApiResponse(code = 403, message = "Sem permiss�o para abrir a sess�o"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exce��o"),
	})	
	@RequestMapping(value="/abrirSessao", method=RequestMethod.GET)
 	public ResponseEntity<Pauta> abrirSessao(
 			@RequestParam(value = "idPauta" ) String id, 
 			@RequestParam(value = "numMinutos", defaultValue = "1") Integer numMinutos){
		Pauta pauta = service.abrirSessao(id, numMinutos);
		return ResponseEntity.ok().body(pauta);
	}	
	
	@ApiOperation(value = "Insere uma pauta")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Pauta inserida"),
		    @ApiResponse(code = 403, message = "Sem permiss�o para inserir a pauta"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exce��o"),
	})	
	@RequestMapping(method=RequestMethod.POST)
 	public ResponseEntity<Void> insert(@RequestBody PautaDTO pautaDTO) {
		Pauta pauta = service.fromDTO(pautaDTO);
		pauta = service.insert(pauta);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pauta.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@ApiOperation(value = "Deleta uma pauta")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Pauta deletada"),
		    @ApiResponse(code = 403, message = "Sem permiss�o para deletar a pauta"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exce��o"),
	})	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
 	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "Atualiza os dados de uma pauta")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Pauta atualizada"),
		    @ApiResponse(code = 403, message = "Sem permiss�o para atualizar a pauta"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exce��o"),
	})
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
 	public ResponseEntity<Void> update(@RequestBody PautaDTO pautaDTO, @PathVariable String idPauta) {
		Pauta pauta = service.fromDTO(pautaDTO);
		pauta.setId(idPauta);
		pauta = service.update(pauta);
		return ResponseEntity.noContent().build();
	}	
	
	@ApiOperation(value = "Busca uma pauta por termo contido no t�tulo")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Pauta encontrada"),
		    @ApiResponse(code = 403, message = "Sem permiss�o efetuar a busca"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exce��o"),
	})
	@RequestMapping(value="/findByTitle", method=RequestMethod.GET)
 	public ResponseEntity<List<Pauta>> findByTitle(@RequestParam(value="titulo", defaultValue="") String text) {
		text = URL.decode(text);
		List<Pauta> list = service.findByTitulo(text);
		return ResponseEntity.ok().body(list);
	}

	@ApiOperation(value = "Busca uma pauta por termo contido no t�tulo ou descri��o e entre um periodo de datas")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Pauta encontrada"),
		    @ApiResponse(code = 403, message = "Sem permiss�o efetuar a busca"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exce��o"),
	})
	@RequestMapping(value = "/findByTextoAndData", method = RequestMethod.GET)
	public ResponseEntity<List<Pauta>> findByTextoAndData(
			@RequestParam(value = "termoBusca", defaultValue = "") String text,
			@RequestParam(value = "minDate", defaultValue = "") String minDate,
			@RequestParam(value = "maxDate", defaultValue = "") String maxDate) throws ParseException {
		text = URL.decode(text);
		Date min = Data.stringToDate(minDate);
		Date max = Data.stringToDate(maxDate);
		List<Pauta> list = service.findByTituloOrDescricaoAndData(text, min, max);
		return ResponseEntity.ok().body(list);
	}
}
