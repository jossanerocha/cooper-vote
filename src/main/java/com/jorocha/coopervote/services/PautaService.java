package com.jorocha.coopervote.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jorocha.coopervote.domain.Pauta;
import com.jorocha.coopervote.dto.PautaDTO;
import com.jorocha.coopervote.repository.PautaRepository;
import com.jorocha.coopervote.resources.AssociadoResource;
import com.jorocha.coopervote.services.exception.ObjectNotFoundException;

@Service
public class PautaService {
	
	private static Logger LOG = LoggerFactory.getLogger(AssociadoResource.class);

	@Autowired
	private PautaRepository pautaRepository;
	
	public List<Pauta> findAll() {
		return pautaRepository.findAll();
	}	
	
	public Pauta findById(String id) {
		LOG.info(">>> Busca da pauta: ".concat(id));
		Optional<Pauta> obj = pautaRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Pauta não encontrada"));
	}
	
	public Pauta abrirSessao(String id, Integer numMinutos) {
		LOG.info(">>> Abertura de sessão da pauta: ".concat(id));
		Pauta pauta = findById(id);
		pauta.setDuracaoSessao(numMinutos);
		return update(pauta);
	}
	
	public Pauta insert(Pauta pauta) {
		if(pauta.getDuracaoSessao() == null || pauta.getDuracaoSessao() == 0)
			pauta.setDuracaoSessao(1);
		return pautaRepository.insert(pauta);
	}

	public void delete(String id) {
		findById(id);
		LOG.info(">>> Exclusão da pauta: ".concat(id));
		pautaRepository.deleteById(id);
	}

	public Pauta update(Pauta obj) {
		Pauta newObj = findById(obj.getId());
		updateData(newObj, obj);
		LOG.info(">>> Atualização da pauta: ".concat(obj.getId()));
		return pautaRepository.save(newObj);
	}

	private void updateData(Pauta newPata, Pauta pauta) {
		BeanUtils.copyProperties(pauta, newPata);
	}

	public Pauta fromDTO(PautaDTO pautaDTO) {
		return new Pauta(pautaDTO.getId(), pautaDTO.getData(), pautaDTO.getTitulo(), pautaDTO.getDescricao(), pautaDTO.getDuracaoSessao(), pautaDTO.getInicioSessao(), pautaDTO.getFimSessao(), pautaDTO.getVotos());
	}	
	
	public List<Pauta> findByTitulo(String text) {
		return pautaRepository.findByTitulo(text);
	}
	
	public List<Pauta> findByTituloOrDescricaoAndData(String text, Date minDate, Date maxDate) {
		return pautaRepository.buscarPorTituloOrDescricaoAndData(text, minDate, maxDate);
	}
	
}
