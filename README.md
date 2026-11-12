# todo-list-java

Aplicação de console para gerenciamento de tarefas, desenvolvida em Java como projeto de estudo do curso de Análise e Desenvolvimento de Sistemas.

## Funcionalidades

- Adicionar, listar, editar, concluir e remover tarefas
- Listar apenas as tarefas pendentes
- Tarefas salvas automaticamente no arquivo `tarefas.csv`, que é recarregado ao abrir o programa
- Tratamento de erros (id inexistente, id inválido, descrição vazia)

## Tecnologias

- Java 17
- Maven
- JUnit 5

## Estrutura

```
src/main/java/br/com/mello/todolist/
├── Main.java                       # menu no console
├── model/Tarefa.java               # entidade Tarefa
├── service/GerenciadorDeTarefas.java # regras de negócio
└── repository/TarefaRepository.java  # leitura e escrita do CSV
```

## Como rodar

Com Maven:

```bash
mvn package
java -jar target/todo-list-java-1.0.0.jar
```

Sem Maven, só com o JDK:

```bash
javac -d out $(find src/main -name "*.java")
java -cp out br.com.mello.todolist.Main
```

## Testes

```bash
mvn test
```

## Exemplo de uso

```
=== Lista de Tarefas ===

1 - Adicionar tarefa
2 - Listar todas
3 - Listar pendentes
4 - Concluir tarefa
5 - Editar tarefa
6 - Remover tarefa
0 - Sair
Escolha uma opção: 2
[x] #1 - Estudar POO (criada em 2026-11-12)
[ ] #2 - Fazer trabalho de banco de dados (criada em 2026-11-12)
```

## Próximos passos

- [ ] Prioridade e data de vencimento nas tarefas
- [ ] Busca por texto
- [ ] Trocar o CSV por um banco de dados (PostgreSQL)
