package com.xadrez.grpc;

import java.util.ArrayList;
import java.util.UUID;

import com.google.gson.JsonObject;
import com.xadrez.models.Jogador;
import com.xadrez.models.Partida;

import io.grpc.Status;

public class XadrezServiceImpl extends XadrezServiceGrpc.XadrezServiceImplBase {
	//O synchronized durante todos os métodos que comunicam com o servidor (recebendo ou enviando dados) servem para não permitir que duas chamadas ocorram simultâneamente. Dessa forma, ele impede o race condition.
	
	//inicia a partida assim que o servidor liga
	Partida partida = new Partida(UUID.randomUUID().toString());
	
	//método de conectar jogador na partida
	@Override
	public synchronized void conectarJogador(ConectarRequest request, io.grpc.stub.StreamObserver<com.xadrez.grpc.ConectarResponse> responseObserver) {
		//verificações da partida (se está livre para entrada de jogadores e quais cores estão livres -> assim define quantos jogadores tem em sala)
		
		//verifica se o jogo está cheio (2 jogadores entraram)
		JsonObject verificaSeJogoEstaCheio = partida.verificaSeJogoEstaCheio();
		if(verificaSeJogoEstaCheio.get("código").getAsString().equals("9")) {
			//utiliza o método para retornar erro ao servidor
			responderServidorErro(responseObserver, verificaSeJogoEstaCheio.get("código").getAsString(), verificaSeJogoEstaCheio.get("descrição").getAsString());
			//return para parar o código
			return;
		}
		
		//jogo tem vaga (pode ser uma ou duas). De qualquer forma, precisamos iniciar o jogador que vai entrar.
		//cria o objeto jogador com ID novo ou se já foi enviado um ID ele cria com o ID enviado
		Jogador jogador = new Jogador(
				request.getIdJogador().isBlank() ? 
						UUID.randomUUID().toString() : request.getIdJogador().trim(), 
				request.getNomeJogador().trim()
		);
		
		//verifica se o nome passado está válido (não é vazio e tem tamanho adequado)
		JsonObject verificaNome = jogador.verificaNome(partida.listaJogadores());
		if(verificaNome.get("código").getAsString().equals("9")) {
			//utiliza o método para retornar erro ao servidor
			responderServidorErro(responseObserver, verificaNome.get("código").getAsString(), verificaNome.get("descrição").getAsString());
			//return para parar o código
			return;
		}
		//verifica se o nome não está repetido
			
		
		//verifica cores disponíveis no jogo. Aqui é definido quantas vagas sobraram
		ArrayList<Partida.CorPecas> coresDisponiveis = partida.corDisponivel();
		//verificação de precaução mesmo que o jogo tenha vindo como não cheio acima
		if(coresDisponiveis.isEmpty()) {
			//utiliza o método para retornar erro ao servidor
			responderServidorErro(responseObserver, "9", "Jogo está cheio! Tente novamente mais tarde");
			//return para parar o código
			return;
		}
		//Adiciona a cor ao jogador, sempre com índice 0 para garantir que pega sempre o primeiro. (Quem entra primeiro sempre é branca)
		partida.adicionarCorPecasJogadores(coresDisponiveis.get(0), jogador);
		
		
	}
	
	//método para responder o servidor com erro. (REFERENTE A CONEXÃO DE JOGADOR)
	public void responderServidorErro(io.grpc.stub.StreamObserver<com.xadrez.grpc.ConectarResponse> responseObserver, String codigo, String descricao) {
		//inicia a exceção que será jogada ao usuário (para saber porque não funcionou)
		Throwable excecao;
		//switch para definir qual código foi usado.
		switch(codigo) {
			case "9":
				//cria a exceção para retornar erro
				excecao = Status.FAILED_PRECONDITION.withDescription(descricao).asRuntimeException(); 
				break;
			default:
				//cria a exceção para retornar erro
				excecao = Status.UNKNOWN.withDescription(descricao).asRuntimeException(); 
		}
		//lança o erro ao usuário
		responseObserver.onError(excecao);
		//retorna para terminar a resposta
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