package com.xadrez.models;

public class Tabuleiro {
	private final Peca[][] tabuleiro;

	public Tabuleiro() {
		super();
		this.tabuleiro = new Peca[8][8];
	}
	
	public Peca[][] getTabuleiro() {
		return tabuleiro;
	}
	
	
}
