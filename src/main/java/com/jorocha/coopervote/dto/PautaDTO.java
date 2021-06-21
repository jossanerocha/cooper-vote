package com.jorocha.coopervote.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jorocha.coopervote.domain.Pauta;
import com.jorocha.coopervote.domain.Voto;

public class PautaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private Date data;
	private String titulo;
	private String descricao;
	private Integer totalVotos;
	private Long totalVotosSim;
	private Long totalVotosNao;
	private String resultado;
	private Integer duracaoSessao;
	private LocalDateTime inicioSessao;
	private LocalDateTime fimSessao;	
	private List<Voto> votos = new ArrayList<>();
	
	public PautaDTO() {
		super();
	}

	public PautaDTO(Pauta pauta) {
		this.id = pauta.getId();
		this.data = pauta.getData();
		this.titulo = pauta.getTitulo();
		this.descricao = pauta.getDescricao();
		this.totalVotos = pauta.getVotos().size();
		this.totalVotosSim = pauta.getVotos().stream().filter(v -> v.getIndVoto().equalsIgnoreCase("Sim")).count();
		this.totalVotosNao = pauta.getVotos().stream().filter(v -> v.getIndVoto().equalsIgnoreCase("Não")).count();
		this.resultado = this.totalVotosSim > this.totalVotosNao ? "Aprovado" : this.totalVotosNao > this.totalVotosSim ? "Reprovado" : "";		
		this.duracaoSessao = pauta.getDuracaoSessao();
		this.inicioSessao = pauta.getInicioSessao();
		this.fimSessao = pauta.getFimSessao();
		this.getVotos().addAll(pauta.getVotos());		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getTotalVotos() {
		return totalVotos;
	}

	public void setTotalVotos(Integer totalVotos) {
		this.totalVotos = totalVotos;
	}

	public Long getTotalVotosSim() {
		return totalVotosSim;
	}

	public void setTotalVotosSim(Long totalVotosSim) {
		this.totalVotosSim = totalVotosSim;
	}

	public Long getTotalVotosNao() {
		return totalVotosNao;
	}

	public void setTotalVotosNao(Long totalVotosNao) {
		this.totalVotosNao = totalVotosNao;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public Integer getDuracaoSessao() {
		return duracaoSessao;
	}

	public void setDuracaoSessao(Integer duracaoSessao) {
		this.duracaoSessao = duracaoSessao;
	}

	public List<Voto> getVotos() {
		return votos;
	}

	public void setVotos(List<Voto> votos) {
		this.votos = votos;
	}	
	
	public LocalDateTime getInicioSessao() {
		return inicioSessao;
	}

	public void setInicioSessao(LocalDateTime inicioSessao) {
		this.inicioSessao = inicioSessao;
	}

	public LocalDateTime getFimSessao() {
		return fimSessao;
	}

	public void setFimSessao(LocalDateTime fimSessao) {
		this.fimSessao = fimSessao;
	}	
	
	
}