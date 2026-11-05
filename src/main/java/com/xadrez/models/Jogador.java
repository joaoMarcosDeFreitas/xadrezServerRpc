package com.xadrez.models;

import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.xadrez.utils.*;

public class Jogador {
	private String id;
	private String nome;
	
	public Jogador(String id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
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

	//método para verificar se o nome atende as regras
	public Boolean verificaNome(ArrayList<Jogador> jogadores) {
		//verifica se tem menos que 3 letras ou mais que 15
		if(this.nome.length() > 15 || this.nome.length() < 3) {
			return false;
		}
		
		//looping na lista de jogadores
		for(Jogador jogador : jogadores) {
			//verifica se nome já está sendo utilizado
			if(this.nome.equalsIgnoreCase(jogador.getNome())) 
				return false;
		}
		
		return true;
	}
		
}