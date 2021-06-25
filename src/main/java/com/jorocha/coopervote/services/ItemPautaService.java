package com.jorocha.coopervote.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jorocha.coopervote.domain.ItemPauta;
import com.jorocha.coopervote.dto.ItemPautaDTO;
import com.jorocha.coopervote.repository.ItemPautaRepository;
import com.jorocha.coopervote.services.exception.ObjectNotFoundException;

@Service
public class ItemPautaService {
	
	private static Logger LOG = LoggerFactory.getLogger(ItemPautaService.class);

	@Autowired
	private ItemPautaRepository itemPautaRepository;
	
	
	/**
	 * Lista itens da pauta
	 *
	 * @param 
	 * @return List<ItemPauta>
	 */
	public List<ItemPauta> findAll() {
		return itemPautaRepository.findAll();
	}	
	
	/**
	 * Busca um item da pauta
	 *
	 * @param idItemPauta
	 * @return ItemPauta
	 */	
	public ItemPauta findById(String id) {
		LOG.info(">>> Busca item: ".concat(id));
		Optional<ItemPauta> obj = itemPautaRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Item da pauta não encontrado"));
	}
	
	/**
	 * Insere um item de pauta
	 *
	 * @param ItemPauta
	 * @return ItemPauta
	 */	
	public ItemPauta insert(ItemPauta itemPauta) {
		return itemPautaRepository.insert(itemPauta);
	}

	/**
	 * Exclui um item de pauta
	 *
	 * @param idItemPauta
	 * @return void
	 */	
	public void delete(String id) {
		findById(id);
		LOG.info(">>> Exclusão de item da pauta: ".concat(id));
		itemPautaRepository.deleteById(id);
	}

	/**
	 * Atualiza os dados de um item da pauta
	 *
	 * @param ItemPauta
	 * @return ItemPauta
	 */	
	public ItemPauta update(ItemPauta obj) {
		ItemPauta newObj = findById(obj.getId());
		updateData(newObj, obj);
		LOG.info(">>> Atualização do item da pauta: ".concat(obj.getId()));
		return itemPautaRepository.save(newObj);
	}

	/**
	 * Copia os dados atualizados para o objeto ItemPauta
	 *
	 * @param new ItemPauta
	 * @param ItemPauta
	 * @return void
	 * @see BeanUtils
	 */		
	private void updateData(ItemPauta newItemPauta, ItemPauta itemPauta) {
		BeanUtils.copyProperties(itemPauta, newItemPauta);
	}

	/**
	 * Cria um objeto ItemPauta a partir de um DTO
	 *
	 * @param ItemPautaDTO
	 * @return ItemPauta
	 */	
	public ItemPauta fromDTO(ItemPautaDTO itemPautaDTO) {		
		return new ItemPauta(itemPautaDTO.getId(), itemPautaDTO.getTitulo(), itemPautaDTO.getDescricao(), itemPautaDTO.getTotalVotos(), itemPautaDTO.getTotalVotosSim(), itemPautaDTO.getTotalVotosNao(), itemPautaDTO.getResultado(), itemPautaDTO.getVotos());
	}	
	
	/**
	 * Lista itens de pauta por termo contido no título
	 *
	 * @param text
	 * @return List<ItemPauta>
	 */	
	public List<ItemPauta> findByTitulo(String text) {
		return itemPautaRepository.findByTitulo(text);
	}
	
	/**
	 * Lista itens de pauta por termo contido no título ou descrição e entre um periodo de data
	 *
	 * @param text
	 * @param minDate
	 * @param maxDate
	 * @return List<Pauta>
	 */	
	public List<ItemPauta> findByTituloOrDescricao(String text) {
		return itemPautaRepository.buscarPorTituloOrDescricao(text);
	}
	
}
