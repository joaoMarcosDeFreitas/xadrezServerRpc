package com.xadrez.grpc;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.xadrez.models.Jogador;

public class XadrezServiceImpl extends XadrezServiceGrpc.XadrezServiceImplBase {
	//concurrent para tratar os problemas de race condition
	ConcurrentHashMap<String, Jogador> listaJogadores = new ConcurrentHashMap<>();
	
	@Override
	public synchronized void conectarJogador(ConectarRequest request, io.grpc.stub.StreamObserver<com.xadrez.grpc.ConectarResponse> responseObserver) {
		//coleta de dados ao conectar
		String id = request.getIdJogador();
		String nome = request.getNomeJogador();
		
		//verifica quantos jogadores estão presentes
		if(listaJogadores.size() < 2) {
			//verifica se usuário tem id
			if(id.isEmpty() || id == null || id == "") {
				//usuário sem id = usuário novo
				//cria o id do usuário
				id = UUID.randomUUID().toString();
				//faz a decisão da cor
				//cria objeto jogador com status ativo
			}else {
				if(!listaJogadores.containsKey(id)) {
					//faz a decisão da cor
					//cria objeto jogador com status ativo
				}
			}
		}
		
		//monta a resposta para o servidor
		ConectarResponse respostaServer = ConectarResponse.newBuilder()
				.setIdJogador(id)
				.build();
		
		//envia a resposta ao servidor
		responseObserver.onNext(respostaServer);
		responseObserver.onCompleted();
	}
	
	//método de decisão de cor
	
	//método de criar objeto jogador
}
