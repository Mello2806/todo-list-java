package br.com.mello.todolist.service;

import br.com.mello.todolist.model.Tarefa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GerenciadorDeTarefasTest {

    private GerenciadorDeTarefas gerenciador;

    @BeforeEach
    void setUp() {
        gerenciador = new GerenciadorDeTarefas();
    }

    @Test
    void deveAdicionarTarefaComIdsSequenciais() {
        Tarefa primeira = gerenciador.adicionar("Estudar Java");
        Tarefa segunda = gerenciador.adicionar("Fazer exercícios");

        assertEquals(1, primeira.getId());
        assertEquals(2, segunda.getId());
        assertEquals(2, gerenciador.listarTodas().size());
    }

    @Test
    void naoDeveAceitarDescricaoVazia() {
        assertThrows(IllegalArgumentException.class, () -> gerenciador.adicionar("   "));
    }

    @Test
    void deveConcluirTarefa() {
        Tarefa tarefa = gerenciador.adicionar("Estudar Java");

        gerenciador.concluir(tarefa.getId());

        assertTrue(gerenciador.buscarPorId(tarefa.getId()).isConcluida());
        assertTrue(gerenciador.listarPendentes().isEmpty());
        assertEquals(1, gerenciador.listarConcluidas().size());
    }

    @Test
    void deveEditarDescricao() {
        Tarefa tarefa = gerenciador.adicionar("Estudar Java");

        gerenciador.editar(tarefa.getId(), "Estudar Spring Boot");

        assertEquals("Estudar Spring Boot", gerenciador.buscarPorId(tarefa.getId()).getDescricao());
    }

    @Test
    void deveRemoverTarefa() {
        Tarefa tarefa = gerenciador.adicionar("Estudar Java");

        gerenciador.remover(tarefa.getId());

        assertTrue(gerenciador.listarTodas().isEmpty());
    }

    @Test
    void deveLancarExcecaoParaIdInexistente() {
        assertThrows(TarefaNaoEncontradaException.class, () -> gerenciador.concluir(42));
        assertThrows(TarefaNaoEncontradaException.class, () -> gerenciador.remover(42));
    }

    @Test
    void deveContinuarIdsAPartirDasTarefasCarregadas() {
        List<Tarefa> carregadas = List.of(
                new Tarefa(3, "Antiga", true, LocalDate.of(2026, 1, 10)),
                new Tarefa(7, "Outra antiga", false, LocalDate.of(2026, 1, 11)));
        GerenciadorDeTarefas comDados = new GerenciadorDeTarefas(carregadas);

        Tarefa nova = comDados.adicionar("Nova");

        assertEquals(8, nova.getId());
    }
}
