package com.xadrez.models;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import com.xadrez.utils.*;

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
			return Utils.montaRespotaJsonObject("9", "Jogo está cheio! Tente novamente mais tarde");
		}
		
		//cria um JSON para montar a resposta (informar ao código principal que está inválido)
		return Utils.montaRespotaJsonObject("0", "Sucesso! Sala tem vaga!");
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
		//verifica se é o mesmo jogador enviado de novo
		for(Jogador jogador : jogadores.values()) 
			if(jogadorParaAdicionar.getId().equals(jogador.getId()))
				return Utils.montaRespotaJsonObject("9", "Jogador já entrou no jogo. Aguarde seu oponente!");
		
		//adiciona a cor e o jogador ao mapa de jogadores
		this.jogadores.put(cor, jogadorParaAdicionar);
		//retorna a mensagem de sucesso (apenas por prática, não é mostrada)
		return Utils.montaRespotaJsonObject("0", "Sucesso! Jogador adicionado ao jogo");
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
}