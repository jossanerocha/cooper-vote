package com.jorocha.coopervote.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jorocha.coopervote.domain.ItemPauta;
import com.jorocha.coopervote.domain.Voto;

public class ItemPautaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String titulo;
	private String descricao;
	private Integer totalVotos;
	private Long totalVotosSim;
	private Long totalVotosNao;
	private String resultado;
	private List<Voto> votos = new ArrayList<>();
	
	public ItemPautaDTO() {
		super();
	}

	public ItemPautaDTO(ItemPauta itemPauta) {
		super();
		this.id = itemPauta.getId();
		this.titulo = itemPauta.getTitulo();
		this.descricao = itemPauta.getDescricao();
		this.totalVotos = itemPauta.getTotalVotos();
		this.totalVotosSim = itemPauta.getTotalVotosSim();
		this.totalVotosNao = itemPauta.getTotalVotosNao();
		this.resultado = itemPauta.getResultado();
		this.getVotos().addAll(itemPauta.getVotos());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public List<Voto> getVotos() {
		return votos;
	}

	public void setVotos(List<Voto> votos) {
		this.votos = votos;
	}
	
}
