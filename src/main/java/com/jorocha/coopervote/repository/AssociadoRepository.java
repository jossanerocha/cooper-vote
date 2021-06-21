package com.jorocha.coopervote.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jorocha.coopervote.domain.Associado;

@Repository
public interface AssociadoRepository extends MongoRepository<Associado, String> {
	
	Associado findByCpf(String cpf);

}