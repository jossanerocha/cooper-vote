package com.jorocha.coopervote.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.jorocha.coopervote.domain.Associado;
@Service
public class KafKaConsumerService {
	private static Logger LOG = LoggerFactory.getLogger(KafKaConsumerService.class);

	@KafkaListener(topics = "${general.topic.name}", groupId = "${general.topic.group.id}")
	public void consume(String message) {
		LOG.info(String.format("Mensagem recebida -> %s", message));
	}

	@KafkaListener(topics = "${associado.topic.name}", groupId = "${associado.topic.group.id}", containerFactory = "associadoKafkaListenerContainerFactory")
	public void consume(Associado associado) {
		LOG.info(String.format("Associado criado -> %s %s", associado.getNome(), associado.getCpf()));
	}
}
