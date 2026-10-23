package com.xadrez.models;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

import com.google.gson.JsonObject;

public class Partida {
    private String idPartida;
	public enum CorPecas {
		BRANCAS, PRETAS
	}
	private final Map<CorPecas, Jogador> jogadores = new EnumMap<>(CorPecas.class);
    
	public Partida(String idPartida) {
		this.idPartida = idPartida;
	}

	public String getIdPartida() {
		return idPartida;
	}

	public void setIdPartida(String idPartida) {
		this.idPartida = idPartida;
	}

	//verifica se já tem 2 jogadores
	public JsonObject verificaSeJogoEstaCheio() {
		if(jogadores.size() == 2) {
			//cria um JSON para montar a resposta (informar ao código principal que está inválido)
			return montaRespotaJsonObject("9", "Jogo está cheio! Tente novamente mais tarde");
		}
		
		//cria um JSON para montar a resposta (informar ao código principal que está inválido)
		return montaRespotaJsonObject("0", "Sucesso! Nome válido!");
	}

	//verifica qual cor está disponível
	public ArrayList<CorPecas> corDisponivel() {
		//cria um array de CorPecas para armazenas os disponíveis
		ArrayList<CorPecas> listaDeDisponibilidade = new ArrayList<>();
		//looping para preencher quais são as cores disponíveis (as duas ou só uma)
		for(CorPecas cor : CorPecas.values()) {
			if(!jogadores.containsKey(cor)) {
				listaDeDisponibilidade.add(cor);
			}
		}
		return listaDeDisponibilidade;
	}
	
	//adiciona um jogador ao jogo
	public JsonObject adicionarCorPecasJogadores(CorPecas cor, Jogador jogadorParaAdicionar) {
		//percorre a lista de jogadores 
		for(Jogador jogador : jogadores.values()) {
			//verifica se é um jogador diferente do que já está na lista
			if(!jogador.getId().equals(jogadorParaAdicionar.getId())) {
				//adiciona a cor e o jogador ao mapa de jogadores
				this.jogadores.put(cor, jogadorParaAdicionar);
				//retorna a mensagem de sucesso (apenas por prática, não é mostrada)
				return montaRespotaJsonObject("0", "Sucesso! Jogador adicionado ao jogo");
			}
		}
		//cria um JSON para montar a resposta (informar ao código principal que está inválido)
		return montaRespotaJsonObject("9", "Erro! Jogador já está presente no jogo.");
	}
	
	//pega a lista de jogadores que está presente
	public ArrayList<Jogador> listaJogadores() {
		//inicia o array de jogadores
		ArrayList<Jogador> jogadoresNaLista = new ArrayList<>();
		//percorre a lista de jogadores
		for(Jogador jogador : jogadores.values()) {
			//adiciona no array de jogadores os jogadores na lista
			jogadoresNaLista.add(jogador);
		}
		//retorna os jogadores
		return jogadoresNaLista;
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