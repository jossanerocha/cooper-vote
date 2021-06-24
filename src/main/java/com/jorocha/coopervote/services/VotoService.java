package com.jorocha.coopervote.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jorocha.coopervote.domain.Pauta;
import com.jorocha.coopervote.domain.Voto;
import com.jorocha.coopervote.dto.UserInfo;
import com.jorocha.coopervote.dto.VotoDTO;
import com.jorocha.coopervote.repository.VotoRepository;
import com.jorocha.coopervote.resources.util.JSONUtils;
import com.jorocha.coopervote.services.exception.CpfInvalidoException;
import com.jorocha.coopervote.services.exception.ObjectNotFoundException;
import com.jorocha.coopervote.services.exception.PautaFechadaException;
import com.jorocha.coopervote.services.exception.PautaNotFoundException;
import com.jorocha.coopervote.services.exception.UnableToVoteException;
import com.jorocha.coopervote.services.exception.VotoException;

@Service
public class VotoService {
	
	private static final String URL_USER_INFO = "https://user-info.herokuapp.com/users/";
	
	private static Logger LOG = LoggerFactory.getLogger(VotoService.class);

	@Autowired
	private VotoRepository votoRepository;	
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private PautaService pautaService;	
	
	/**
	 * Lista os votos de todas as pautas
	 *
	 * @param 
	 * @return List<Voto>
	 */	
	public List<Voto> findAll() {
		return votoRepository.findAll();
	}	
	
	/**
	 * Busca um voto
	 *
	 * @param idVoto
	 * @return Voto
	 */		
	public Voto findById(String id) {
		Optional<Voto> obj = votoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Voto n�o encontrado"));
	}
	
	/**
	 * Insere o voto em uma pauta
	 *
	 * @param Voto
	 * @param idPauta
	 * @return Voto
	 */		
	public Voto insert(Voto voto, String idPauta) {
		verificarVotacaoPauta(idPauta, voto.getAssociado().getCpf());
		
		if(!voto.getIndVoto().equalsIgnoreCase("Sim") || !voto.getIndVoto().equalsIgnoreCase("Sim")) {
			LOG.error("Erro ao registrar voto. Tipo de voto inv�lido.");
			throw new VotoException("Tipos v�lidos de voto: Sim/N�o");
		}
		
		try {
			String json = restTemplate.getForObject(URL_USER_INFO.concat(voto.getAssociado().getCpf()), String.class);
			UserInfo userInfo = JSONUtils.json2Object(json, UserInfo.class);
			if(userInfo != null && userInfo.isAbleToVote()) {
				return votoRepository.insert(voto);
			}else {
				LOG.error("Associado n�o habilitado para votar: ".concat(voto.getAssociado().getCpf()));
				throw new UnableToVoteException();
			}
			
		} catch (Exception e) {
			LOG.error("CPF n�o encontrado: ".concat(voto.getAssociado().getCpf()));
			throw new CpfInvalidoException("CPF n�o encontrado");
		}
	}

	/**
	 * Valida��es da pauta: exist�ncia, dura��o e intervalo para vota��o
	 * Valida se o associado j� votou
	 *
	 * @param idPauta
	 * @param cpfAssociado
	 * @return void
	 */	
	private void verificarVotacaoPauta(String idPauta, String cpfAssociado) {
		Pauta pauta = pautaService.findById(idPauta);
		if(pauta == null) {
			LOG.error("Pauta n�o encontrada: ".concat(idPauta));
			throw new PautaNotFoundException("Pauta n�o encontrada");
		}
					
		if(pauta.getDuracaoSessao() == null) {
			LOG.error("Dura��o da sess�o n�o cadastrada: ".concat(idPauta));
			throw new PautaFechadaException("Dura��o da sess�o n�o cadastrada");						
		}
		
		if(LocalDateTime.now().isBefore(pauta.getInicioSessao())) throw new PautaFechadaException("Vota��o n�o iniciada");
		if(LocalDateTime.now().isAfter(pauta.getFimSessao())) throw new PautaFechadaException("Vota��o encerrada");
		
		Voto voto = pauta.getVotos().stream().filter(v -> v.getAssociado().getCpf().equalsIgnoreCase(cpfAssociado)).findFirst().orElse(null);
		if(voto != null) {
			LOG.error("Associado j� votou: ".concat(cpfAssociado));
			throw new VotoException("Associado j� votou");
		}
	}

	/**
	 * Deleta o voto de uma pauta
	 *
	 * @param idVoto
	 * @return void
	 */		
	public void delete(String id) {
		findById(id);
		LOG.info(">>> Exclus�o do voto: ".concat(id));
		votoRepository.deleteById(id);
	}

	/**
	 * Atualiza o voto de uma pauta
	 *
	 * @param idVoto
	 * @return Voto
	 */		
	public Voto update(Voto obj) {
		Voto newObj = findById(obj.getId());
		updateData(newObj, obj);
		LOG.info(">>> Atualiza��o do voto: ".concat(obj.getId()));
		return votoRepository.save(newObj);
	}

	/**
	 * Copia os dados atualizados para o objeto Voto
	 *
	 * @param new Voto
	 * @param Voto
	 * @return void
	 */		
	private void updateData(Voto newVoto, Voto Voto) {
		newVoto.setIndVoto(Voto.getIndVoto());
		newVoto.setAssociado(Voto.getAssociado());
	}

	/**
	 * Cria um objeto Voto a partir de um DTO
	 *
	 * @param VotoDTO
	 * @return Voto
	 */	
	public Voto fromDTO(VotoDTO votoDTO) {
		return new Voto(votoDTO.getId(), votoDTO.getIndVoto(), votoDTO.getAssociado());
	}
}
