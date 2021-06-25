package com.jorocha.coopervote.config;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.jorocha.coopervote.domain.Associado;
import com.jorocha.coopervote.domain.ItemPauta;
import com.jorocha.coopervote.domain.Pauta;
import com.jorocha.coopervote.domain.User;
import com.jorocha.coopervote.domain.Voto;
import com.jorocha.coopervote.repository.AssociadoRepository;
import com.jorocha.coopervote.repository.ItemPautaRepository;
import com.jorocha.coopervote.repository.PautaRepository;
import com.jorocha.coopervote.repository.UserRepository;
import com.jorocha.coopervote.repository.VotoRepository;

//@Configuration
public class Instatiation implements CommandLineRunner{
	
	@Autowired
	private AssociadoRepository associadoRepository;
	
	@Autowired
	private PautaRepository pautaRepository;
	
	@Autowired
	private ItemPautaRepository itemPautaRepository;	
	
	@Autowired
	private VotoRepository votoRepository;
	
	private UserRepository userRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		userRepository.deleteAll();
		associadoRepository.deleteAll();
		votoRepository.deleteAll();
		itemPautaRepository.deleteAll();
		pautaRepository.deleteAll();
		
		User user = new User();
		user.setUsername("user001");
		user.setPassword("user001");
		userRepository.save(user);
		
		Associado associado001 = new Associado(null, "Pedro", "associado001@gmail.com", "00000000272");
		Associado associado002 = new Associado(null, "Jonas", "associado002@gmail.com", "00000000353");
		Associado associado003 = new Associado(null, "Marta", "associado003@gmail.com", "00000000434");		
		associadoRepository.saveAll(Arrays.asList(associado001, associado002, associado003));
		
		Pauta pauta1 = new Pauta(null, new Date(), "Reuniao deliberativa ordinaria", "Reforma do telhado", 5, LocalDateTime.now(), LocalDateTime.now(), new ArrayList<ItemPauta>());
		Pauta pauta2 = new Pauta(null, new Date(), "Reuniao deliberativa extraordinaria", "Festa junina", null, LocalDateTime.now(), LocalDateTime.now(), new ArrayList<ItemPauta>());				
		pautaRepository.saveAll(Arrays.asList(pauta1, pauta2));
		
		ItemPauta item = new ItemPauta(null, "Pandemia", "Distancia de 2 metros para cada mesa", 3, new Long(3), new Long(0), "Aprovado",new ArrayList<Voto>());
		itemPautaRepository.saveAll(Arrays.asList(item));
		
		Voto voto1Pauta2 = new Voto(null, "Sim", item.getId(), associado001.getId());
		Voto voto2Pauta2 = new Voto(null, "Sim", item.getId(), associado002.getId());
		Voto voto3Pauta2 = new Voto(null, "Sim", item.getId(), associado003.getId());
		votoRepository.saveAll(Arrays.asList(voto1Pauta2, voto2Pauta2, voto3Pauta2));
		
		pauta2.getItens().add(item);
		pautaRepository.save(pauta2);
		
		item.getVotos().addAll(Arrays.asList(voto1Pauta2, voto2Pauta2, voto3Pauta2));
		itemPautaRepository.save(item);
	}

}