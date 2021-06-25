package com.jorocha.coopervote.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ItemPauta implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String titulo;
	private String descricao;
	private Integer totalVotos;
	private Long totalVotosSim;
	private Long totalVotosNao;
	private String resultado;	
	
	@DBRef(lazy = true)
	private List<Voto> votos = new ArrayList<>();
	
	public ItemPauta() {
	}

	public ItemPauta(String id, String titulo, String descricao, Integer totalVotos, Long totalVotosSim, Long totalVotosNao, String resultado, List<Voto> votos) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.totalVotos = votos.size();
		this.totalVotosSim = votos.stream().filter(v -> v.getIndVoto().equalsIgnoreCase("Sim")).count();
		this.totalVotosNao = votos.stream().filter(v -> v.getIndVoto().equalsIgnoreCase("nao")).count();
		this.resultado = this.totalVotosSim > this.totalVotosNao ? "Aprovado" : this.totalVotosNao > this.totalVotosSim ? "Reprovado" : "";		
		this.getVotos().addAll(votos);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemPauta other = (ItemPauta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
