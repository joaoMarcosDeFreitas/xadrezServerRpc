package com.xadrez.models;

import com.google.gson.JsonObject;

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

	//método para verificar se o ID atende as regras
	public JsonObject verificaId() {
		//verifica se ID está vazio
		if(id.isBlank()) {
			//retorna o erro ao código principal
			return montaRespotaJsonObject("3", "Nome inválido, insira um nome válido e tente novamente.");
		}
		
		return montaRespotaJsonObject("0", "Sucesso! ID válido.");
	}

	//método para verificar se o nome atende as regras
	public JsonObject verificaNome() {
		//verifica se nome está vazio
		if(this.nome.isEmpty() || (this.nome.length() > 15 || this.nome.length() < 3)) {
			//retorna o erro ao código principal
			return montaRespotaJsonObject("3", "Nome inválido, insira um nome válido e tente novamente. Nome deve ser maior que 3 carácteres, menor que 15 e não pode estar vazio.");
		}
		
		//cria um JSON para montar a resposta (informar ao código principal que está inválido)
		return montaRespotaJsonObject("0", "Sucesso! Nome válido!");
	}
	
	//método para montar a resposta em JSON que é enviada ao IMPL
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