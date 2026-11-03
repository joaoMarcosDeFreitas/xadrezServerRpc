package com.xadrez.models;

import com.xadrez.models.enums.CorPecaEnum.CorPecas;

public class Peca {
	private enum tipo {
		PEAO,
		TORRE,
		CAVALO,
		BISPO,
		RAINHA,
		REI
	}
	private CorPecas cor;
	private boolean jaMexeu;
	
	public Peca(CorPecas cor, boolean jaMexeu) {
		super();
		this.cor = cor;
		this.jaMexeu = jaMexeu;
	}

	public CorPecas getCor() {
		return cor;
	}

	public void setCor(CorPecas cor) {
		this.cor = cor;
	}

	public boolean isJaMexeu() {
		return jaMexeu;
	}

	public void setJaMexeu(boolean jaMexeu) {
		this.jaMexeu = jaMexeu;
	}
	
	
}
