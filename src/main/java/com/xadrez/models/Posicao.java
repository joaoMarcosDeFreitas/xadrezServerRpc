package com.xadrez.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Posicao {
	private String posicaoOrigem;
	private String posicaoDestino;
	private String coluna;
	private String linha;
	
	public Posicao(String posicaoOrigem, String posicaoDestino) {
		super();
		this.posicaoOrigem = posicaoOrigem;
		this.posicaoDestino = posicaoDestino;
	}
	
	public String getColuna() {
		return coluna;
	}
	
	public String getLinha() {
		return linha;
	}

	public String getPosicaoOrigem() {
		return posicaoOrigem;
	}

	public void setPosicaoOrigem(String posicaoOrigem) {
		this.posicaoOrigem = posicaoOrigem;
	}

	public String getPosicaoDestino() {
		return posicaoDestino;
	}

	public void setPosicaoDestino(String posicaoDestino) {
		this.posicaoDestino = posicaoDestino;
	}

	public void conversorDeMovimento() {
		//validador de padrão
		Pattern padraoBuscado = Pattern.compile("\\b[a-h][1-8]\\b", Pattern.CASE_INSENSITIVE);
		Matcher buscaPadrao = padraoBuscado.matcher(this.posicaoDestino);	
		
		//padrão encontrado, são validas as posições
		if(buscaPadrao.find()) {
			//atribui a letra a linha
			this.linha = this.posicaoDestino.split("[a-h]")[0];
			
			//atribui o numero a coluna
			this.coluna = this.posicaoDestino.split("[a-h]")[1];
			
			
		}
	}
}
