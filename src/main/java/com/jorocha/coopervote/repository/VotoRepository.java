package com.jorocha.coopervote.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jorocha.coopervote.domain.Voto;

@Repository
public interface VotoRepository extends MongoRepository<Voto, String> {
	

}