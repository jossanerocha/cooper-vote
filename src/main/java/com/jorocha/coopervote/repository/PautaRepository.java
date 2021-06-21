package com.jorocha.coopervote.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.jorocha.coopervote.domain.Pauta;

@Repository
public interface PautaRepository extends MongoRepository<Pauta, String> {

	@Query("{ 'titulo': { $regex: ?0, $options: 'i' } }")
	List<Pauta> findByTitulo(String text);
	
	List<Pauta> findByTituloContainingIgnoreCase(String text);
	
	@Query("{ $and: [ { data: {$gte: ?1} }, { data: { $lte: ?2} } , { $or: [ { 'titulo': { $regex: ?0, $options: 'i' } }, { 'descricao': { $regex: ?0, $options: 'i' } } ] } ] }")
	List<Pauta> buscarPorTituloOrDescricaoAndData(String text, Date minDate, Date maxDate);
}


	