package com.xadrez.grpc;

import com.xadrez.models.Jogador;

import io.grpc.Status;

public class XadrezServiceImpl extends XadrezServiceGrpc.XadrezServiceImplBase {
	//O synchronized durante todos os métodos que comunicam com o servidor (recebendo ou enviando dados) servem para não permitir que duas chamadas ocorram simultâneamente. Dessa forma, ele impede o race condition.
	
	//método de conectar jogador na partida
	@Override
	public synchronized void conectarJogador(ConectarRequest request, io.grpc.stub.StreamObserver<com.xadrez.grpc.ConectarResponse> responseObserver) {
		//Cria o jogador para fazer as verificações com suas informações
		//ID e nome são definidos pelo front. Aqui só valida essas informações.
		Jogador jogador = new Jogador(request.getIdJogador().trim(), request.getNomeJogador().trim());

		//verifica se o ID passado está válido (não é vazio)
		if(jogador.verificaId().get("código").getAsInt() == 3) 
			responderServidorErro(responseObserver, jogador.verificaId().get("descrição").getAsString());
		
		//verifica se o nome passado está válido (não é vazio e tem tamanho adequado)
		if(jogador.verificaNome().get("código").getAsInt() == 3) 
			responderServidorErro(responseObserver, jogador.verificaNome().get("descrição").getAsString());
		
		//após verificações sobre o jogador, é hora de verificar a partida (se está livre para entrada de jogadores, qual cor o jogador vai receber e se está reconectando)
		
	}
	
	//método para responder o servidor com erro. (REFERENTE A CONEXÃO DE JOGADOR)
	public void responderServidorErro(io.grpc.stub.StreamObserver<com.xadrez.grpc.ConectarResponse> responseObserver, String descricao) {
		//cria a exceção para retornar erro
		Throwable excecao = Status.FAILED_PRECONDITION.withDescription(descricao).asRuntimeException(); 
			
		//lança o erro ao usuário
		responseObserver.onError(excecao);
		//retorna para nao dar seguimento na função
		return;
	}
		
	//método para responder o servidor com sucesso. (REFERENTE A CONEXÃO DE JOGADOR)
	public void responderServidorSucesso(io.grpc.stub.StreamObserver<com.xadrez.grpc.ConectarResponse> responseObserver, String descricao) {
		
	}

	//método para computar as jogadas do jogador
	@Override 
	public synchronized void enviarJogada(JogadaRequest request, io.grpc.stub.StreamObserver<com.xadrez.grpc.JogadaResponse> responseObserver) {
		
	}
}