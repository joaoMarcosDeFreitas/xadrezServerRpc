package com.xadrez.grpc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.gson.JsonObject;
import com.xadrez.models.Jogador;

import io.grpc.Status;

public class XadrezServiceImpl extends XadrezServiceGrpc.XadrezServiceImplBase {
	//lista de jogadores presentes quando o servidor está ativo
	HashMap<String, Jogador> listaJogadores = new HashMap<>();
	
	//synchronized impede que seja acessado ao mesmo tempo. Tratando race condition
	//método de conectar jogador na partida
	@Override
	public synchronized void conectarJogador(ConectarRequest request, io.grpc.stub.StreamObserver<com.xadrez.grpc.ConectarResponse> responseObserver) {
		//Cria o jogador para fazer as verificações com suas informações
		//ID e nome são definidos pelo front. Aqui só valida e define a cor.
		Jogador jogador = new Jogador(request.getIdJogador().trim(), request.getNomeJogador().trim(), null);

		//verificações se a requisição foi válida (jogador criado está de acordo)
		jogador.verificaId()

		//verificar se jogador está na lista
		if (listaJogadores.containsKey(jogador.getId())) {	
			//busca o jogador na lista
			jogador = listaJogadores.get(jogador.getId());

			//inicializa um objeto JSON para receber a resposta da verificação do nome
			JsonObject respostaVerificacao = jogador.verificaNome(listaJogadores, jogador);

			if(respostaVerificacao.get("código").getAsInt() == 3) {
				//cria a exceção para retornar erro
				Throwable excecao = Status.INVALID_ARGUMENT.withDescription(respostaVerificacao.get("descrição").getAsString()).asRuntimeException(); 
			
				//lança o erro ao usuário
				responseObserver.onError(excecao);
				//retorna para nao dar seguimento na função
				return;
			}

			//edita o nome do jogador para o que foi enviado agora
			jogador.setNome(nome);
			
			//montar resposta
			ConectarResponse respostaServer = ConectarResponse.newBuilder()
				.setIdJogador(jogador.getId())
				.setNomeJogador(jogador.getNome())
				.setCorPecas(jogador.getCorPecas())
				.setStatus(jogador.getStatus())
				.build();
			
			//enviar resposta
			responseObserver.onNext(respostaServer);
		
			//fechar resposta
			responseObserver.onCompleted();
			//retorna pra parar o código
			return;
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
		} //se o jogador foi o 2° a entrar registra e começa o jogo
		else if() {

		} //tentou entrar em sala cheia
		else {
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

		//verifica se o jogador foi encontrado
		if(jogador == null) {
			//cria a exceção para retornar erro
			Throwable excecao = Status.INVALID_ARGUMENT.withDescription("ID não encontrado. Não é possível realizar uma jogada sem jogador.").asRuntimeException(); 
			
			//lança o erro ao usuário
			responseObserver.onError(excecao);
			//retorna para nao dar seguimento na função
			return;
		}
		
		//veriica se solicitou de uma partida existente
	}
}
