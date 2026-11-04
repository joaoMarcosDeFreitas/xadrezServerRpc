package com.xadrez.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Posicao {
	private String posicaoOrigem;
	private String posicaoDestino;
	public String coluna;
	public String linha;
	
	public Posicao(String posicaoOrigem, String posicaoDestino) {
		super();
		this.posicaoOrigem = posicaoOrigem;
		this.posicaoDestino = posicaoDestino;
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

	public JsonObject conversorDeMovimento() {
		Pattern padraoBuscado = Pattern.compile("\\b[a-h][1-8]\\b", Pattern.CASE_INSENSITIVE);
		Matcher buscaPadrao = padraoBuscado.matcher(posicaoDestino);	
		
		if(buscaPadrao.find()) {
			Pattern padraoLinhaBuscado = Pattern.compile("[a-h]", Pattern.CASE_INSENSITIVE);
			Matcher buscaLinhaPadrao = padraoLinhaBuscado.matcher(posicaoDestino);
			
			String linha;
		}
	}
}
