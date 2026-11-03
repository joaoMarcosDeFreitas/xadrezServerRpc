package com.xadrez.models;

public class Posicao {
	private String coluna;
	private String linha;
	
	public Posicao(String coluna, String linha) {
		super();
		this.coluna = coluna;
		this.linha = linha;
	}
	
	public String getColuna() {
		return coluna;
	}
	
	public void setColuna(String coluna) {
		this.coluna = coluna;
	}
	
	public String getLinha() {
		return linha;
	}
	
	public void setLinha(String linha) {
		this.linha = linha;
	}
	
	
}
