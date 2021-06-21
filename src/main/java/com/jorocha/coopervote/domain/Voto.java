package com.jorocha.coopervote.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Voto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String indVoto;
	private Associado associado;
	
	public Voto() {
	}

	public Voto(String id, String indVoto, Associado associado) {
		super();
		this.id = id;
		this.indVoto = indVoto;
		this.associado = associado;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIndVoto() {
		return indVoto;
	}

	public void setIndVoto(String indVoto) {
		this.indVoto = indVoto;
	}

	public Associado getAssociado() {
		return associado;
	}

	public void setAssociado(Associado associado) {
		this.associado = associado;
	}	

}
