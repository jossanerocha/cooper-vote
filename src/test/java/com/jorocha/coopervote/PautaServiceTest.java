package com.jorocha.coopervote;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jorocha.coopervote.domain.Pauta;
import com.jorocha.coopervote.domain.Voto;
import com.jorocha.coopervote.repository.PautaRepository;
import com.jorocha.coopervote.services.PautaService;
import com.jorocha.coopervote.services.exception.ObjectNotFoundException;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class PautaServiceTest {
	
	@Autowired
	PautaService service;
	
	@Autowired
	PautaRepository repository;
	
	
	@Test
	@Order(1)
	public void deveriaInserirUmaListaDeAssociados() {	
		Pauta pauta1 = new Pauta(null, new Date(), "Reunião 001", "Descrição 001", 5, LocalDateTime.of(2021, Month.JUNE, 21, 14, 33), LocalDateTime.of(2021, Month.JUNE, 21, 14, 45), new ArrayList<Voto>());
		Pauta pauta2 = new Pauta(null, new Date(), "Reunião 002", "Descrição 002", null, LocalDateTime.now(), LocalDateTime.now(), new ArrayList<Voto>());
		
		Arrays.asList(pauta1, pauta2).stream().forEach(p -> {
			service.insert(p);	
		});
		
		repository.saveAll(Arrays.asList(pauta1, pauta2));			
		List<Pauta> result = service.findAll();
		assertThat(result.size()).isEqualTo(2);
	}
	
	@Test
	@Order(2)
	public void deveriaRetornarUmaPautaPorTermoContidoNoTitulo() {	
		List<Pauta> result = service.findByTitulo("Reunião");
		assertThat(result.size()).isEqualTo(2);
	}
	
	@Test
	@Order(3)
	public void deveriaRetornarIndexOutOfBoundsException() {	
		List<Pauta> result = service.findByTitulo("003");	    
		Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
	        result.get(0);
	    });
	    String msgEsperada = "Index: 0, Size: 0";
	    String msgAtual = exception.getMessage();	    
	    assertTrue(msgAtual.equals(msgEsperada));
	}
	
	@Test
	@Order(4)
	public void deveriaRetornarUmaPautaPorTermoContidoNaDescricao() {	
		List<Pauta> result = service.findByTitulo("002");	     
		Pauta pauta = result.get(0);
	    assertThat(pauta).isNotNull();
	    assertThat(pauta.getTitulo()).isEqualTo("Reunião 002");
	}	
	
	@Test
	@Order(5)
	public void deveriaRetornarTodosAsPautas() {	
		List<Pauta> result = service.findAll();
		assertThat(result.size()).isGreaterThan(0);
	}
	
	@Test
	@Order(6)
	public Pauta deveriaRetornarUmaPauta() {	
		Pauta result = service.findAll().get(0);
		assertThat(result).isNotNull();
		return result;
	}
	
	@Test
	@Order(7)
	public void deveriaAbrirSessaoDeUmaPauta() {	
		Pauta pauta = deveriaRetornarUmaPauta();
		pauta = service.abrirSessao(pauta.getId(), 3);
		assertThat(pauta.getDuracaoSessao()).isEqualTo(3);
	}	
	
	@Test
	@Order(8)
	public void deveriaAtualizarUmaPauta() {	
		Pauta pauta = deveriaRetornarUmaPauta();
		pauta.setTitulo("Título alterado");
		pauta = service.update(pauta);
		assertThat(pauta.getTitulo()).isEqualTo("Título alterado");
	}	
	
	@Test
	@Order(9)
	public void deveriaExcluirUmaPauta() {	
		Pauta pauta = deveriaRetornarUmaPauta();
		service.delete(pauta.getId());
		deveriaRetornarObjectNotFoundException();
	}
	
	public void deveriaRetornarObjectNotFoundException() {	
		Exception exception = assertThrows(ObjectNotFoundException.class, () -> {
			service.findById("dfdfdfdfdfdfdfdf");
	    });
	    String msgEsperada = "Pauta não encontrada";
	    String msgAtual = exception.getMessage();	    
	    assertEquals(msgAtual, msgEsperada);
	}	

}
