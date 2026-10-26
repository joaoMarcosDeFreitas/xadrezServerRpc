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
	public JsonObject verificaNome(ArrayList<Jogador> jogadores) {
		//verifica se tem menos que 3 letras ou mais que 15
		if(this.nome.length() > 15 || this.nome.length() < 3) 
			//cria um JSON para montar a resposta (informar ao código principal que está inválido)
			return Utils.montaRespotaJsonObject("3", "Nome inválido, insira um nome válido e tente novamente. Nome deve ser maior ou igual a 3 carácteres, menor ou igual a 15 carácteres e não pode estar vazio.");
		
		//looping na lista de jogadores
		for(Jogador jogador : jogadores) {
			//verifica se nome já está sendo utilizado
			if(this.nome.equalsIgnoreCase(jogador.getNome())) 
				//cria um JSON para montar a resposta (informar ao código principal que está inválido)
				return Utils.montaRespotaJsonObject("3", "Nome inválido! Nome deve ser único.");
		}
		
		//cria um JSON para montar a resposta (informar ao código principal que está inválido)
		return Utils.montaRespotaJsonObject("0", "Sucesso! Nome válido!");
	}
}