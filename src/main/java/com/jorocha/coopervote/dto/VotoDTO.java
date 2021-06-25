package com.jorocha.coopervote.dto;

import java.io.Serializable;

import com.jorocha.coopervote.domain.Associado;
import com.jorocha.coopervote.domain.Voto;

public class VotoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String indVoto;
	private String idItemPauta;
	private String idAssociado;
	
	public VotoDTO() {
	}
	
	public VotoDTO(Voto voto) {
		this.id = voto.getId();
		this.idItemPauta = voto.getIdItemPauta();
		this.idAssociado = voto.getIdAssociado();
	}

	public VotoDTO(String id, String indVoto, String idItemPauta, String idAssociado) {
		super();
		this.id = id;
		this.indVoto = indVoto;
		this.idItemPauta = idItemPauta;
		this.idAssociado = idAssociado;
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