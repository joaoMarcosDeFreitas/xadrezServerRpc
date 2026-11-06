package com.xadrez.models;

import com.xadrez.models.enums.CorPecaEnum.CorPecas;
import com.xadrez.models.enums.PecaEnum.TipoPeca;

public class Peca {
	private final TipoPeca tipoPeca;
	private final CorPecas cor;
	private boolean jaMexeu;
	private String textoTipo;
	private String textoCor;
	
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

	public String getTextoTipo() {
		return textoTipo;
	}

	public void setTextoTipo(String textoTipo) {
		this.textoTipo = textoTipo;
	}

	public String getTextoCor() {
		return textoCor;
	}

	public void setTextoCor(String textoCor) {
		this.textoCor = textoCor;
	}
	
	//método para retornar o nome da peça
	public String nomePeca() {
		return this.tipoPeca.toString();
	}
}
