package com.xadrez.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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

	//método para definir qual cor de peça o jogador receberá
	public void defineEAtribuiCorPecas(HashMap<String, Jogador> listaJogadores) {		
		//inicializa o randomizador
		Random randomizador = new Random();
		
		//não tem jogador no tabuleiro
		if(listaJogadores.size() == 0) {
			//define aleatoriamente a cor
			this.corPecas = randomizador.nextInt(2) == 0 ? "Brancas" : "Pretas";
		} 
		
		//já tem jogador
		Map.Entry<String, Jogador> jogador = listaJogadores.entrySet().iterator().next();
		this.corPecas = jogador.getValue().getCorPecas().equalsIgnoreCase("Brancas") ? "Pretas" : "Brancas";
	}
}