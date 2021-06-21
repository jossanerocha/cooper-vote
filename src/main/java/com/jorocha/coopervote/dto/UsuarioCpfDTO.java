package com.jorocha.coopervote.dto;

import java.io.Serializable;

public class UsuarioCpfDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private String 	CPF;
    private String 	Nome;
    private Integer SituacaoCadastral;
    private String 	ResidenteExterior;
    private String 	NomeMae;
    private String 	DataNascimento;
    private Integer Sexo;
    private Integer NaturezaOcupacao;
    private Integer ExercicioOcupacao;
    private String 	TipoLogradouro;
    private String 	Logradouro;
    private String 	NumeroLogradouro;
    private String 	Bairro;
    private Integer Cep;
    private String 	UF;
    private Integer CodigoMunicipio;
    private String 	Municipio;
    private Integer UnidadeAdministrativa;
    private String 	NomeUnidadeAdministrativa;
    private String 	Estrangeiro;
    private String 	DataAtualizacao;
    private String 	DataInscricao;
	
	public UsuarioCpfDTO() {
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public String getNome() {
		return Nome;
	}

	public void setNome(String nome) {
		Nome = nome;
	}

	public Integer getSituacaoCadastral() {
		return SituacaoCadastral;
	}

	public void setSituacaoCadastral(Integer situacaoCadastral) {
		SituacaoCadastral = situacaoCadastral;
	}

	public String getResidenteExterior() {
		return ResidenteExterior;
	}

	public void setResidenteExterior(String residenteExterior) {
		ResidenteExterior = residenteExterior;
	}

	public String getNomeMae() {
		return NomeMae;
	}

	public void setNomeMae(String nomeMae) {
		NomeMae = nomeMae;
	}

	public String getDataNascimento() {
		return DataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		DataNascimento = dataNascimento;
	}

	public Integer getSexo() {
		return Sexo;
	}

	public void setSexo(Integer sexo) {
		Sexo = sexo;
	}

	public Integer getNaturezaOcupacao() {
		return NaturezaOcupacao;
	}

	public void setNaturezaOcupacao(Integer naturezaOcupacao) {
		NaturezaOcupacao = naturezaOcupacao;
	}

	public Integer getExercicioOcupacao() {
		return ExercicioOcupacao;
	}

	public void setExercicioOcupacao(Integer exercicioOcupacao) {
		ExercicioOcupacao = exercicioOcupacao;
	}

	public String getTipoLogradouro() {
		return TipoLogradouro;
	}

	public void setTipoLogradouro(String tipoLogradouro) {
		TipoLogradouro = tipoLogradouro;
	}

	public String getLogradouro() {
		return Logradouro;
	}

	public void setLogradouro(String logradouro) {
		Logradouro = logradouro;
	}

	public String getNumeroLogradouro() {
		return NumeroLogradouro;
	}

	public void setNumeroLogradouro(String numeroLogradouro) {
		NumeroLogradouro = numeroLogradouro;
	}

	public String getBairro() {
		return Bairro;
	}

	public void setBairro(String bairro) {
		Bairro = bairro;
	}

	public Integer getCep() {
		return Cep;
	}

	public void setCep(Integer cep) {
		Cep = cep;
	}

	public String getUF() {
		return UF;
	}

	public void setUF(String uF) {
		UF = uF;
	}

	public Integer getCodigoMunicipio() {
		return CodigoMunicipio;
	}

	public void setCodigoMunicipio(Integer codigoMunicipio) {
		CodigoMunicipio = codigoMunicipio;
	}

	public String getMunicipio() {
		return Municipio;
	}

	public void setMunicipio(String municipio) {
		Municipio = municipio;
	}

	public Integer getUnidadeAdministrativa() {
		return UnidadeAdministrativa;
	}

	public void setUnidadeAdministrativa(Integer unidadeAdministrativa) {
		UnidadeAdministrativa = unidadeAdministrativa;
	}

	public String getNomeUnidadeAdministrativa() {
		return NomeUnidadeAdministrativa;
	}

	public void setNomeUnidadeAdministrativa(String nomeUnidadeAdministrativa) {
		NomeUnidadeAdministrativa = nomeUnidadeAdministrativa;
	}

	public String getEstrangeiro() {
		return Estrangeiro;
	}

	public void setEstrangeiro(String estrangeiro) {
		Estrangeiro = estrangeiro;
	}

	public String getDataAtualizacao() {
		return DataAtualizacao;
	}

	public void setDataAtualizacao(String dataAtualizacao) {
		DataAtualizacao = dataAtualizacao;
	}

	public String getDataInscricao() {
		return DataInscricao;
	}

	public void setDataInscricao(String dataInscricao) {
		DataInscricao = dataInscricao;
	}

}