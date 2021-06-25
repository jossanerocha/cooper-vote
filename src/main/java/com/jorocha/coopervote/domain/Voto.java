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
	private String idItemPauta;
	private String idAssociado;
	
	public Voto() {
	}

	public Voto(String id, String indVoto, String idItemPauta, String idAssociado) {
		super();
		this.id = id;
		this.indVoto = indVoto;
		this.idItemPauta = idItemPauta;
		this.idAssociado = idAssociado;
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

	public String getIdItemPauta() {
		return idItemPauta;
	}

	public void setIdItemPauta(String idItemPauta) {
		this.idItemPauta = idItemPauta;
	}

	public String getIdAssociado() {
		return idAssociado;
	}

	public void setIdAssociado(String idAssociado) {
		this.idAssociado = idAssociado;
	}		

}
