# Etapas que faltam — Back-end do Xadrez (servidor gRPC)

Regra de trabalho: uma sub-etapa por vez. Item resolvido e conferido sai deste arquivo.

## Etapa 1 — Pré-jogo e reconexão
- 1.1 `Partida` localiza um jogador pelo id e informa nome e cor dele.
- 1.2 Reconexão verificada primeiro no `conectarJogador`: id conhecido recebe de volta id, partida, nome cadastrado e cor, mesmo com a sala cheia. O nome enviado é ignorado.
- 1.3 Sala cheia só barra jogador novo, com `FAILED_PRECONDITION`.
- 1.4 Id enviado que o servidor não conhece é ignorado e substituído por um id novo.
- 1.5 `Partida` protege a entrada sozinha: máximo de 2 jogadores, sem repetir jogador e sem sobrescrever cor ocupada.
- 1.6 Estado explícito da partida (aguardando, em andamento, finalizada).
- 1.7 Cor da vez; ao entrar o segundo jogador, a partida passa para "em andamento" com o turno das brancas.
- 1.8 Limpezas: cor oposta no enum de cor, remover `setIdPartida` e o campo público `corPecas`.
- 1.9 `Main`: a mensagem e a porta precisam ser a mesma.

## Etapa 2 — Peças e coordenadas
- 2.2 Representação em texto da peça, para o estado enviado ao cliente.
- 2.3 `Posicao` com coluna e linha imutáveis; posição inválida não pode ser criada (hoje a classe não compila).
- 2.4 Criar `Posicao` a partir de texto como "e2" e converter para índices da matriz.
- 2.5 Texto da posição, deslocamento até outra posição e igualdade.

## Etapa 3 — Tabuleiro
- 3.1 Esconder a matriz e ler uma casa.
- 3.2 Montar a posição inicial padrão.
- 3.3 Informar se o caminho entre duas casas está livre.
- 3.4 Mover e capturar, recusando mover de casa vazia e capturar peça da mesma cor.
- 3.5 Localizar o rei de uma cor.
- 3.6 Gerar o texto do estado.
- 3.7 Simular um lance e desfazê-lo.
- 3.8 Trocar a peça de uma casa (promoção).

## Etapa 4 — `ObterEstadoDoJogo`
- 4.1 Registrar o método no serviço, com `NOT_FOUND` para partida inexistente e sincronização.
- 4.2 Devolver o texto das peças.
- 4.3 Devolver turno e estado.

## Etapa 5 — Movimentos
- 5.1 Validador de regras como classe própria.
- 5.2 Peão.
- 5.3 Torre.
- 5.4 Bispo e rainha.
- 5.5 Cavalo.
- 5.6 Rei.
- 5.7 `Jogada` com `Posicao`, sem setters, com origem diferente do destino e resultado da validação.

## Etapa 6 — `EnviarJogada`
- 6.1 Conferir partida e jogador.
- 6.2 Conferir estado da partida e vez do jogador.
- 6.3 Converter e validar as posições.
- 6.4 Validar pelas regras, aplicar e passar o turno.
- 6.5 Jogada ilegal devolve resposta normal com `jogadaValida = false` e o motivo.

## Etapa 7 — Fim de jogo
- 7.1 Nenhum lance pode deixar o próprio rei em xeque.
- 7.2 Detectar xeque.
- 7.3 Detectar xeque-mate.
- 7.4 Detectar afogamento.
- 7.5 Resultado no estado da partida e campo novo no `.proto`.

## Etapa 8 — Regras avançadas
- 8.1 Promoção, com campo novo no `JogadaRequest` para a peça escolhida.
- 8.2 Roque.
- 8.3 Empate por material insuficiente.
- 8.4 Empate pela regra dos 50 lances.
- 8.5 Empate por repetição de posição.

## Etapa 9 — Robustez
- 9.1 Status gRPC restantes (`NOT_FOUND`, `PERMISSION_DENIED`).
- 9.2 `INTERNAL` para exceção inesperada.
- 9.3 Falha do `Main` sem mensagem nula.
- 9.4 `.gitignore` e `target/` fora do versionamento.

## Etapa 10 — Testes
- 10.1 Testes das regras.
- 10.2 Testes de integração com o serviço em memória.

## Etapa 11 — Entrega
- 11.1 Relatório técnico com as 6 seções do enunciado.
- 11.2 Documentação dos formatos de texto do `.proto`.
- 11.3 Reflexão do servidor e porta configurável.
- 11.4 Teste em duas máquinas e simulação de desconexão.
