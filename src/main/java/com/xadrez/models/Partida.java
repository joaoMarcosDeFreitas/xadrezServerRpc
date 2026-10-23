package com.xadrez.models;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

import com.google.gson.JsonObject;

public class Partida {
    private String idPartida;
	private enum CorPecas {
		BRANCAS, PRETAS
	}
	public final Map<CorPecas, String> corPecasJogadores = new EnumMap<>(CorPecas.class);
    
	public Partida(String idPartida) {
		this.idPartida = idPartida;
	}

	public String getIdPartida() {
		return idPartida;
	}

	public void setIdPartida(String idPartida) {
		this.idPartida = idPartida;
	}

	//buscar o mapa de jogadores através das cores (brancas e pretas)
	public Map<CorPecas, String> getCorPecasJogadores() {
		return corPecasJogadores;
	}

	//verifica se já tem 2 jogadores
	public Boolean verificaSeJogoEstaCheio() {
		return corPecasJogadores.size() == 2;
	}

	//verifica qual cor está disponível
	public ArrayList<CorPecas> corDisponivel() {
		ArrayList<CorPecas> listaDeDisponibilidade = new ArrayList<>();

		for(CorPecas cor : CorPecas.values()) {
			if(!corPecasJogadores.containsKey(cor)) {
				listaDeDisponibilidade.add(cor);
			}
		}

		return listaDeDisponibilidade;
	}

	//adiciona um jogador ao jogo
	public void adicionarCorPecasJogadores(String idJogador) {
		
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