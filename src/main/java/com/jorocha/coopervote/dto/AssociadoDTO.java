package com.jorocha.coopervote.dto;

import java.io.Serializable;

import com.jorocha.coopervote.domain.Associado;

public class AssociadoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String id;
	private String nome;
	private String email;
	private String cpf;
	
	public AssociadoDTO() {
	}
	
	public AssociadoDTO(Associado obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.email = obj.getEmail();
		this.cpf = obj.getCpf();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
}