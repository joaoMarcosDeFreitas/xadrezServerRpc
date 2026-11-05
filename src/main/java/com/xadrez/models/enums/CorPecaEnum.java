package com.xadrez.models.enums;

public class CorPecaEnum {
	public enum CorPecas {
		BRANCAS, 
		PRETAS;
		
		public CorPecas oposta() {
			return this == BRANCAS ? PRETAS : BRANCAS;
		}
	}
}
