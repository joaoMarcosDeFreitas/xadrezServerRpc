package com.xadrez.grpc;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import com.xadrez.models.Jogador;

import io.grpc.Status;

public class XadrezServiceImpl extends XadrezServiceGrpc.XadrezServiceImplBase {
	HashMap<String, Jogador> listaJogadores = new HashMap<>();
	
	//synchronized impede que seja acessado ao mesmo tempo. Tratando race condition
	@Override
	public synchronized void conectarJogador(ConectarRequest request, io.grpc.stub.StreamObserver<com.xadrez.grpc.ConectarResponse> responseObserver) {
		//coleta de dados ao conectar
		String id = request.getIdJogador();
		String nome = request.getNomeJogador();
		
		//verifica se nome está vazio
		if(nome.trim().isEmpty()) {
			//cria a exceção para retornar erro
			Throwable excecao = Status.INVALID_ARGUMENT.withDescription("Nome inválido, insira um nome diferente e tente novamente").asRuntimeException(); 
			
			//lança o erro ao usuário
			responseObserver.onError(excecao);
			//retorna para nao dar seguimento na função
			return;
		}
		
		//verifica se pode entrar no jogo
		if(listaJogadores.size() < 2) {
			
			//pode entrar no jogo
			
			//verifica se já tem id
			if(id.isEmpty()) {
				//gera id aleatório
				id = UUID.randomUUID().toString();
				//cria jogador sem cor e status
				Jogador jogador = new Jogador(id, nome, "", "");
				//define a cor da peça do jogador
				jogador.setCorPecas(defineCorPecas(listaJogadores, jogador));
				//define status ativo
				jogador.setStatus("Ativo");
				//envia para lista de jogadores
				listaJogadores.put(id, jogador);
			
				//resposta ao servidor
				
				//montar resposta
				ConectarResponse resposta = ConectarResponse.newBuilder()
					.setIdJogador(jogador.getId())
					.setNomeJogador(jogador.getNome())
					.setCorPecas(jogador.getCorPecas())
					.setStatus(jogador.getStatus())
					.build();
				
				//enviar resposta
				responseObserver.onNext(resposta);
			
				//fechar resposta
				responseObserver.onCompleted();
				
				//já tem id, busca na lista e responde com dados já enviados
			} else if(listaJogadores.containsKey(id)) {
				//busca o jogador na lista
				Jogador jogador = listaJogadores.get(id);
				
				//montar resposta
				ConectarResponse resposta = ConectarResponse.newBuilder()
					.setIdJogador(jogador.getId())
					.setNomeJogador(jogador.getNome())
					.setCorPecas(jogador.getCorPecas())
					.setStatus(jogador.getStatus())
					.build();
				
				//enviar resposta
				responseObserver.onNext(resposta);
			
				//fechar resposta
				responseObserver.onCompleted();
			} else {
				//cria a exceção para retornar erro
				Throwable excecao = Status.NOT_FOUND.withDescription("ID não encontrado. Tente reiniciar completamente a aplicação.").asRuntimeException(); 
				
				//lança o erro ao usuário
				responseObserver.onError(excecao);
				//retorna para nao dar seguimento na função
				return;
			}
		} else {
			//cria a exceção para retornar erro
			Throwable excecao = Status.FAILED_PRECONDITION.withDescription("Sala cheia. Tente novamente mais tarde").asRuntimeException(); 
			
			//lança o erro ao usuário
			responseObserver.onError(excecao);
			//retorna para nao dar seguimento na função
			return;
		}
	}
	
	public String defineCorPecas(HashMap<String, Jogador> listaJogadores, Jogador jogador) {
		//inicializa a cor como nada
		String corPecas = "";
		
		//inicializa o randomizador
		Random randomizador = new Random();
		
		//não tem jogador no tabuleiro
		if(listaJogadores.size() == 0) 
			//define aleatoriamente a cor
			corPecas = randomizador.nextInt(2) == 0 ? "Brancas" : "Pretas";
			//já tem um jogador
		else if(listaJogadores.size() == 1) 
			//define a cor por eliminação
			corPecas = listaJogadores.values().iterator().next().getCorPecas().equals("Brancas") ? "Pretas" : "Brancas";
		
		return corPecas;
	}
}
