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
import com.jorocha.coopervote.resources.AssociadoResource;
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
	
	private static Logger LOG = LoggerFactory.getLogger(AssociadoResource.class);

	@Autowired
	private VotoRepository votoRepository;	
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private PautaService pautaService;	
	
	public List<Voto> findAll() {
		return votoRepository.findAll();
	}	
	
	public Voto findById(String id) {
		Optional<Voto> obj = votoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Voto n�o encontrado"));
	}
	
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

	public void delete(String id) {
		findById(id);
		votoRepository.deleteById(id);
	}

	public Voto update(Voto obj) {
		Voto newObj = findById(obj.getId());
		updateData(newObj, obj);
		return votoRepository.save(newObj);
	}

	private void updateData(Voto data, Voto Voto) {
		data.setIndVoto(Voto.getIndVoto());
		data.setAssociado(Voto.getAssociado());
	}

	public Voto fromDTO(VotoDTO votoDTO) {
		return new Voto(votoDTO.getId(), votoDTO.getIndVoto(), votoDTO.getAssociado());
	}
}
