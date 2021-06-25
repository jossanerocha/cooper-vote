package com.jorocha.coopervote.services;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jorocha.coopervote.config.kafka.KafKaProducerService;
import com.jorocha.coopervote.domain.Pauta;
import com.jorocha.coopervote.dto.PautaDTO;
import com.jorocha.coopervote.repository.PautaRepository;
import com.jorocha.coopervote.services.exception.ObjectNotFoundException;

@Service
public class PautaService {
	
	private static Logger LOG = LoggerFactory.getLogger(PautaService.class);

	@Autowired
	private PautaRepository pautaRepository;
	
	@Autowired
	private KafKaProducerService producerService;	
	
	/**
	 * Lista de pautas
	 *
	 * @param 
	 * @return List<Pauta>
	 */
	public List<Pauta> findAll() {
		return pautaRepository.findAll();
	}	
	
	/**
	 * Busca uma pauta
	 *
	 * @param idPauta
	 * @return Pauta
	 */	
	public Pauta findById(String id) {
		LOG.info(">>> Busca da pauta: ".concat(id));
		Optional<Pauta> obj = pautaRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Pauta não encontrada"));
	}
	
	/**
	 * Abertura da sessão de votação de uma pauta
	 * Duração mínima de uma sessão: 1 minuto
	 *
	 * @param idPauta
	 * @param numMinutos
	 * @return Pauta
	 */	
	public Pauta abrirSessao(String id, Integer numMinutos) {
		LOG.info(">>> Abertura de sessão da pauta: ".concat(id));
		Pauta pauta = findById(id);
		pauta.setDuracaoSessao(numMinutos);
		return update(pauta);
	}
	
	/**
	 * Fechamento da sessão de votação de uma pauta
	 *
	 * @param idPauta
	 * @return Pauta
	 */	
	public Pauta fecharSessao(String id) {
		LOG.info(">>> Fechamento de sessão da pauta: ".concat(id));
		Pauta pauta = findById(id);
		pauta.setFimSessao(LocalDateTime.now());
		pauta = pautaRepository.save(pauta);
		producerService.fecharSessao(pauta);
		return pauta;
	}	
	
	/**
	 * Insere uma nova pauta
	 *
	 * @param Pauta
	 * @return Pauta
	 */	
	public Pauta insert(Pauta pauta) {
		if(pauta.getDuracaoSessao() == null || pauta.getDuracaoSessao() == 0)
			pauta.setDuracaoSessao(1);
		return pautaRepository.insert(pauta);
	}

	/**
	 * Exclui uma pauta
	 *
	 * @param idPauta
	 * @return void
	 */	
	public void delete(String id) {
		findById(id);
		LOG.info(">>> Exclusão da pauta: ".concat(id));
		pautaRepository.deleteById(id);
	}

	/**
	 * Atualiza os dados de uma pauta
	 *
	 * @param Pauta
	 * @return Pauta
	 */	
	public Pauta update(Pauta obj) {
		Pauta newObj = findById(obj.getId());
		updateData(newObj, obj);
		LOG.info(">>> Atualização da pauta: ".concat(obj.getId()));
		return pautaRepository.save(newObj);
	}

	/**
	 * Copia os dados atualizados para o objeto Pauta
	 *
	 * @param new Pauta
	 * @param Pauta
	 * @return void
	 * @see BeanUtils
	 */		
	private void updateData(Pauta newPauta, Pauta pauta) {
		BeanUtils.copyProperties(pauta, newPauta);
	}

	/**
	 * Cria um objeto Pauta a partir de um DTO
	 *
	 * @param PautaDTO
	 * @return Pauta
	 */	
	public Pauta fromDTO(PautaDTO pautaDTO) {
		return new Pauta(pautaDTO.getId(), pautaDTO.getData(), pautaDTO.getTitulo(), pautaDTO.getDescricao(), pautaDTO.getDuracaoSessao(), pautaDTO.getInicioSessao(), pautaDTO.getFimSessao(), pautaDTO.getItens());
	}	
	
	/**
	 * Lista pautas por termo contido no título
	 *
	 * @param text
	 * @return List<Pauta>
	 */	
	public List<Pauta> findByTitulo(String text) {
		return pautaRepository.findByTitulo(text);
	}
	
	/**
	 * Lista pautas por termo contido no título ou descrição e entre um periodo de data
	 *
	 * @param text
	 * @param minDate
	 * @param maxDate
	 * @return List<Pauta>
	 */	
	public List<Pauta> findByTituloOrDescricaoAndData(String text, Date minDate, Date maxDate) {
		return pautaRepository.buscarPorTituloOrDescricaoAndData(text, minDate, maxDate);
	}
	
}
