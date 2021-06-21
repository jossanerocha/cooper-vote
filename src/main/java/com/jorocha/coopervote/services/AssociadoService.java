package com.jorocha.coopervote.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jorocha.coopervote.domain.Associado;
import com.jorocha.coopervote.dto.AssociadoDTO;
import com.jorocha.coopervote.repository.AssociadoRepository;
import com.jorocha.coopervote.resources.util.CpfValidator;
import com.jorocha.coopervote.services.exception.CpfInvalidoException;
import com.jorocha.coopervote.services.exception.ObjectNotFoundException;

@Service
public class AssociadoService {

	@Autowired
	private AssociadoRepository repo;

	public List<Associado> findAll() {
		return repo.findAll();
	}

	public Associado findById(String id) {
		Optional<Associado> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Associado não encontrado"));
	}

	public Associado insert(Associado obj) {
		return repo.insert(obj);
	}

	public void delete(String id) {
		findById(id);
		repo.deleteById(id);
	}

	public Associado update(Associado obj) {
		Associado newObj = findById(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	private void updateData(Associado newAssociado, Associado associado) {
		BeanUtils.copyProperties(associado, newAssociado);
	}

	public Associado fromDTO(AssociadoDTO objDto) {
		Boolean isValid = CpfValidator.isValid(objDto.getCpf());
		if(Boolean.TRUE.equals(isValid))
			return new Associado(objDto.getId(), objDto.getName(), objDto.getEmail(), objDto.getCpf());
		else
			throw new CpfInvalidoException();
	}
}
