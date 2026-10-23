package com.xadrez.models;

import java.util.EnumMap;
import java.util.Map;

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

	public Map<CorPecas, String> getCorPecasJogadores() {
		return corPecasJogadores;
	}

	public void adicionarCorPecasJogadores(String idJogador) {
		
	}
}