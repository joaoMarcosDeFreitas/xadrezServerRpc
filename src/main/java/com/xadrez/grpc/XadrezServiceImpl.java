package com.xadrez.grpc;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class XadrezServiceImpl extends XadrezServiceGrpc.XadrezServiceImplBase {
	//concurrent para tratar os problemas de race condition
	ConcurrentHashMap<String, String> listaJogadores = new ConcurrentHashMap<>();
	
	@Override
	public synchronized void conectarJogador(ConectarRequest request, io.grpc.stub.StreamObserver<com.xadrez.grpc.ConectarResponse> responseObserver) {
		//coleta de dados ao conectar
		String nome = request.getNomeJogador();
		
		
		
		//verificar se jogador está conectado
		if(!listaJogadores.containsKey(request.getIdJogador())) {
			//cria o UUID do user
			UUID uuid = UUID.randomUUID();
					
		}
		
		//monta a resposta para o servidor
		ConectarResponse respostaServer = ConectarResponse.newBuilder()
				.setIdJogador("2")
				.build();
		
		//envia a resposta ao servidor
		responseObserver.onNext(respostaServer);
		responseObserver.onCompleted();
	}
}
