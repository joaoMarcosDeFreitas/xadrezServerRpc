# O que falta fazer — Back-end do Xadrez (servidor gRPC)

Atualizado em 2026-11-09, depois de comparar a lista com o código atual.

**Como usar este arquivo**
- Trabalhar uma sub-etapa por vez.
- Quando um item estiver pronto e conferido (código lido e, quando der, testado no Postman), ele sai daqui. Quando todos os itens de uma etapa saírem, a etapa inteira sai.
- Cada item tem uma linha **Hoje** (o que o código já tem) e uma linha **Falta** (o que ainda precisa ser feito).
- Teste manual: Postman gRPC em `localhost:<porta do Main> (hoje 50040)`, sem TLS, importando `src/main/proto/xadrez.proto` (o servidor não tem reflexão ligada).

**Já concluído e removido:** Etapa 1 (pré-jogo e reconexão), testada no Postman em 2026-11-04. O item 2.1 também já tinha saído da lista original.

**Resumo**

| Etapa | Assunto | Situação |
|---|---|---|
| 2 | Peças e coordenadas | Em andamento (2.5: texto feito; faltam deslocamento e igualdade) |
| 3 | Tabuleiro | Não iniciada (só existe a matriz vazia) |
| 4 | `ObterEstadoDoJogo` | Não iniciada |
| 5 | Movimentos | Não iniciada |
| 6 | `EnviarJogada` | Não iniciada (método existe, mas vazio) |
| 7 | Fim de jogo | Não iniciada |
| 8 | Regras avançadas | Não iniciada |
| 9 | Robustez | Parcial (9.1 em parte) |
| 10 | Testes | Não iniciada |
| 11 | Entrega | Não iniciada |

---

## Etapa 2 — Peças e coordenadas

**2.5 Texto, deslocamento e igualdade** — *em andamento (texto feito)*
- Hoje: `getCasa()` devolve o texto da própria casa ("e2", "a1", "h8"). Deslocamento e igualdade ainda não existem.
- Falta: calcular o deslocamento até outra posição, mantendo o sinal (diferença de colunas e de linhas, destino menos origem). Comparar duas posições por igualdade (coluna e linha iguais; se for usar em coleções com hash, sobrescrever o hash junto). Opcional: trocar `NullPointerException` por `IllegalArgumentException` no construtor, e corrigir o comentário que diz que o caractere '2' vale 48 (vale 50).

---

## Etapa 3 — Tabuleiro

Hoje: `Tabuleiro` tem só a matriz 8x8 vazia e um getter que entrega a matriz inteira. `Partida` ainda não tem tabuleiro.

- **3.1 Esconder a matriz e ler uma casa** — não feito. Tirar o getter que expõe a matriz e criar a leitura de uma casa por `Posicao`.
- **3.2 Posição inicial padrão** — não feito. Montar as 32 peças nas casas certas (peças pretas e brancas, peões na segunda e sétima linha).
- **3.3 Caminho livre entre duas casas** — não feito. Informar se não há peça nas casas do meio (usado por torre, bispo, rainha e pelo avanço duplo do peão).
- **3.4 Mover e capturar** — não feito. Recusar mover de casa vazia e capturar peça da mesma cor.
- **3.5 Localizar o rei de uma cor** — não feito.
- **3.6 Gerar o texto do estado** — não feito. Juntar o texto das peças (item 2.2) no formato que vai em `posicaoPecas`.
- **3.7 Simular um lance e desfazê-lo** — não feito. Necessário para a Etapa 7 (testar se o lance deixa o rei em xeque).
- **3.8 Trocar a peça de uma casa** — não feito. Usado na promoção do peão (item 8.1).
- Ligação a fazer: criar o tabuleiro dentro da `Partida`, montado na posição inicial quando a partida começa.

---

## Etapa 4 — `ObterEstadoDoJogo`

Hoje: o `.proto` já declara o método, com `EstadoRequest` (id da partida) e `EstadoResponse` (`posicaoPecas`, `turnoAtual`, `estadoJogo`). O serviço ainda não implementa o método. A `Partida` já tem `getTurno()` e `getEstadoDaPartida()`.

- **4.1 Registrar o método no serviço** — não feito. Responder `NOT_FOUND` para partida inexistente e manter a sincronização como nos outros métodos.
- **4.2 Devolver o texto das peças** — não feito (depende dos itens 2.2 e 3.6).
- **4.3 Devolver turno e estado** — não feito. Os dados já existem na `Partida`; falta converter em texto e preencher a resposta.

---

## Etapa 5 — Movimentos

Hoje: existe `Jogada`, com jogador, origem e destino como textos e setters.

- **5.1 Validador de regras como classe própria** — não feito.
- **5.2 Peão** — não feito. Andar uma casa, duas na primeira jogada, capturar na diagonal; direção conforme a cor.
- **5.3 Torre** — não feito.
- **5.4 Bispo e rainha** — não feito.
- **5.5 Cavalo** — não feito.
- **5.6 Rei** — não feito (uma casa em qualquer direção; o roque fica no 8.2).
- **5.7 `Jogada`** — não feito. Origem e destino passam a ser `Posicao`, sem setters. Origem diferente do destino. Guardar o resultado da validação.

---

## Etapa 6 — `EnviarJogada`

Hoje: o método existe no serviço, mas está vazio. Ele não responde, então o cliente fica esperando até estourar o tempo.

- **6.1 Conferir partida e jogador** — não feito.
- **6.2 Conferir estado da partida e vez do jogador** — não feito. Recusar se a partida não estiver em andamento ou se o turno for da outra cor. A `Partida` já tem o necessário (`getTurno`, `getEstadoDaPartida`, `trocarTurno`).
- **6.3 Converter e validar as posições** — não feito (depende da Etapa 2).
- **6.4 Validar pelas regras, aplicar e passar o turno** — não feito (depende das Etapas 3 e 5).
- **6.5 Jogada ilegal** — não feito. Responder normalmente com `jogadaValida` falso e o motivo em `mensagemRetorno`, sem erro gRPC.

---

## Etapa 7 — Fim de jogo

- **7.1 Nenhum lance pode deixar o próprio rei em xeque** — não feito (usa 3.5 e 3.7).
- **7.2 Detectar xeque** — não feito.
- **7.3 Detectar xeque-mate** — não feito.
- **7.4 Detectar afogamento** — não feito.
- **7.5 Resultado no estado e campo novo no `.proto`** — não feito. O estado `CONCLUIDA` já existe no enum, mas nada o define. O `EstadoResponse` não tem campo de resultado.

---

## Etapa 8 — Regras avançadas

Hoje: `Peca` já guarda "já mexeu", que o roque e o avanço duplo do peão vão usar.

- **8.1 Promoção** — não feito. Campo novo no `JogadaRequest` para a peça escolhida (depende de 3.8).
- **8.2 Roque** — não feito.
- **8.3 Empate por material insuficiente** — não feito.
- **8.4 Empate pela regra dos 50 lances** — não feito.
- **8.5 Empate por repetição de posição** — não feito.

---

## Etapa 9 — Robustez

- **9.1 Status gRPC restantes** — *parcial*. Hoje a conexão usa `INVALID_ARGUMENT` e `FAILED_PRECONDITION` (e `UNKNOWN` como padrão). Faltam `NOT_FOUND` (partida inexistente) e `PERMISSION_DENIED` (jogador que não pertence à partida).
- **9.2 `INTERNAL` para exceção inesperada** — não feito.
- **9.3 Falha do `Main` sem mensagem nula** — não feito. O `catch` ainda usa a mensagem da exceção direto; se ela for nula, o próprio `catch` quebra.
- **9.4 `.gitignore` e `target/` fora do versionamento** — não feito. Não existe `.gitignore`, e arquivos de `target/` aparecem como modificados no git, ou seja, estão versionados e precisam sair do controle.

---

## Etapa 10 — Testes

Hoje: as pastas `src/test/java` e `src/test/resources` existem, vazias. O `pom.xml` já tem JUnit 4.13.2, Mockito e `grpc-testing`.

- **10.1 Testes das regras** — não feito (posição, tabuleiro, cada peça, xeque, fim de jogo).
- **10.2 Testes de integração com o serviço em memória** — não feito (conexão, jogada válida e inválida, estado).
- A conexão de jogadores já foi testada à mão no Postman (Etapa 1), mas não há teste automatizado dela.

---

## Etapa 11 — Entrega

- **11.1 Relatório técnico com as 6 seções do enunciado** — em andamento (conteúdo do relatório não conferido). As seis seções, conforme `docs/Orientacao_Trabalho_Pratico_Sistemas_Distribuidos.docx`:
  1. Introdução: o jogo escolhido e o motivo de usar RPC (gRPC).
  2. Arquitetura da solução: diagrama (cliente, servidor, stub/skeleton, middleware) e o fluxo de mensagens a cada jogada. Já existe `docs/figura2-diagrama-sequencia.png` (e `.svg`).
  3. Especificação das interfaces remotas: todos os métodos chamáveis (`ConectarJogador`, `EnviarJogada`, `ObterEstadoDoJogo`), com parâmetros, tipos de retorno e exceções tratadas (os status gRPC da Etapa 9).
  4. Tratamento de concorrência e estado do jogo: como o servidor mantém a consistência com vários clientes ao mesmo tempo (hoje, métodos `synchronized`).
  5. Manual de compilação e execução: como subir o servidor e rodar os clientes.
  6. Conclusão e autoavaliação: dificuldades do backend e o relato do uso de IA no frontend.
  - O rascunho do relatório está em `docs/Relatorio_Tecnico_SD_ABNT-2026.docx.pdf`; ele não foi lido para saber quais seções já estão prontas. O arquivo `instrucoes-da-1-entrega.txt` cobre só as seções 1 e 2.
- **11.2 Documentação dos formatos de texto do `.proto`** — não feito (formato do texto das peças, das posições, do turno e do estado).
- **11.3 Reflexão do servidor e porta configurável** — não feito. Hoje a porta (50040) está fixa no `Main`, e a reflexão gRPC não está ligada (por isso o Postman precisa importar o `.proto`).
- **11.4 Teste em duas máquinas e simulação de desconexão** — não feito. O enunciado pede o teste em processos diferentes no localhost e entre máquinas na rede local, e a simulação de desconexão e de exceção na chamada remota.
- **11.5 Concorrência conferida e explicada** — não feito. O enunciado dá 20% da nota a "gerenciamento de concorrência e estado" (sem inconsistências com acessos simultâneos). Hoje os métodos do serviço são `synchronized`. Falta conferir que isso cobre também a leitura do estado (Etapa 4) e a jogada (Etapa 6), e anotar a explicação para o relatório (seção 4) e para a defesa.

---

## Ordem sugerida

1. Etapa 2 (a `Posicao` e o texto da peça são base de tudo).
2. Etapa 3 (tabuleiro) e depois Etapa 4 (estado), que já dá para testar no Postman.
3. Etapas 5 e 6 (movimentos e `EnviarJogada`).
4. Etapas 7 e 8 (fim de jogo e regras avançadas).
5. Etapas 9, 10 e 11 (robustez, testes e entrega). O item 9.4 (`.gitignore`) pode ser feito a qualquer momento.
