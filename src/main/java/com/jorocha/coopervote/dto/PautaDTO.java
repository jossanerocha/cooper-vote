package com.jorocha.coopervote.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jorocha.coopervote.domain.ItemPauta;
import com.jorocha.coopervote.domain.Pauta;

public class PautaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private Date data;
	private String titulo;
	private String descricao;
	private Integer duracaoSessao;
	private LocalDateTime inicioSessao;
	private LocalDateTime fimSessao;	
	private List<ItemPauta> itens = new ArrayList<>();
	
	public PautaDTO() {
		super();
	}

	public PautaDTO(Pauta pauta) {
		this.id = pauta.getId();
		this.data = pauta.getData();
		this.titulo = pauta.getTitulo();
		this.descricao = pauta.getDescricao();		
		this.duracaoSessao = pauta.getDuracaoSessao();
		this.inicioSessao = pauta.getInicioSessao();
		this.fimSessao = pauta.getFimSessao();
		this.getItens().addAll(pauta.getItens());		
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

	public Integer getDuracaoSessao() {
		return duracaoSessao;
	}

	public void setDuracaoSessao(Integer duracaoSessao) {
		this.duracaoSessao = duracaoSessao;
	}
	
	public List<ItemPauta> getItens() {
		return itens;
	}

	public void setItens(List<ItemPauta> itens) {
		this.itens = itens;
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