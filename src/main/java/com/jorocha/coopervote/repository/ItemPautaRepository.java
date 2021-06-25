package com.jorocha.coopervote.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.jorocha.coopervote.domain.ItemPauta;

@Repository
public interface ItemPautaRepository extends MongoRepository<ItemPauta, String> {

	@Query("{ 'titulo': { $regex: ?0, $options: 'i' } }")
	List<ItemPauta> findByTitulo(String text);
	
	List<ItemPauta> findByTituloContainingIgnoreCase(String text);
	
	@Query(" { $or: [ { 'titulo': { $regex: ?0, $options: 'i' } }, { 'descricao': { $regex: ?0, $options: 'i' } } ] } ")
	List<ItemPauta> buscarPorTituloOrDescricao(String text);
}


	