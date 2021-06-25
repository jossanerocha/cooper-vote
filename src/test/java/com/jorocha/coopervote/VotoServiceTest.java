package com.jorocha.coopervote;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jorocha.coopervote.domain.Associado;
import com.jorocha.coopervote.domain.ItemPauta;
import com.jorocha.coopervote.domain.Voto;
import com.jorocha.coopervote.repository.AssociadoRepository;
import com.jorocha.coopervote.repository.ItemPautaRepository;
import com.jorocha.coopervote.repository.VotoRepository;
import com.jorocha.coopervote.services.VotoService;
import com.jorocha.coopervote.services.exception.ObjectNotFoundException;
import com.jorocha.coopervote.services.exception.VotoException;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class VotoServiceTest {
	
	@Autowired
	VotoService service;
	
	@Autowired
	private VotoRepository repository;	
	
	@Autowired
	private AssociadoRepository associadoRepository;		
	
	@Autowired
	private ItemPautaRepository itemPautaRepository;	
	
	@Test
	@Order(1)
	public void deveriaInserirUmaListaDeVotos() {	
		Associado associado001 = new Associado(null, "Pedro", "associado001@gmail.com", "00000000272");
		Associado associado002 = new Associado(null, "Jonas", "associado002@gmail.com", "00000000353");
		Associado associado003 = new Associado(null, "Marta", "associado003@gmail.com", "00000000434");		
		associadoRepository.saveAll(Arrays.asList(associado001, associado002, associado003));
		
		ItemPauta itemPauta = new ItemPauta(null, "Troca de empresa de limpeza", "Contratar nova empresa X", 3, new Long(3), new Long(0), "Aprovado",new ArrayList<Voto>());
		itemPautaRepository.save(itemPauta);
		
		Voto voto1Pauta2 = new Voto(null, "Sim", itemPauta.getId(), associado001.getId());
		Voto voto2Pauta2 = new Voto(null, "Sim", itemPauta.getId(), associado002.getId());
		Voto voto3Pauta2 = new Voto(null, "Sim", itemPauta.getId(), associado003.getId());
		repository.saveAll(Arrays.asList(voto1Pauta2, voto2Pauta2, voto3Pauta2));			
		List<Voto> result = service.findAll();
		assertThat(result.size()).isGreaterThan(0);
	}	
	
	@Test
	@Order(2)
	public void deveriaRetornarTodosOsVotos() {	
		List<Voto> result = service.findAll();
		assertThat(result.size()).isGreaterThan(0);
	}
	
	@Test
	@Order(3)
	public Voto deveriaRetornarUmVoto() {	
		Voto result = service.findAll().get(0);
		assertThat(result).isNotNull();
		return result;
	}
	
	@Test
	@Order(4)
	public void deveriaAtualizarUmVoto() {	
		Voto result = deveriaRetornarUmVoto();
		result.setIndVoto("nao");
		result = service.update(result);
		assertThat(result.getIndVoto()).isEqualTo("nao");
	}		
	
	@Test
	@Order(5)
	public void deveriaRetornarVotoException() {	
		Voto result = deveriaRetornarUmVoto();
		result.setIndVoto("Talvez");
		Exception exception = assertThrows(VotoException.class, () -> {
			service.verificarTipoVoto(result);
	    });
	    String msgEsperada = "Tipos validos de voto: Sim/nao";
	    String msgAtual = exception.getMessage();	    
	    assertEquals(msgAtual, msgEsperada);
	}
	
	@Test
	@Order(6)
	public void deveriaExcluirUmVoto() {	
		Voto result = deveriaRetornarUmVoto();
		service.delete(result.getId());
		deveriaRetornarObjectNotFoundException();
	}
	
	public void deveriaRetornarObjectNotFoundException() {	
		Exception exception = assertThrows(ObjectNotFoundException.class, () -> {
			service.findById("dfdfdfdfdfdfdfdf");
	    });
	    String msgEsperada = "Voto nao encontrado";
	    String msgAtual = exception.getMessage();	    
	    assertEquals(msgAtual, msgEsperada);
	}	

}
