package com.xadrez.models;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

import com.xadrez.models.enums.CorPecaEnum.CorPecas;
import com.xadrez.utils.*;

import com.google.gson.JsonObject;

public class Partida {
    private String idPartida;
	public CorPecas corPecas;
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
	
	//verifica se o jogador já entrou na partida
	public JsonObject verificaSeJogadorEntrou(Jogador jogadorParaAdicionar) {
		//looping na lista de jogadores
		for(Jogador jogador : jogadores.values()) {
			//verifica se o ID é igual
			if(jogadorParaAdicionar.getId().equals(jogador.getId())) 
				//cria um JSON para montar a resposta (informar ao código principal que está inválido)
				return Utils.montaRespotaJsonObject("9", "Jogador já entrou no jogo. Aguarde seu oponente!");
		}
		
		//cria um JSON para montar a resposta (informar ao código principal que está inválido)
		return Utils.montaRespotaJsonObject("0", "Sucesso! Jogador pode entrar na partida.");
	}
	
	//adiciona um jogador ao jogo
	public void adicionarCorPecasJogadores(CorPecas cor, Jogador jogadorParaAdicionar) {
		//adiciona a cor e o jogador ao mapa de jogadores
		this.jogadores.put(cor, jogadorParaAdicionar);
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