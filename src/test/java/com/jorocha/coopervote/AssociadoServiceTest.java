package com.jorocha.coopervote;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jorocha.coopervote.domain.Associado;
import com.jorocha.coopervote.dto.AssociadoDTO;
import com.jorocha.coopervote.repository.AssociadoRepository;
import com.jorocha.coopervote.services.AssociadoService;
import com.jorocha.coopervote.services.exception.CpfInvalidoException;
import com.jorocha.coopervote.services.exception.ObjectNotFoundException;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class AssociadoServiceTest {
	
	@Autowired
	AssociadoService service;
	
	@Autowired
	private AssociadoRepository repository;	
	
	@Test
	@Order(1)
	public void deveriaInserirUmaListaDeAssociados() {	
		Associado associado001 = new Associado(null, "Pedro", "associado001@gmail.com", "00000000272");
		Associado associado002 = new Associado(null, "Jonas", "associado002@gmail.com", "00000000353");
		Associado associado003 = new Associado(null, "Marta", "associado003@gmail.com", "00000000434");		
		repository.saveAll(Arrays.asList(associado001, associado002, associado003));		
		List<Associado> result = service.findAll();
		assertThat(result.size()).isGreaterThan(0);
	}	
	
	@Test
	@Order(2)
	public void deveriaRetornarTodosOsAssociados() {	
		List<Associado> result = service.findAll();
		assertThat(result.size()).isGreaterThan(0);
	}
	
	@Test
	@Order(3)
	public Associado deveriaRetornarUmAssociado() {	
		Associado result = service.findAll().get(0);
		assertThat(result).isNotNull();
		return result;
	}
	
	@Test
	@Order(4)
	public void deveriaAtualizarUmAssociado() throws OAuthSystemException, OAuthProblemException, IOException {	
		Associado result = deveriaRetornarUmAssociado();
		result.setEmail("novo@gmail.com");
		result = service.update(result);
		assertThat(result.getEmail()).isEqualTo("novo@gmail.com");
	}	
	
	@Test
	@Order(5)
	public void deveriaValidarCPF() throws OAuthSystemException, OAuthProblemException, IOException {	
		Associado result = deveriaRetornarUmAssociado();
		AssociadoDTO dto = new AssociadoDTO();
		BeanUtils.copyProperties(result, dto);
		service.validarCpf(dto);
		assertNotNull(dto);
	}	
	
	@Test
	@Order(6)
	public void deveriaRetornarCpfInvalidoException() {	
		Associado result = deveriaRetornarUmAssociado();
		AssociadoDTO dto = new AssociadoDTO();
		BeanUtils.copyProperties(result, dto);
		dto.setCpf("12345678910");
		Exception exception = assertThrows(CpfInvalidoException.class, () -> {
			service.validarCpf(dto);
	    });
	    String msgEsperada = "CPF não encontrado";
	    String msgAtual = exception.getMessage();	    
	    assertEquals(msgAtual, msgEsperada);
	}
	
	@Test
	@Order(7)
	public void deveriaExcluirUmAssociado() {	
		Associado result = deveriaRetornarUmAssociado();
		service.delete(result.getId());
		deveriaRetornarObjectNotFoundException();
	}
	
	public void deveriaRetornarObjectNotFoundException() {	
		Exception exception = assertThrows(ObjectNotFoundException.class, () -> {
			service.findById("dfdfdfdfdfdfdfdf");
	    });
	    String msgEsperada = "Associado não encontrado";
	    String msgAtual = exception.getMessage();	    
	    assertEquals(msgAtual, msgEsperada);
	}	

}
