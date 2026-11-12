package br.com.mello.todolist;

import br.com.mello.todolist.model.Tarefa;
import br.com.mello.todolist.repository.TarefaRepository;
import br.com.mello.todolist.service.GerenciadorDeTarefas;
import br.com.mello.todolist.service.TarefaNaoEncontradaException;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

/**
 * Menu da aplicação no console.
 */
public class Main {

    private static final Path ARQUIVO = Path.of("tarefas.csv");

    private final Scanner scanner = new Scanner(System.in);
    private final TarefaRepository repository = new TarefaRepository(ARQUIVO);
    private final GerenciadorDeTarefas gerenciador = new GerenciadorDeTarefas(repository.carregar());

    public static void main(String[] args) {
        new Main().executar();
    }

    private void executar() {
        System.out.println("=== Lista de Tarefas ===");
        boolean rodando = true;
        while (rodando) {
            mostrarMenu();
            String opcao = lerTexto("Escolha uma opção: ");
            try {
                switch (opcao) {
                    case "1" -> adicionar();
                    case "2" -> listar(gerenciador.listarTodas());
                    case "3" -> listar(gerenciador.listarPendentes());
                    case "4" -> concluir();
                    case "5" -> editar();
                    case "6" -> remover();
                    case "0" -> rodando = false;
                    default -> System.out.println("Opção inválida.");
                }
            } catch (TarefaNaoEncontradaException | IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
        System.out.println("Até mais!");
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("1 - Adicionar tarefa");
        System.out.println("2 - Listar todas");
        System.out.println("3 - Listar pendentes");
        System.out.println("4 - Concluir tarefa");
        System.out.println("5 - Editar tarefa");
        System.out.println("6 - Remover tarefa");
        System.out.println("0 - Sair");
    }

    private void adicionar() {
        String descricao = lerTexto("Descrição: ");
        Tarefa tarefa = gerenciador.adicionar(descricao);
        salvar();
        System.out.println("Tarefa criada: " + tarefa);
    }

    private void listar(List<Tarefa> tarefas) {
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa por aqui.");
            return;
        }
        tarefas.forEach(System.out::println);
    }

    private void concluir() {
        int id = lerId();
        gerenciador.concluir(id);
        salvar();
        System.out.println("Tarefa #" + id + " concluída.");
    }

    private void editar() {
        int id = lerId();
        String descricao = lerTexto("Nova descrição: ");
        gerenciador.editar(id, descricao);
        salvar();
        System.out.println("Tarefa #" + id + " atualizada.");
    }

    private void remover() {
        int id = lerId();
        gerenciador.remover(id);
        salvar();
        System.out.println("Tarefa #" + id + " removida.");
    }

    private void salvar() {
        repository.salvar(gerenciador.listarTodas());
    }

    private String lerTexto(String mensagem) {
        System.out.print(mensagem);
        if (!scanner.hasNextLine()) {
            // Entrada encerrada (Ctrl+D). As tarefas já foram salvas a cada operação.
            System.out.println();
            System.out.println("Até mais!");
            System.exit(0);
        }
        return scanner.nextLine().trim();
    }

    private int lerId() {
        String texto = lerTexto("Id da tarefa: ");
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Id inválido: " + texto);
        }
    }
}
