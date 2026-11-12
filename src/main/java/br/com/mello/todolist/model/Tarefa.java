package br.com.mello.todolist.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Representa uma tarefa da lista.
 */
public class Tarefa {

    private final int id;
    private String descricao;
    private boolean concluida;
    private final LocalDate dataCriacao;

    public Tarefa(int id, String descricao) {
        this(id, descricao, false, LocalDate.now());
    }

    public Tarefa(int id, String descricao, boolean concluida, LocalDate dataCriacao) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descrição da tarefa não pode ser vazia.");
        }
        this.id = id;
        this.descricao = descricao.trim();
        this.concluida = concluida;
        this.dataCriacao = dataCriacao;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descrição da tarefa não pode ser vazia.");
        }
        this.descricao = descricao.trim();
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void concluir() {
        this.concluida = true;
    }

    public void reabrir() {
        this.concluida = false;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    @Override
    public String toString() {
        String status = concluida ? "[x]" : "[ ]";
        return String.format("%s #%d - %s (criada em %s)", status, id, descricao, dataCriacao);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tarefa)) return false;
        return id == ((Tarefa) o).id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
