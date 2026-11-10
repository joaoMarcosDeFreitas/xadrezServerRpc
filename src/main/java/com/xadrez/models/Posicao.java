package com.xadrez.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Posicao {
	private final String coluna;
	private final String linha;
	
	public Posicao(String posicaoDestino) {
		super();
		//validador de padrão
		Pattern padraoBuscado = Pattern.compile("\\b[a-h][1-8]\\b", Pattern.CASE_INSENSITIVE);
		Matcher buscaPadrao = padraoBuscado.matcher(posicaoDestino);	
				
		//padrão encontrado, são validas as posições
		if(buscaPadrao.find()) {
			//atribui a letra a linha
			this.linha = posicaoDestino.split("[a-h][A-H]")[0];
					
			//atribui o numero a coluna
			this.coluna = posicaoDestino.split("[a-h][A-H]")[1];
		} else {
			this.linha = null;
			this.coluna = null;
		}
	}
	
	public String getColuna() {
		return coluna;
	}
	
	public String getLinha() {
		return linha;
	}
}
