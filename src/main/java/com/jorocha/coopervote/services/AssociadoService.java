package com.jorocha.coopervote.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jorocha.coopervote.config.ClienteApiCpfLight;
import com.jorocha.coopervote.domain.Associado;
import com.jorocha.coopervote.dto.AssociadoDTO;
import com.jorocha.coopervote.dto.UsuarioCpfDTO;
import com.jorocha.coopervote.repository.AssociadoRepository;
import com.jorocha.coopervote.resources.AssociadoResource;
import com.jorocha.coopervote.resources.util.JSONUtils;
import com.jorocha.coopervote.services.exception.AssociadoException;
import com.jorocha.coopervote.services.exception.CpfInvalidoException;
import com.jorocha.coopervote.services.exception.ObjectNotFoundException;

@Service
public class AssociadoService {
	
	private static Logger LOG = LoggerFactory.getLogger(AssociadoResource.class);
	
	@Autowired
	private AssociadoRepository associadoRepository;

	public List<Associado> findAll() {
		return associadoRepository.findAll();
	}

	public Associado findById(String id) {
		Optional<Associado> obj = associadoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Associado não encontrado"));
	}

	public Associado insert(Associado associado) throws OAuthSystemException, OAuthProblemException, IOException {
		AssociadoDTO associadoDTO = new AssociadoDTO();
		BeanUtils.copyProperties(associado, associadoDTO);
		validarCpf(associadoDTO);
		
		Associado associadoCadastrado = associadoRepository.findByCpf(associado.getCpf());
		if(associadoCadastrado != null) {
			LOG.error("Erro ao cadastrar o CPF: ".concat(associadoDTO.getCpf()));
			throw new AssociadoException("CPF já cadastrado");
		}
		
		return associadoRepository.insert(associado);
	}

	public void delete(String id) {
		findById(id);
		associadoRepository.deleteById(id);
	}

	public Associado update(Associado associado) throws OAuthSystemException, OAuthProblemException, IOException {
		Associado newObj = findById(associado.getId());
		
		AssociadoDTO associadoDTO = new AssociadoDTO();
		BeanUtils.copyProperties(associado, associadoDTO);
		validarCpf(associadoDTO);
		
		updateData(newObj, associado);
		return associadoRepository.save(newObj);
	}

	private void updateData(Associado newAssociado, Associado associado) {
		BeanUtils.copyProperties(associado, newAssociado);
	}

	public Associado fromDTO(AssociadoDTO associadoDTO) throws OAuthSystemException, OAuthProblemException, IOException {		
		UsuarioCpfDTO usuarioCpfDTO = validarCpf(associadoDTO);
		return new Associado(null, usuarioCpfDTO.getNome(), usuarioCpfDTO.getNome().trim().concat("@gmail.com") , usuarioCpfDTO.getCPF());
	}

	private UsuarioCpfDTO validarCpf(AssociadoDTO associadoDTO) throws OAuthSystemException, OAuthProblemException, IOException {
		String json = ClienteApiCpfLight.consultaCpf(associadoDTO.getCpf());
		List<UsuarioCpfDTO> lstUsuarioCPF = JSONUtils.json2ListObject(json, UsuarioCpfDTO.class);
		UsuarioCpfDTO usuarioCpfDTO = lstUsuarioCPF.stream().findFirst().orElse(null);
		
		if(usuarioCpfDTO == null || StringUtils.isEmpty(usuarioCpfDTO.getNome())) {
			LOG.error("CPF não encontrado: ".concat(associadoDTO.getCpf()));
			throw new CpfInvalidoException("CPF não encontrado");
		}
		
		return usuarioCpfDTO;
	}
}
