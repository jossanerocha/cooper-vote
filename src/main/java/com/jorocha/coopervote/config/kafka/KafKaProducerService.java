package com.jorocha.coopervote.config.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.jorocha.coopervote.domain.Associado;
import com.jorocha.coopervote.domain.Pauta;

@Service
public class KafKaProducerService {
	
	private static Logger LOG = LoggerFactory.getLogger(KafKaProducerService.class);

	@Value(value = "${general.topic.name}")
	private String topicName;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Value(value = "${associado.topic.name}")
	private String associadoTopicName;
	
	@Value(value = "${sessao.topic.name}")
	private String sessaoTopicName;	

	@Autowired
	private KafkaTemplate<String, Associado> associadoKafkaTemplate;
	
	@Autowired
	private KafkaTemplate<String, Pauta> sessaoKafkaTemplate;	

	public void sendMessage(String message) {
		ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(topicName, message);

		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
			@Override
			public void onSuccess(SendResult<String, String> result) {
				LOG.info("Mensagem enviada: " + message + " offset: " + result.getRecordMetadata().offset());
			}

			@Override
			public void onFailure(Throwable ex) {
				LOG.error("Falha ao enviar a mensagem : " + message, ex);
			}
		});
	}

	public void saveAssociado(Associado associado) {
		ListenableFuture<SendResult<String, Associado>> future = this.associadoKafkaTemplate.send(associadoTopicName, associado);

		future.addCallback(new ListenableFutureCallback<SendResult<String, Associado>>() {
			@Override
			public void onSuccess(SendResult<String, Associado> result) {
				LOG.info("Associado criado: " + associado + " offset: " + result.getRecordMetadata().offset());
			}

			@Override
			public void onFailure(Throwable ex) {
				LOG.error("Falha ao criar associado : " + associado, ex);
			}
		});
	}
	
	public void fecharSessao(Pauta pauta) {
		ListenableFuture<SendResult<String, Pauta>> future = this.sessaoKafkaTemplate.send(sessaoTopicName, pauta);

		future.addCallback(new ListenableFutureCallback<SendResult<String, Pauta>>() {
			@Override
			public void onSuccess(SendResult<String, Pauta> result) {
				LOG.info("Sessao fechada: " + pauta + " offset: " + result.getRecordMetadata().offset());
			}

			@Override
			public void onFailure(Throwable ex) {
				LOG.error("Falha ao fechar sessao : " + pauta, ex);
			}
		});
	}	

}
