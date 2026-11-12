package br.com.mello.todolist.service;

import br.com.mello.todolist.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

/**
 * Regras de negócio da lista de tarefas: adicionar, listar, concluir, editar e remover.
 */
public class GerenciadorDeTarefas {

    private final List<Tarefa> tarefas = new ArrayList<>();
    private int proximoId = 1;

    public GerenciadorDeTarefas() {
    }

    /** Cria o gerenciador já com tarefas carregadas (por exemplo, de um arquivo). */
    public GerenciadorDeTarefas(List<Tarefa> tarefasIniciais) {
        for (Tarefa tarefa : tarefasIniciais) {
            tarefas.add(tarefa);
            proximoId = Math.max(proximoId, tarefa.getId() + 1);
        }
    }

    public Tarefa adicionar(String descricao) {
        Tarefa tarefa = new Tarefa(proximoId, descricao);
        tarefas.add(tarefa);
        proximoId++;
        return tarefa;
    }

    public List<Tarefa> listarTodas() {
        return List.copyOf(tarefas);
    }

    public List<Tarefa> listarPendentes() {
        return tarefas.stream().filter(t -> !t.isConcluida()).toList();
    }

    public List<Tarefa> listarConcluidas() {
        return tarefas.stream().filter(Tarefa::isConcluida).toList();
    }

    public Tarefa buscarPorId(int id) {
        return tarefas.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(() -> new TarefaNaoEncontradaException(id));
    }

    public void concluir(int id) {
        buscarPorId(id).concluir();
    }

    public void reabrir(int id) {
        buscarPorId(id).reabrir();
    }

    public void editar(int id, String novaDescricao) {
        buscarPorId(id).setDescricao(novaDescricao);
    }

    public void remover(int id) {
        tarefas.remove(buscarPorId(id));
    }
}
