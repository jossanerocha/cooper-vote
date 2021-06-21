package com.jorocha.coopervote.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Pauta implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private Date data;
	private String titulo;
	private String descricao;
	private Integer duracaoSessao;
	private LocalDateTime inicioSessao;
	private LocalDateTime fimSessao;
	
	@DBRef()
	private List<Voto> votos = new ArrayList<>();
	
	public Pauta() {
	}

	public Pauta(String id, Date data, String titulo, String descricao, Integer duracaoSessao, LocalDateTime inicioSessao, LocalDateTime fimSessao, List<Voto> votos) {
		super();
		this.id = id;
		this.data = data;
		this.titulo = titulo;
		this.descricao = descricao;
		this.duracaoSessao = duracaoSessao;
		this.inicioSessao = inicioSessao;
		this.fimSessao = fimSessao;
		this.getVotos().addAll(votos);
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

	public List<Voto> getVotos() {
		return votos;
	}

	public void setVotos(List<Voto> votos) {
		this.votos = votos;
	}

	public Integer getDuracaoSessao() {
		return duracaoSessao;
	}

	public void setDuracaoSessao(Integer duracaoSessao) {
		this.duracaoSessao = duracaoSessao;
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
		Pauta other = (Pauta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
