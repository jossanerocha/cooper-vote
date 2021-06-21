package com.jorocha.coopervote.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

@Service
public class VotoService {
	
	private static final String URL_USER_INFO = "https://user-info.herokuapp.com/users/";

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
		return obj.orElseThrow(() -> new ObjectNotFoundException("Voto não encontrado"));
	}
	
	public Voto insert(Voto voto, String idPauta) {
		verificarStatusVotacaoPauta(idPauta);
		try {
			String json = restTemplate.getForObject(URL_USER_INFO.concat(voto.getAssociado().getCpf()), String.class);
			UserInfo userInfo = JSONUtils.json2Object(json, UserInfo.class);
			if(userInfo != null && userInfo.isAbleToVote())
				return votoRepository.insert(voto);
			else
				throw new UnableToVoteException();
			
		} catch (Exception e) {
			throw new CpfInvalidoException();
		}
	}

	private void verificarStatusVotacaoPauta(String idPauta) {
		Pauta pauta = pautaService.findById(idPauta);		
		if(pauta == null) throw new PautaNotFoundException("Pauta não encontrada");			
		if(pauta.getDuracaoSessao() == null) throw new PautaFechadaException("Duração da sessão não cadastrada");						
		if(LocalDateTime.now().isBefore(pauta.getInicioSessao())) throw new PautaFechadaException("Votação não iniciada");
		if(LocalDateTime.now().isAfter(pauta.getFimSessao())) throw new PautaFechadaException("Votação encerrada");
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
