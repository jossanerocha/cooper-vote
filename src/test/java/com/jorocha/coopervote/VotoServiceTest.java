package com.jorocha.coopervote;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jorocha.coopervote.domain.Associado;
import com.jorocha.coopervote.domain.Voto;
import com.jorocha.coopervote.repository.AssociadoRepository;
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
	
	@Test
	@Order(1)
	public void deveriaInserirUmaListaDeVotos() {	
		Associado associado001 = new Associado(null, "Pedro", "associado001@gmail.com", "00000000272");
		Associado associado002 = new Associado(null, "Jonas", "associado002@gmail.com", "00000000353");
		Associado associado003 = new Associado(null, "Marta", "associado003@gmail.com", "00000000434");		
		associadoRepository.saveAll(Arrays.asList(associado001, associado002, associado003));
		
		Voto voto1Pauta2 = new Voto(null, "Sim", associado001);
		Voto voto2Pauta2 = new Voto(null, "Sim", associado002);
		Voto voto3Pauta2 = new Voto(null, "Sim", associado003);
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
		result.setIndVoto("Não");
		result = service.update(result);
		assertThat(result.getIndVoto()).isEqualTo("Não");
	}		
	
	@Test
	@Order(5)
	public void deveriaRetornarVotoException() {	
		Voto result = deveriaRetornarUmVoto();
		result.setIndVoto("Talvez");
		Exception exception = assertThrows(VotoException.class, () -> {
			service.verificarTipoVoto(result);
	    });
	    String msgEsperada = "Tipos válidos de voto: Sim/Não";
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
	    String msgEsperada = "Voto não encontrado";
	    String msgAtual = exception.getMessage();	    
	    assertEquals(msgAtual, msgEsperada);
	}	

}
