package com.xadrez.models;

public class Jogador {
	private String id;
	private String nome;
	private String corPecas;
	private String status;
	
	public Jogador(String id, String nome, String corPecas, String status) {
		super();
		this.id = id;
		this.nome = nome;
		this.corPecas = corPecas;
		this.status = status;
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

	public String getCorPecas() {
		return corPecas;
	}

	public void setCorPecas(String corPecas) {
		this.corPecas = corPecas;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
