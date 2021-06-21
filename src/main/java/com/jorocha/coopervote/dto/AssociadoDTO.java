package com.jorocha.coopervote.dto;

import java.io.Serializable;

import com.jorocha.coopervote.domain.Associado;

public class AssociadoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String email;
	private String cpf;
	
	public AssociadoDTO() {
	}
	
	public AssociadoDTO(Associado obj) {
		this.id = obj.getId();
		this.name = obj.getNome();
		this.email = obj.getEmail();
		this.cpf = obj.getCpf();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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