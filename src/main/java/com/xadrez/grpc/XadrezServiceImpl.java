package com.xadrez.grpc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.xadrez.models.Jogador;

import io.grpc.Status;

public class XadrezServiceImpl extends XadrezServiceGrpc.XadrezServiceImplBase {
	//lista de jogadores presentes quando o servidor está ativo
	HashMap<String, Jogador> listaJogadores = new HashMap<>();
	
	//synchronized impede que seja acessado ao mesmo tempo. Tratando race condition
	//método de conectar jogador na partida
	@Override
	public synchronized void conectarJogador(ConectarRequest request, io.grpc.stub.StreamObserver<com.xadrez.grpc.ConectarResponse> responseObserver) {
		//coleta de dados ao conectar
		String id = request.getIdJogador().trim();
		String nome = request.getNomeJogador().trim();
		
		//verifica se nome está vazio
		if(nome.isEmpty()) {
			//cria a exceção para retornar erro
			Throwable excecao = Status.INVALID_ARGUMENT.withDescription("Nome inválido, insira um nome válido e tente novamente.").asRuntimeException(); 
			
			//lança o erro ao usuário
			responseObserver.onError(excecao);
			//retorna para nao dar seguimento na função
			return;
		} //verifica o tamanho do nome
		else if (nome.length() > 15) {
			//cria a exceção para retornar erro
			Throwable excecao = Status.INVALID_ARGUMENT.withDescription("Nome muito longo, insira um nome válido e tente novamente.").asRuntimeException(); 
			
			//lança o erro ao usuário
			responseObserver.onError(excecao);
			//retorna para nao dar seguimento na função
			return;
		} 
		
		//verificar se jogador está na lista
		if (listaJogadores.containsKey(id)) {	
			//busca o jogador na lista
			Jogador jogador = listaJogadores.get(id);
			jogador.setNome(nome);
			
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
			//retorna pra parar o código
			return;
		}
		 
		//verifica se tem jogador no tabuleiro
		if(listaJogadores.size() == 1) {
			//já tem jogador
			//verifica se jogador botou o mesmo nome que o outro
			Map.Entry<String, Jogador> jogador = listaJogadores.entrySet().iterator().next();
			if(!id.equalsIgnoreCase(jogador.getKey()) && nome.toLowerCase().equalsIgnoreCase(jogador.getValue().getNome().toLowerCase())) {
				//cria a exceção para retornar erro
				Throwable excecao = Status.INVALID_ARGUMENT.withDescription("Nome já está em uso, mude e tente novamente.").asRuntimeException(); 
				
				//lança o erro ao usuário
				responseObserver.onError(excecao);
				//retorna para nao dar seguimento na função
				return;
			}
		}

		//verifica se pode entrar no jogo
		if(listaJogadores.size() < 2) {
			//pode entrar no jogo
			//gera id aleatório
			id = UUID.randomUUID().toString();
			//cria jogador sem cor e status
			Jogador jogador = new Jogador(id, nome, "", "");
			//define a cor da peça do jogador
			jogador.defineEAtribuiCorPecas(listaJogadores);
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
			//retorna para evitar que siga o código
			return;
		} else {
			//cria a exceção para retornar erro
			Throwable excecao = Status.FAILED_PRECONDITION.withDescription("Sala cheia. Tente novamente mais tarde").asRuntimeException(); 
			
			//lança o erro ao usuário
			responseObserver.onError(excecao);
			//retorna para nao dar seguimento na função
			return;
		}
	}

	//synchronized impede que seja acessado ao mesmo tempo. Tratando race condition
	//método para computar as jogadas do jogador
	@Override 
	public synchronized void enviarJogada(JogadaRequest request, io.grpc.stub.StreamObserver<com.xadrez.grpc.JogadaResponse> responseObserver) {
		if(request.getIdJogador().isBlank()) {
			//cria a exceção para retornar erro
			Throwable excecao = Status.INVALID_ARGUMENT.withDescription("Não é possível realizar uma jogada sem ID de Jogador.").asRuntimeException(); 
			
			//lança o erro ao usuário
			responseObserver.onError(excecao);
			//retorna para nao dar seguimento na função
			return;
		}

		//coleta o jogador que mandou a jogada
		Jogador jogador = listaJogadores.get(request.getIdJogador().trim());

		//verifia se o jogador foi encontrado
		if(jogador == null) {
			//cria a exceção para retornar erro
			Throwable excecao = Status.INVALID_ARGUMENT.withDescription("ID não encontrado. Não é possível realizar uma jogada sem jogador.").asRuntimeException(); 
			
			//lança o erro ao usuário
			responseObserver.onError(excecao);
			//retorna para nao dar seguimento na função
			return;
		}

	}
}
