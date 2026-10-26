package com.xadrez.utils;

import com.google.gson.JsonObject;

//classe para reaproveitar códigos (métodos e funções) que são utilizados em muitos locais e não pertencem a um grupo particular
public class Utils {
	//método para montar a resposta em JSON que é enviada ao IMPL
	public static JsonObject montaRespotaJsonObject(String codigo, String descricao) {
		//cria um JSON para montar a resposta (informar ao código principal que está inválido)
		JsonObject resposta = new JsonObject();

		//adiciona no JSON as chaves de código de erro e descrição
		resposta.addProperty("código", codigo);
		resposta.addProperty("descrição", descricao);
				
		//retorna o erro ao código principal
		return resposta;
	}
}
