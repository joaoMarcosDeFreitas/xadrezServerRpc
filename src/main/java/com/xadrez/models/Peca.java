package com.xadrez.models;

import com.xadrez.models.enums.CorPecaEnum.CorPecas;
import com.xadrez.models.enums.PecaEnum.TipoPeca;

public class Peca {
	private final TipoPeca tipoPeca;
	private final CorPecas cor;
	private boolean jaMexeu;
	
	public Peca(TipoPeca tipoPeca, CorPecas cor, boolean jaMexeu) {
		super();
		this.tipoPeca = tipoPeca;
		this.cor = cor;
		this.jaMexeu = jaMexeu;
	}
	
	public TipoPeca getTipoPeca() {
		return tipoPeca;
	}

	public CorPecas getCor() {
		return cor;
	}

	public boolean isJaMexeu() {
		return jaMexeu;
	}

	public void setJaMexeu(boolean jaMexeu) {
		this.jaMexeu = jaMexeu;
	}
	
	
}
