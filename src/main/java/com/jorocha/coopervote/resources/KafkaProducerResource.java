package com.jorocha.coopervote.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jorocha.coopervote.domain.Associado;
import com.jorocha.coopervote.services.KafKaProducerService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaProducerResource {

	@Autowired
	private KafKaProducerService producerService;

	@Autowired
	public KafkaProducerResource(KafKaProducerService producerService) {
		this.producerService = producerService;
	}

	@ApiOperation(value = "Publicação da mensagem")
	@PostMapping(value = "/publicar")
	public void enviarParaTopic(@RequestParam("mensagem") String message) {
		this.producerService.sendMessage(message);
	}

	@ApiOperation(value = "Criação da mensagem")
	@PostMapping(value = "/criarAssociado")
	public void enviarParaTopic(
			@RequestParam("id") String id, 
			@RequestParam("cpf") String cpf,
			@RequestParam("nome") String nome) {
		
		Associado associado = new Associado();
		associado.setId(id);
		associado.setCpf(cpf);
		associado.setNome(nome);
		this.producerService.saveAssociado(associado);
	}
}
