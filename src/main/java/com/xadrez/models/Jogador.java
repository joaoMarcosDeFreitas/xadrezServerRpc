package com.xadrez.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.google.gson.JsonObject;

public class Jogador {
	private String id;
	private String nome;
	private String corPecas;
	
	public Jogador(String id, String nome, String corPecas) {
		super();
		this.id = id;
		this.nome = nome;
		this.corPecas = corPecas;
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

	public JsonObject verificaId(String id) {
		//verifica se ID está vazio
		if(id.isBlank()) {
			//cria um JSON para montar a resposta (informar ao código principal que está inválido)
			JsonObject resposta = new JsonObject();

			//adiciona no JSON as chaves de código de erro e descrição
			resposta.addProperty("código", "3");
			resposta.addProperty("descrição", "Nome inválido, insira um nome válido e tente novamente.");
			
			//retorna o erro ao código principal
			return resposta;
		}
	}

	//método para verificar se o nome do jogador está de acordo com as regras
	public JsonObject verificaEAtribuiNome(HashMap<String, Jogador> listaJogadores, Jogador jogador) {
		//verifica se nome está vazio
		if(jogador.getNome().isEmpty()) {
			//cria um JSON para montar a resposta (informar ao código principal que está inválido)
			JsonObject resposta = new JsonObject();

			//adiciona no JSON as chaves de código de erro e descrição
			resposta.addProperty("código", "3");
			resposta.addProperty("descrição", "Nome inválido, insira um nome válido e tente novamente.");
			
			//retorna o erro ao código principal
			return resposta;
		} //verifica o tamanho do nome
		else if (jogador.getNome().length() > 15) {
			//cria um JSON para montar a resposta (informar ao código principal que está inválido)
			JsonObject resposta = new JsonObject();

			//adiciona no JSON as chaves de código de erro e descrição
			resposta.addProperty("código", "3");
			resposta.addProperty("descrição", "Nome muito longo, insira um nome válido e tente novamente.");
			
			//retorna o erro ao código principal
			return resposta;
		} //verifica se tem jogador no tabuleiro
		else if(listaJogadores.size() == 1) {
			//já tem jogador
			//verifica se jogador botou o mesmo nome que o outro
			Map.Entry<String, Jogador> jogadorNaLista = listaJogadores.entrySet().iterator().next();
			if(!id.equalsIgnoreCase(jogadorNaLista.getKey()) && nome.toLowerCase().equalsIgnoreCase(jogadorNaLista.getValue().getNome().toLowerCase())) {
				//cria um JSON para montar a resposta (informar ao código principal que está inválido)
				JsonObject resposta = new JsonObject();

				//adiciona no JSON as chaves de código de erro e descrição
				resposta.addProperty("código", "3");
				resposta.addProperty("descrição", "Nome já está em uso, insira um nome diferente e tente novamente.");
			
				//retorna o erro ao código principal
				return resposta;
			}
		}

		//passou nas verificações
		//cria um JSON para montar a resposta (informar ao código principal que está inválido)
		return montaRespotaJsonObject("0", "Sucesso! Nome válido!");
			
		//retorna o erro ao código principal
		return resposta;
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

	
	public JsonObject montaRespotaJsonObject(String codigo, String descricao) {
		//cria um JSON para montar a resposta (informar ao código principal que está inválido)
		JsonObject resposta = new JsonObject();

		//adiciona no JSON as chaves de código de erro e descrição
		resposta.addProperty("código", codigo);
		resposta.addProperty("descrição", descricao);
			
		//retorna o erro ao código principal
		return resposta;
	}

}