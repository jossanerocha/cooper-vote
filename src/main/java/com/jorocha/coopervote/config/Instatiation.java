package com.jorocha.coopervote.config;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.jorocha.coopervote.domain.Associado;
import com.jorocha.coopervote.domain.Pauta;
import com.jorocha.coopervote.domain.Voto;
import com.jorocha.coopervote.repository.AssociadoRepository;
import com.jorocha.coopervote.repository.PautaRepository;
import com.jorocha.coopervote.repository.VotoRepository;

@Configuration
public class Instatiation implements CommandLineRunner{
	
	@Autowired
	private AssociadoRepository associadoRepository;
	
	@Autowired
	private PautaRepository pautaRepository;
	
	@Autowired
	private VotoRepository votoRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		associadoRepository.deleteAll();
		votoRepository.deleteAll();
		pautaRepository.deleteAll();
		
		Associado associado001 = new Associado(null, "Pedro", "associado001@gmail.com", "00000000272");
		Associado associado002 = new Associado(null, "Jonas", "associado002@gmail.com", "00000000353");
		Associado associado003 = new Associado(null, "Marta", "associado003@gmail.com", "00000000434");		
		associadoRepository.saveAll(Arrays.asList(associado001, associado002, associado003));

		Pauta pauta1 = new Pauta(null, new Date(), "Reunião deliberativa ordinária", "Reforma do telhado", 5, LocalDateTime.now(), LocalDateTime.now(), new ArrayList<Voto>());
		Pauta pauta2 = new Pauta(null, new Date(), "Reunião deliberativa extraordinária", "Festa junina", null, LocalDateTime.now(), LocalDateTime.now(), new ArrayList<Voto>());
		pautaRepository.saveAll(Arrays.asList(pauta1, pauta2));
				
		Voto voto1Pauta1 = new Voto(null, "Sim", associado001);
		Voto voto2Pauta1 = new Voto(null, "Não", associado002);
		Voto voto3Pauta1 = new Voto(null, "Não", associado003);
		votoRepository.saveAll(Arrays.asList(voto1Pauta1, voto2Pauta1, voto3Pauta1));
		
		Voto voto1Pauta2 = new Voto(null, "Sim", associado001);
		Voto voto2Pauta2 = new Voto(null, "Sim", associado002);
		Voto voto3Pauta2 = new Voto(null, "Sim", associado003);
		votoRepository.saveAll(Arrays.asList(voto1Pauta2, voto2Pauta2, voto3Pauta2));
		
		pauta1.getVotos().addAll(Arrays.asList(voto1Pauta1, voto2Pauta1, voto3Pauta1));
		pauta2.getVotos().addAll(Arrays.asList(voto1Pauta2, voto2Pauta2, voto3Pauta2));
		pautaRepository.saveAll(Arrays.asList(pauta1, pauta2));		

	}

}
