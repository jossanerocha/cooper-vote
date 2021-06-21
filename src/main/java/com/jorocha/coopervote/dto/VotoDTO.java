package com.jorocha.coopervote.dto;

import java.io.Serializable;

import com.jorocha.coopervote.domain.Associado;
import com.jorocha.coopervote.domain.Voto;

public class VotoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String indVoto;
	private Associado associado;
	
	public VotoDTO() {
	}
	
	public VotoDTO(Voto voto) {
		this.id = voto.getId();
		this.indVoto = voto.getIndVoto();
		this.associado = voto.getAssociado();
	}

	public VotoDTO(String id, String indVoto, Associado associado) {
		super();
		this.id = id;
		this.indVoto = indVoto;
		this.associado = associado;
	}

	public VotoDTO(Associado obj) {
		id = obj.getId();
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