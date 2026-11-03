# Documento de Requisitos — Back-end do Xadrez (servidor gRPC)

| | |
|---|---|
| **Projeto** | servidor-grpc (xadrezServerRpc) |
| **Versão do documento** | 1.3 — 2026-11-03 (decisões DEC-11 a DEC-20 incorporadas) |
| **Escopo** | Back-end completo: conexão, partida, tabuleiro, regras do xadrez e contrato gRPC |
| **Fora de escopo** | Front-end, proxy gRPC-Web, logs (decisão do autor), persistência em banco |

## Como ler este documento

Cada requisito tem um identificador (para rastrear no código e nos testes) e uma situação:

| Marca | Significado |
|---|---|
| ✅ | Feito e coerente com o requisito |
| 🟡 | Parcial: existe, mas não atende totalmente (a coluna *Observação* diz o que falta) |
| ⬜ | Pendente |
| 🚫 | Descartado por decisão do autor (não será implementado) |

Prioridades: **E** = essencial (inclui tudo que as decisões DEC-11 a DEC-16 mantêm no escopo); **A** = desejável, fora do núcleo.

---

## 1. Decisões de projeto já tomadas

| ID | Decisão |
|---|---|
| DEC-01 | O servidor é a fonte da verdade. Nada que o cliente afirma (cor, turno, validade de jogada) é aceito sem conferência. |
| DEC-02 | O servidor mantém uma única partida, criada junto com o serviço, com `UUID` gerado pelo servidor. |
| DEC-03 | O identificador do jogador é gerado pelo servidor quando o cliente não envia um. |
| DEC-04 | Nome do jogador: entre 3 e 15 caracteres (após remover espaços das pontas), único na partida, sem diferenciar maiúsculas de minúsculas. |
| DEC-05 | O primeiro a entrar fica com as brancas; o segundo com as pretas. |
| DEC-06 | A cor das peças pertence à `Partida` (mapa cor → jogador), e não ao `Jogador`. |
| DEC-07 | O tabuleiro é uma matriz 8x8 de peças, encapsulada na classe `Tabuleiro`. |
| DEC-08 | Os métodos do serviço que alteram estado são `synchronized`. |
| DEC-09 | O cliente descobre mudanças de estado consultando `ObterEstadoDoJogo` (polling). |
| DEC-10 | Erros de entrada e de estado usam status gRPC; códigos internos: `"0"` sucesso, `"3"` argumento inválido, `"9"` pré-condição falhou. |
| DEC-11 | Jogadores que forem reconectados devem receber seus dados de volta. |
| DEC-12 | Jogadas inválidas retornam resposta normal com status ok e avisam que a jogada foi inválida. |
| DEC-13 | As regras avançadas que devem entrar são empates (qualquer motivo) e roque. De resto são descartadas. |
| DEC-14 | Não haverá desistência ou proposta de empate. |
| DEC-15 | Se um jogador cair ele tenta reconectar. Caindo assim na DEC-11 que garante que deve receber seus dados de volta. |
| DEC-16 | Para criar uma nova partida (por qualquer motivo que a atual tenha sido terminada), deve ser reiniciado o servidor. |
| DEC-17 | Na reconexão, o nome enviado é ignorado: o servidor devolve o nome cadastrado. |
| DEC-18 | Um id enviado que o servidor não conhece é tratado como entrada nova: o servidor ignora o id enviado e gera um id novo. |
| DEC-19 | A promoção do peão entra como regra do jogo (altera DEC-13, que a descartava). Continua descartada apenas a captura en passant. |
| DEC-20 | Na promoção, o cliente escolhe a peça (rainha, torre, bispo ou cavalo) em um campo do `JogadaRequest`. Sem a escolha, o lance de promoção é recusado com `jogadaValida = false`; não há peça padrão. |

### Consequências assumidas

- Sem en passant (DEC-13): a captura en passant não existe; os demais movimentos seguem as regras normais.
- Sem desistência nem proposta de empate (DEC-14): a partida só termina por xeque-mate ou por empate automático.
- Sem detecção de queda nem tempo limite (DEC-15): se um jogador não voltar, a partida fica parada até o servidor ser reiniciado (DEC-16).
- O servidor tem uma única partida (DEC-02 e DEC-16): depois que ela termina, ninguém mais joga até o reinício.

---

## 2. Arquitetura e dependências

```
XadrezServiceImpl (gRPC)
   ├── Partida ── Tabuleiro ── Peca (TipoPeca, CorPecas)
   │       └── Jogador          Posicao
   ├── Jogada ── Jogador, Posicao
   ├── Regras do xadrez (validador de movimentos)   ← componente novo
   └── Utils (retornos de verificação)
```

| ID | Requisito | Prior. | Situação | Observação |
|---|---|---|---|---|
| ARQ-01 | Nenhuma classe de modelo conhece o gRPC nem o serviço. | E | ✅ | |
| ARQ-02 | `Posicao` e `Peca` não dependem de nenhuma outra classe do domínio. | E | 🟡 | `Peca` importa `Partida.CorPecas`; resolve ao mover o enum para arquivo próprio (COR-02). |
| ARQ-03 | As regras de movimento ficam em um componente próprio (validador), e não espalhadas na `Partida` nem no serviço. | E | ⬜ | |
| ARQ-04 | Os retornos de verificação seguem um único formato em todo o projeto. | E | ✅ | `Utils.montaRespotaJsonObject`. |
| ARQ-05 | Cada código de retorno corresponde a um único status gRPC. | E | ✅ | `"3"` → `INVALID_ARGUMENT`, `"9"` → `FAILED_PRECONDITION`. |
| ARQ-06 | Os códigos de retorno são constantes nomeadas, e não textos soltos. | A | ⬜ | Evita erro de digitação em `"3"`, `"9"` e `"0"`. |

---

## 3. Contrato gRPC (serviço)

### 3.1 `ConectarJogador`

| Item | Definição |
|---|---|
| **Entrada** | `idJogador` (opcional), `nomeJogador` |
| **Saída** | `idJogador`, `idPartida`, `nomeJogador`, `corPecas` |
| **Erros** | `FAILED_PRECONDITION`: sala cheia (entrada nova). `INVALID_ARGUMENT`: nome inválido ou repetido. Quem reconecta com id conhecido não recebe erro: recebe os próprios dados (DEC-11) |

| ID | Requisito | Prior. | Situação | Observação |
|---|---|---|---|---|
| CON-01 | Recusa a entrada de um jogador novo quando a partida está cheia. | E | 🟡 | Hoje a checagem vem antes de tudo e barra também quem reconecta; ajustar com CON-10. |
| CON-02 | Gera um id novo quando o cliente não envia id. | E | ✅ | |
| CON-03 | Valida o nome (tamanho e unicidade) e recusa com `INVALID_ARGUMENT`. | E | ✅ | |
| CON-04 | Reconexão: um id já presente na partida recebe de volta seus dados (id, partida, nome cadastrado e cor), mesmo com a partida cheia. O nome enviado é ignorado (DEC-11, DEC-15, DEC-17). | E | ⬜ | Hoje o código recusa com "Jogador já entrou no jogo". |
| CON-05 | Atribui a cor: primeiro a entrar fica com brancas, o segundo com pretas. | E | ✅ | |
| CON-06 | Responde com id, `idPartida`, nome e cor em caso de sucesso. | E | ✅ | |
| CON-07 | Todo caminho termina com resposta ou erro; nenhum fica sem retorno. | E | ✅ | |
| CON-08 | Ao entrar o segundo jogador, a partida passa a "em andamento" e define o turno das brancas. | E | ⬜ | Depende de PAR-11 e PAR-12. |
| CON-09 | Um id enviado que o servidor não conhece é ignorado e substituído por um id novo gerado pelo servidor; a entrada segue como jogador novo (DEC-18). | E | ⬜ | Hoje o id enviado é adotado como id do novo jogador. |
| CON-10 | A reconexão é verificada antes da checagem de sala cheia e antes da validação de nome de jogador novo. | E | ⬜ | Ordem: reconexão, sala cheia, nome, inserção. Ver DEC-17 e DEC-18. |
| CON-11 | Protege contra exceção inesperada devolvendo `INTERNAL`. | E | ⬜ | Hoje uma exceção vira `UNKNOWN`. |
| CON-12 | Remoção de caracteres inválidos ou de controle do nome. | A | ⬜ | |

### 3.2 `EnviarJogada`

| Item | Definição |
|---|---|
| **Entrada** | `idJogador`, `idPartida`, `posicaoOrigem`, `posicaoDestino` |
| **Saída** | `jogadaValida`, `mensagemRetorno` |
| **Erros gRPC** | `NOT_FOUND`: partida inexistente. `PERMISSION_DENIED`: jogador não pertence à partida. `FAILED_PRECONDITION`: partida não está em andamento. `INVALID_ARGUMENT`: posição mal formada |

| ID | Requisito | Prior. | Situação | Observação |
|---|---|---|---|---|
| JOG-RPC-01 | O método está implementado e sempre responde. | E | ⬜ | Hoje está declarado e vazio: a chamada fica pendurada. |
| JOG-RPC-02 | Confere que a partida informada existe e é a do jogador. | E | ⬜ | |
| JOG-RPC-03 | Confere que o jogador pertence à partida. | E | ⬜ | |
| JOG-RPC-04 | Recusa se a partida não está em andamento. | E | ⬜ | |
| JOG-RPC-05 | Converte e valida os textos de origem e destino. | E | ⬜ | |
| JOG-RPC-06 | Confere que é a vez da cor do jogador. | E | ⬜ | |
| JOG-RPC-07 | Valida a jogada pelas regras (seção 9) e aplica no tabuleiro se válida. | E | ⬜ | |
| JOG-RPC-08 | Após uma jogada válida, passa o turno e atualiza o estado (xeque, mate, empate). | E | ⬜ | |
| JOG-RPC-09 | Jogada ilegal por regra do xadrez devolve resposta normal (status OK) com `jogadaValida = false` e o motivo (DEC-12). | E | ⬜ | Erros gRPC ficam para falhas do pedido: partida inexistente, posição mal formada, jogador estranho à partida. |
| JOG-RPC-10 | Nos lances de promoção, exige e valida a peça escolhida pelo cliente; sem ela, devolve `jogadaValida = false` com o motivo (DEC-20). | E | ⬜ | |

### 3.3 `ObterEstadoDoJogo`

| Item | Definição |
|---|---|
| **Entrada** | `idPartida` |
| **Saída** | `posicaoPecas`, `turnoAtual`, `estadoJogo` |
| **Erros gRPC** | `NOT_FOUND`: partida inexistente |

| ID | Requisito | Prior. | Situação | Observação |
|---|---|---|---|---|
| EST-01 | O método está sobrescrito no serviço e implementado. | E | ⬜ | Hoje não existe; o gRPC responderia `UNIMPLEMENTED`. |
| EST-02 | Devolve o texto com a posição das peças, em formato fixo e documentado. | E | ⬜ | |
| EST-03 | Devolve o turno atual, ou vazio enquanto não há turno. | E | ⬜ | |
| EST-04 | Devolve o estado da partida (aguardando, em andamento, xeque, xeque-mate, empate...). | E | ⬜ | |
| EST-05 | Devolve o resultado (vencedor ou empate, com o motivo do empate) quando a partida termina. | E | ⬜ | O proto atual não tem campo para isso (seção 10). |

### 3.4 Operações que não farão parte do serviço

| Operação | Situação | Motivo |
|---|---|---|
| Desistir da partida | 🚫 | DEC-14 |
| Propor e aceitar empate | 🚫 | DEC-14 |
| Sair da partida / detectar queda de conexão | 🚫 | DEC-15: quem cai tenta reconectar (CON-04) |
| Iniciar nova partida após o término | 🚫 | DEC-16: só reiniciando o servidor |

---

## 4. Modelo `Jogador`

Representa uma pessoa. Não conhece cor, tabuleiro nem gRPC.

| ID | Requisito | Prior. | Situação | Observação |
|---|---|---|---|---|
| JOG-01 | Possui id e nome. | E | ✅ | |
| JOG-02 | O id não muda depois de criado. | E | 🟡 | Existe `setId`; remover. |
| JOG-03 | Nome entre 3 e 15 caracteres. | E | ✅ | |
| JOG-04 | Nome único na partida, sem diferenciar maiúsculas. | E | ✅ | |
| JOG-05 | Informa se o nome é válido e o motivo da recusa. | E | ✅ | |
| JOG-06 | Não guarda a cor das peças. | E | ✅ | |
| JOG-07 | Não conhece o gRPC. | E | ✅ | |
| JOG-08 | O nome pode ser atualizado. | A | ✅ | Existe `setNome`, mas a reconexão não atualiza o nome (DEC-17); pode ser removido. |

---

## 5. Modelo `Partida`

Coordena uma disputa: quem está dentro, em que fase está e de quem é a vez. Dona do `Tabuleiro`.

| ID | Requisito | Prior. | Situação | Observação |
|---|---|---|---|---|
| PAR-01 | Possui id único gerado pelo servidor. | E | ✅ | |
| PAR-02 | O id da partida não muda. | E | 🟡 | Existe `setIdPartida`; remover. |
| PAR-03 | Guarda um jogador por cor, sem outras cores. | E | ✅ | `EnumMap` por cor. |
| PAR-04 | Aceita no máximo dois jogadores. | E | ✅ | |
| PAR-05 | O mesmo jogador não ocupa as duas cores. | E | 🟡 | A checagem existe (`verificaSeJogadorEntrou`), mas é o serviço que precisa lembrar de chamá-la. |
| PAR-06 | Uma cor ocupada não é substituída. | E | ⬜ | `adicionarCorPecasJogadores` faz `put` sem conferir. |
| PAR-07 | Informa se está cheia. | E | ✅ | |
| PAR-08 | Informa as cores livres. | E | ✅ | |
| PAR-09 | Informa se um jogador já entrou. | E | ✅ | |
| PAR-10 | Adicionar jogador garante PAR-04, PAR-05 e PAR-06 sozinho. | E | ⬜ | Hoje é um `put` sem proteção própria. |
| PAR-11 | Informa a cor de um jogador. | E | ⬜ | |
| PAR-12 | Possui um estado explícito (aguardando, em andamento, finalizada). | E | ⬜ | |
| PAR-13 | Ao ocupar a segunda cor, passa para "em andamento" e define o turno das brancas. | E | ⬜ | |
| PAR-14 | Antes de estar em andamento, nenhuma jogada é aceita e não existe turno. | E | ⬜ | |
| PAR-15 | Guarda de quem é a vez, como uma cor. | E | ⬜ | |
| PAR-16 | Após jogada válida, passa o turno para a outra cor. | E | ⬜ | |
| PAR-17 | Só o jogador da cor da vez pode jogar. | E | ⬜ | |
| PAR-18 | Possui um `Tabuleiro` e o inicializa quando a partida começa. | E | ⬜ | |
| PAR-19 | Registra o resultado (vencedor ou empate) e passa para "finalizada". | E | ⬜ | |
| PAR-20 | Informa o estado atual para consulta pelo cliente. | E | ⬜ | |
| PAR-21 | Não expõe sua estrutura interna. | E | ✅ | Mapa privado; a lista devolvida é uma cópia da lista (os objetos `Jogador` são os mesmos). |
| PAR-22 | Registra o histórico das posições ocorridas, para detectar repetição. | E | ⬜ | REG-17. |
| PAR-23 | Mantém o contador de lances sem captura nem movimento de peão, para a regra dos 50 lances. | E | ⬜ | REG-16. |
| PAR-24 | Localiza um jogador pelo id e informa os dados dele (nome e cor), para a reconexão. | E | ⬜ | CON-04. |

---

## 6. Enum `CorPecas`

| ID | Requisito | Prior. | Situação | Observação |
|---|---|---|---|---|
| COR-01 | Contém exatamente duas constantes: brancas e pretas. | E | ✅ | |
| COR-02 | Fica em arquivo próprio, visível a todas as classes. | E | 🟡 | Hoje é um enum aninhado em `Partida` (`Partida.CorPecas`). |
| COR-03 | Informa a cor oposta. | E | ⬜ | Útil para trocar o turno. |

---

## 7. `TipoPeca` e `Peca`

| ID | Requisito | Prior. | Situação | Observação |
|---|---|---|---|---|
| TIP-01 | Enum com os seis tipos: peão, torre, cavalo, bispo, rainha, rei. | E | 🟡 | Existe como enum privado `tipo` dentro de `Peca`, mas nenhum atributo o usa. Precisa de nome com inicial maiúscula e de visibilidade adequada. |
| PEC-01 | A peça possui tipo e cor, que não mudam depois de criadas. | E | 🟡 | Tem cor (com `setCor`); falta o tipo. Remover o setter da cor. |
| PEC-02 | A peça guarda se já se moveu. | E | ✅ | `jaMexeu`. Necessário para o primeiro passo do peão e para o roque. |
| PEC-03 | A peça não conhece a própria posição. | E | ✅ | |
| PEC-04 | Possui representação em texto para o estado enviado ao cliente. | E | ⬜ | |
| PEC-05 | O construtor recebe tipo, cor e estado de movimento. | E | ⬜ | Hoje recebe só cor e `jaMexeu`. |

---

## 8. `Posicao`, `Tabuleiro` e `Jogada`

### 8.1 `Posicao`

| ID | Requisito | Prior. | Situação | Observação |
|---|---|---|---|---|
| POS-01 | Guarda coluna (a–h) e linha (1–8), imutáveis. | E | 🟡 | Existe com `String` e setters; tornar imutável. |
| POS-02 | Uma posição fora do tabuleiro não pode ser criada. | E | ⬜ | Hoje aceita qualquer texto. |
| POS-03 | Cria a posição a partir de um texto como "e2", recusando formatos inválidos. | E | ⬜ | |
| POS-04 | Converte para índices da matriz (linha e coluna). | E | ⬜ | |
| POS-05 | Gera o texto da posição, no formato do proto. | E | ⬜ | |
| POS-06 | Informa o deslocamento até outra posição (diferença de colunas e de linhas). | E | ⬜ | |
| POS-07 | Duas posições com mesma coluna e linha são consideradas iguais. | E | ⬜ | |

### 8.2 `Tabuleiro`

| ID | Requisito | Prior. | Situação | Observação |
|---|---|---|---|---|
| TAB-01 | Possui 64 casas, vazias ou com uma peça. | E | ✅ | Matriz 8x8. |
| TAB-02 | A matriz é privada e não é exposta. | E | 🟡 | Existe `getTabuleiro()` devolvendo a matriz; remover. |
| TAB-03 | Monta a posição inicial padrão do xadrez. | E | ⬜ | |
| TAB-04 | Informa a peça em uma posição (ou casa vazia). | E | ⬜ | |
| TAB-05 | Informa se o caminho entre duas casas está livre. | E | ⬜ | Para torre, bispo, rainha e peão duplo. |
| TAB-06 | Move uma peça, retirando a peça adversária capturada. | E | ⬜ | |
| TAB-07 | Recusa mover de casa vazia e recusa capturar peça da mesma cor. | E | ⬜ | |
| TAB-08 | Localiza o rei de uma cor. | E | ⬜ | Necessário para o xeque. |
| TAB-09 | Gera o texto do estado, no formato enviado ao cliente. | E | ⬜ | |
| TAB-10 | Permite simular um lance sem alterar o estado real (ou desfazê-lo). | E | ⬜ | Necessário para detectar se o lance deixa o próprio rei em xeque. |
| TAB-11 | Gera cópia independente do tabuleiro. | A | ⬜ | Alternativa ao TAB-10. |
| TAB-12 | Substitui a peça de uma casa por outra (usado na promoção). | E | ⬜ | DEC-19. |

### 8.3 `Jogada`

| ID | Requisito | Prior. | Situação | Observação |
|---|---|---|---|---|
| JGD-01 | Guarda jogador, origem e destino, imutáveis. | E | 🟡 | Existe com `String` e setters; usar `Posicao` e remover os setters. |
| JGD-02 | Origem e destino devem ser casas diferentes. | E | ⬜ | |
| JGD-03 | O resultado da validação indica se é válida e o motivo da recusa. | E | ⬜ | Alimenta o `JogadaResponse`. |
| JGD-04 | Guarda a peça de promoção escolhida pelo cliente. | E | ⬜ | DEC-20. |

---

## 9. Regras do xadrez (componente de validação)

| ID | Requisito | Prior. | Situação | Observação |
|---|---|---|---|---|
| REG-01 | A peça movida pertence à cor do jogador e à cor da vez. | E | ⬜ | |
| REG-02 | Movimento do peão: um passo, dois no primeiro movimento, captura na diagonal. | E | ⬜ | |
| REG-03 | Movimento da torre. | E | ⬜ | |
| REG-04 | Movimento do cavalo (pula peças). | E | ⬜ | |
| REG-05 | Movimento do bispo. | E | ⬜ | |
| REG-06 | Movimento da rainha. | E | ⬜ | |
| REG-07 | Movimento do rei (uma casa). | E | ⬜ | |
| REG-08 | Captura de peça adversária; proibida a captura de peça própria. | E | ⬜ | |
| REG-09 | Nenhum lance pode deixar o próprio rei em xeque. | E | ⬜ | |
| REG-10 | Detecção de xeque. | E | ⬜ | |
| REG-11 | Detecção de xeque-mate (fim da partida com vitória). | E | ⬜ | |
| REG-12 | Detecção de afogamento (empate). | E | ⬜ | |
| REG-13 | Promoção do peão ao chegar à última fileira: o peão é substituído por rainha, torre, bispo ou cavalo da própria cor (DEC-19). | E | ⬜ | A peça é escolhida pelo cliente (DEC-20); não há peça padrão. |
| REG-14 | Roque (curto e longo) com todas as condições: rei e torre sem ter se movido, casas entre eles livres, rei fora de xeque e sem atravessar nem terminar em casa atacada. | E | ⬜ | DEC-13. Usa `jaMexeu` de `Peca`. |
| REG-15 | Captura en passant. | — | 🚫 | Descartada (DEC-13). |
| REG-16 | Empate pela regra dos 50 lances. | E | ⬜ | DEC-13. Depende de PAR-23. |
| REG-17 | Empate por repetição de posição. | E | ⬜ | DEC-13. Depende de PAR-22. |
| REG-18 | Empate por material insuficiente. | E | ⬜ | DEC-13. |
| REG-19 | A promoção é validada: o cliente informa a peça quando o peão chega à última fileira (sem ela o lance é recusado), a peça é válida (rainha, torre, bispo ou cavalo) e o lance não deixa o próprio rei em xeque. | E | ⬜ | Rei e peão não são opções de promoção. |

---

## 10. Contrato `.proto`

| ID | Requisito | Prior. | Situação | Observação |
|---|---|---|---|---|
| PRO-01 | `ConectarRequest`: `idJogador` e `nomeJogador`. | E | ✅ | |
| PRO-02 | `ConectarResponse`: `idJogador`, `idPartida`, `nomeJogador`, `corPecas`. | E | ✅ | |
| PRO-03 | `JogadaRequest` e `JogadaResponse` com os campos atuais. | E | ✅ | |
| PRO-04 | `EstadoResponse` informa o vencedor ou o resultado (e o motivo do empate) ao fim da partida. | E | ⬜ | Hoje só tem `posicaoPecas`, `turnoAtual` e `estadoJogo`. |
| PRO-05 | `EstadoRequest` identifica quem consulta, se o servidor precisar conferir que o consultante pertence à partida. | A | ⬜ | Hoje só tem `idPartida`. |
| PRO-06 | `JogadaRequest` permite informar a peça de promoção; o campo é obrigatório nos lances de promoção. | E | ⬜ | DEC-19 e DEC-20. Campo novo, com número ainda não usado. |
| PRO-07 | Campos removidos têm seu número marcado como reservado. | A | ⬜ | Por exemplo, o antigo `status` da conexão. |
| PRO-08 | Os formatos de texto (`posicaoPecas`, `turnoAtual`, `estadoJogo`, `corPecas`) estão documentados. | E | ⬜ | O front-end precisa deles para exibir. |
| PRO-09 | O `.proto` é compartilhado, como única fonte, com o front-end. | E | ⬜ | |

---

## 11. Estados da partida

| Estado | Entrada | Saída | Jogadas aceitas |
|---|---|---|---|
| Aguardando jogadores | Partida criada | Segundo jogador entra | Não |
| Em andamento | Segunda cor ocupada | Xeque-mate ou empate (afogamento, 50 lances, repetição, material insuficiente) | Sim, do jogador da vez |
| Finalizada | Fim por qualquer motivo | Somente com o reinício do servidor (DEC-16) | Não |

---

## 12. Requisitos não funcionais

| ID | Requisito | Prior. | Situação | Observação |
|---|---|---|---|---|
| NF-01 | Todo método do serviço que lê ou altera o estado é sincronizado. | E | 🟡 | `conectarJogador` e `enviarJogada` estão; `obterEstadoDoJogo` precisa seguir o padrão. |
| NF-02 | Todo dado vindo do cliente é validado no servidor. | E | 🟡 | Nome validado; falta tratar o id desconhecido (DEC-18) e as posições. |
| NF-03 | Todo caminho de uma chamada termina com resposta ou erro. | E | 🟡 | Vale para `conectarJogador`; falta nos outros dois RPCs. |
| NF-04 | Mensagens de erro ao cliente são claras e não expõem detalhes internos. | E | ✅ | |
| NF-05 | Cada status gRPC é usado de forma consistente. | E | 🟡 | Mapeamento existe para `"3"` e `"9"`; ampliar para `NOT_FOUND`, `PERMISSION_DENIED` e `INTERNAL`. |
| NF-06 | O servidor sobe numa porta definida e fica ativo. | E | ✅ | Porta 50051 fixa. |
| NF-07 | O `Main` não perde a causa de uma falha ao subir. | E | 🟡 | O `catch` chama `getMessage()` sem garantir que a mensagem exista. |
| NF-08 | A porta é configurável (argumento ou variável de ambiente). | A | ⬜ | |
| NF-09 | Encerramento limpo ao interromper o processo. | A | ⬜ | |
| NF-10 | Reflexão de servidor habilitada para ferramentas como `grpcurl`. | A | ⬜ | |
| NF-11 | Cada regra tem ao menos um teste automatizado. | E | ⬜ | Não há nenhum teste no projeto. |
| NF-12 | Há testes de integração que sobem o serviço em memória. | E | ⬜ | O `grpc-testing` já está no `pom.xml`. |
| NF-13 | Dependências declaradas no `pom.xml`. | E | ✅ | Gson declarado. |
| NF-14 | A pasta `target/` não é versionada. | E | ⬜ | Não há `.gitignore` e os arquivos gerados aparecem como modificados. |
| NF-15 | Strings recebidas têm limite de tamanho. | A | ⬜ | |

---

## 13. Critérios de aceite do back-end

O back-end está pronto quando:

1. Dois jogadores conectam, recebem cores diferentes e a partida passa para "em andamento" sozinha.
2. Um terceiro jogador é recusado com `FAILED_PRECONDITION`.
3. Nome inválido ou repetido é recusado com `INVALID_ARGUMENT`.
4. O mesmo id não ocupa as duas cores.
5. `ObterEstadoDoJogo` devolve posição das peças, turno e estado corretos em qualquer fase.
6. `EnviarJogada` aceita lances legais, recusa os ilegais com motivo e alterna o turno.
7. Xeque, xeque-mate e afogamento são detectados e encerram a partida com o resultado correto.
8. Jogar fora da vez, antes de a partida iniciar ou depois de ela terminar é recusado.
9. Nenhuma chamada fica sem resposta e nenhuma exceção chega ao cliente como `UNKNOWN`.
10. Os testes automatizados cobrem as regras das seções 4 a 9 e passam no `mvn clean test`.
11. O `.proto` e os formatos de texto estão documentados para o front-end.
12. Um jogador que reenvia o próprio id, mesmo com a partida cheia, recebe de volta id, partida, nome e cor.
13. O roque é aceito quando permitido e recusado quando não for.
14. Os empates (afogamento, 50 lances, repetição e material insuficiente) são detectados e encerram a partida com o motivo informado.
15. A promoção é aplicada com a peça que o cliente escolheu, e um lance de promoção sem escolha é recusado.

---

## 14. Ordem de implementação sugerida

| Etapa | Conteúdo | Requisitos |
|---|---|---|
| 1 | Fechar o pré-jogo e a reconexão | PAR-02, PAR-06, PAR-10 a PAR-13, PAR-24, COR-02, COR-03, CON-01, CON-04, CON-08, CON-10 |
| 2 | Peças e coordenadas | TIP-01, PEC-01, PEC-04, PEC-05, POS-01 a POS-07 |
| 3 | Tabuleiro | TAB-02 a TAB-10, TAB-12 |
| 4 | Estado do jogo | EST-01 a EST-04, PAR-14 a PAR-16, PAR-18, PAR-20 |
| 5 | Movimentos básicos | REG-01 a REG-08, JGD-01 a JGD-03 |
| 6 | Enviar jogada | JOG-RPC-01 a JOG-RPC-10, PAR-17 |
| 7 | Xeque e fim de jogo | REG-09 a REG-12, PAR-19, EST-05, PRO-04 |
| 8 | Roque, promoção e empates | REG-13, REG-14, REG-16 a REG-19, PRO-06, JGD-04, PAR-22, PAR-23 |
| 9 | Robustez | CON-09, CON-11, NF-01 a NF-07, NF-14 |
| 10 | Testes | NF-11, NF-12 |
| 11 | Extras | PRO-05, PRO-07, CON-12, ARQ-06, TAB-11, NF-08 a NF-10, NF-15 |

---

## 15. Glossário

| Termo | Significado |
|---|---|
| **Partida** | Uma disputa entre dois jogadores. |
| **Turno** | A cor que tem direito de jogar no momento. |
| **Posição** | Uma casa do tabuleiro, por exemplo "e2". |
| **Xeque** | O rei está sob ataque. |
| **Xeque-mate** | O rei está sob ataque e não há como evitar. |
| **Afogamento** | O jogador da vez não tem lance legal e seu rei não está em xeque (empate). |
| **Polling** | O cliente consulta o servidor repetidamente para saber se algo mudou. |
| **Promoção** | O peão que chega à última fileira é trocado por outra peça (rainha, torre, bispo ou cavalo). |
