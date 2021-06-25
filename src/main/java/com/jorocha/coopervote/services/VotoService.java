package com.jorocha.coopervote.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jorocha.coopervote.domain.Associado;
import com.jorocha.coopervote.domain.ItemPauta;
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
	
	@Autowired
	private AssociadoService associadoService;		
	
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
		return obj.orElseThrow(() -> new ObjectNotFoundException("Voto nao encontrado"));
	}
	
	/**
	 * Insere o voto em uma pauta
	 *
	 * @param Voto
	 * @param idPauta
	 * @return Voto
	 */		
	public Voto insert(Voto voto, String idPauta, String idItemPauta) {
		Associado associado = getAssociado(voto);
		verificarVotacaoPauta(idPauta, idItemPauta, associado.getCpf());
		
		verificarTipoVoto(voto);
		
		try {
			String json = restTemplate.getForObject(URL_USER_INFO.concat(associado.getCpf()), String.class);
			UserInfo userInfo = JSONUtils.json2Object(json, UserInfo.class);
			if(userInfo != null && userInfo.isAbleToVote()) {
				return votoRepository.insert(voto);
			}else {
				LOG.error("Associado nao habilitado para votar: ".concat(associado.getCpf()));
				throw new UnableToVoteException();
			}
			
		} catch (Exception e) {
			LOG.error("CPF nao encontrado: ".concat(associado.getCpf()));
			throw new CpfInvalidoException("CPF nao encontrado");
		}
	}

	private Associado getAssociado(Voto voto) {
		Associado associado = associadoService.findById(voto.getIdAssociado());
		return associado;
	}

	public void verificarTipoVoto(Voto voto) {
		if(!voto.getIndVoto().equalsIgnoreCase("Sim") && !voto.getIndVoto().equalsIgnoreCase("nao")) {
			LOG.error("Erro ao registrar voto. Tipo de voto invalido.");
			throw new VotoException("Tipos validos de voto: Sim/nao");
		}
	}

	/**
	 * Valida��es da pauta: existencia, duracao e intervalo para votacao
	 * Valida se o associado ja votou
	 *
	 * @param idPauta
	 * @param idItemPauta
	 * @param cpfAssociado
	 * @return void
	 */	
	private void verificarVotacaoPauta(String idPauta, String idItemPauta, String cpfAssociado) {
		Pauta pauta = pautaService.findById(idPauta);
		if(pauta == null) {
			LOG.error("Pauta nao encontrada: ".concat(idPauta));
			throw new PautaNotFoundException("Pauta nao encontrada");
		}
					
		if(pauta.getDuracaoSessao() == null) {
			LOG.error("duracao da sess�o nao cadastrada: ".concat(idPauta));
			throw new PautaFechadaException("duracao da sessao nao cadastrada");						
		}
		
		if(LocalDateTime.now().isBefore(pauta.getInicioSessao())) throw new PautaFechadaException("votacao nao iniciada");
		if(LocalDateTime.now().isAfter(pauta.getFimSessao())) throw new PautaFechadaException("votacao encerrada");
		
		ItemPauta itemPauta = pauta.getItens().stream().filter(i -> i.getId() == idItemPauta).findFirst().orElse(null);
		
		Voto voto = itemPauta.getVotos().stream().filter(v -> {
			Associado associado = getAssociado(v);
			if(associado.getCpf().equalsIgnoreCase(cpfAssociado)) return Boolean.TRUE;
			else return Boolean.FALSE;
		}).findFirst().orElse(null); 
				
		if(voto != null) {
			LOG.error("Associado ja votou: ".concat(cpfAssociado));
			throw new VotoException("Associado ja votou");
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
		LOG.info(">>> Exclusao do voto: ".concat(id));
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
		LOG.info(">>> Atualizacao do voto: ".concat(obj.getId()));
		return votoRepository.save(newObj);
	}

	/**
	 * Copia os dados atualizados para o objeto Voto
	 *
	 * @param new Voto
	 * @param voto
	 * @return void
	 */		
	private void updateData(Voto newVoto, Voto voto) {
		verificarTipoVoto(voto);
		Associado associado = getAssociado(voto);
		newVoto.setIndVoto(voto.getIndVoto());
		newVoto.setIdAssociado(associado.getId());
	}

	/**
	 * Cria um objeto Voto a partir de um DTO
	 *
	 * @param VotoDTO
	 * @return Voto
	 */	
	public Voto fromDTO(VotoDTO votoDTO) {
		return new Voto(votoDTO.getId(), votoDTO.getIndVoto(), votoDTO.getIdItemPauta(), votoDTO.getIdAssociado());
	}
}
