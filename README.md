# 🗓️ Agendador de Tarefas Multithread com Prioridades, Dependências e Detecção de Deadlocks

---

## 📋 Descrição do Projeto

Este projeto implementa um **Agendador de Tarefas** em Java que permite gerenciar e executar tarefas com:

- Prioridades que definem a ordem de execução.
- Dependências entre tarefas para garantir que uma só execute após suas dependências estarem completas.
- Execução concorrente usando múltiplas threads (*workers*).
- Detecção e resolução automática de deadlocks causados por dependências circulares.
- Adição e remoção dinâmica de tarefas durante a execução.
- Interface de linha de comando (CLI) para interação simples e direta.

---

## 🚀 Funcionalidades

| Funcionalidade                 | Descrição                                                                                 |
|-------------------------------|-------------------------------------------------------------------------------------------|
| Prioridades                   | Executa tarefas de maior prioridade primeiro.                                             |
| Dependências                  | Garante que tarefas só rodem após suas dependências serem concluídas.                     |
| Multithreading                | Usa múltiplas threads para executar tarefas em paralelo.                                 |
| Detecção de Deadlocks         | Identifica ciclos no grafo de dependências que travam a execução.                         |
| Resolução de Deadlocks        | Remove tarefas para quebrar ciclos e liberar o fluxo de execução.                         |
| Interface CLI Interativa      | Permite adicionar, listar, executar e remover tarefas via comandos no terminal.           |
| Status das Tarefas            | Mostra o status atual (PENDING, RUNNING, COMPLETED) de cada tarefa.                       |

---

## 💡 Como usar

1. **Inicie o programa**

   Compile e execute a classe `Main` que inicializa o agendador e a interface CLI.

   ```bash
   java -cp bin app.Main

2. **Comandos disponíveis**

- add : Adiciona uma nova tarefa com ID, prioridade e dependências.

- remove : Remove uma tarefa pelo ID.

- list : Lista todas as tarefas cadastradas com seus status.

- run : Executa as tarefas pendentes conforme prioridade e dependências.

- exit : Encerra o programa.

## 📌 Requisitos
- Java 8 ou superior

- Sem dependências externas

- Multithreading usando Thread e sincronização básica

## 📜 Licença
Projeto open source para estudo e uso livre.
