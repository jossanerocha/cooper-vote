package com.jorocha.coopervote.resources;

import java.net.URI;
import java.text.ParseException;
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

import com.jorocha.coopervote.domain.ItemPauta;
import com.jorocha.coopervote.dto.ItemPautaDTO;
import com.jorocha.coopervote.resources.util.URL;
import com.jorocha.coopervote.services.ItemPautaService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value="/itensItemPauta")
public class ItemPautaResource {

	@Autowired
	private ItemPautaService service;
	
	@ApiOperation(value = "Lista de itens das Pautas")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna a lista de itens das pautas"),
		    @ApiResponse(code = 403, message = "Sem permissão para acessar a lista de itens das pautas"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})	
	@RequestMapping(method=RequestMethod.GET)
 	public ResponseEntity<List<ItemPautaDTO>> findAll() {
		List<ItemPauta> list = service.findAll();
		List<ItemPautaDTO> listDto = list.stream().map(p -> new ItemPautaDTO(p)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}	

	@ApiOperation(value = "Retorna o ItemPauta a partir de um idItemPauta")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "ItemPauta encontrada"),
		    @ApiResponse(code = 403, message = "Sem permissão para acessar a ItemPauta"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
 	public ResponseEntity<ItemPauta> findById(@PathVariable String id) {
		ItemPauta ItemPauta = service.findById(id);
		return ResponseEntity.ok().body(ItemPauta);
	}
	
	@ApiOperation(value = "Insere um ItemPauta")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "ItemPauta inserido"),
		    @ApiResponse(code = 403, message = "Sem permissão para inserir o ItemPauta"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})	
	@RequestMapping(method=RequestMethod.POST)
 	public ResponseEntity<Void> insert(@RequestBody ItemPautaDTO ItemPautaDTO) {
		ItemPauta ItemPauta = service.fromDTO(ItemPautaDTO);
		ItemPauta = service.insert(ItemPauta);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(ItemPauta.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@ApiOperation(value = "Deleta um ItemPauta")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "ItemPauta deletado"),
		    @ApiResponse(code = 403, message = "Sem permissão para deletar o ItemPauta"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
 	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "Atualiza os dados de um ItemPauta")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "ItemPauta atualizado"),
		    @ApiResponse(code = 403, message = "Sem permissão para atualizar o ItemPauta"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
 	public ResponseEntity<Void> update(@RequestBody ItemPautaDTO ItemPautaDTO, @PathVariable String idItemPauta) {
		ItemPauta ItemPauta = service.fromDTO(ItemPautaDTO);
		ItemPauta.setId(idItemPauta);
		ItemPauta = service.update(ItemPauta);
		return ResponseEntity.noContent().build();
	}	
	
	@ApiOperation(value = "Lista ItemPauta por termo contido no título")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "ItemPauta encontrado"),
		    @ApiResponse(code = 403, message = "Sem permissão efetuar a busca"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})
	@RequestMapping(value="/findByTitle", method=RequestMethod.GET)
 	public ResponseEntity<List<ItemPauta>> findByTitle(@RequestParam(value="titulo", defaultValue="") String text) {
		text = URL.decode(text);
		List<ItemPauta> list = service.findByTitulo(text);
		return ResponseEntity.ok().body(list);
	}

	@ApiOperation(value = "Lista ItemPauta por termo contido no título ou descrição e entre um periodo de datas")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "ItemPauta encontrado"),
		    @ApiResponse(code = 403, message = "Sem permissão efetuar a busca"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})
	@RequestMapping(value = "/findByTextoAndData", method = RequestMethod.GET)
	public ResponseEntity<List<ItemPauta>> findByTextoAndData(@RequestParam(value = "termoBusca", defaultValue = "") String text) throws ParseException {
		text = URL.decode(text);
		List<ItemPauta> list = service.findByTituloOrDescricao(text);
		return ResponseEntity.ok().body(list);
	}
}
