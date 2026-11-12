package br.com.mello.todolist.service;

/**
 * Lançada quando não existe tarefa com o id informado.
 */
public class TarefaNaoEncontradaException extends RuntimeException {

    public TarefaNaoEncontradaException(int id) {
        super("Tarefa #" + id + " não encontrada.");
    }
}
