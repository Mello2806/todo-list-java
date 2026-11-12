package br.com.mello.todolist.repository;

import br.com.mello.todolist.model.Tarefa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TarefaRepositoryTest {

    @TempDir
    Path pastaTemporaria;

    @Test
    void deveRetornarListaVaziaQuandoArquivoNaoExiste() {
        TarefaRepository repository = new TarefaRepository(pastaTemporaria.resolve("nao-existe.csv"));

        assertTrue(repository.carregar().isEmpty());
    }

    @Test
    void deveSalvarECarregarTarefas() {
        TarefaRepository repository = new TarefaRepository(pastaTemporaria.resolve("tarefas.csv"));
        List<Tarefa> originais = List.of(
                new Tarefa(1, "Estudar POO", true, LocalDate.of(2026, 11, 1)),
                new Tarefa(2, "Trabalho; parte 2", false, LocalDate.of(2026, 11, 2)));

        repository.salvar(originais);
        List<Tarefa> carregadas = repository.carregar();

        assertEquals(2, carregadas.size());
        Tarefa segunda = carregadas.get(1);
        assertEquals(2, segunda.getId());
        assertEquals("Trabalho; parte 2", segunda.getDescricao());
        assertFalse(segunda.isConcluida());
        assertEquals(LocalDate.of(2026, 11, 2), segunda.getDataCriacao());
        assertTrue(carregadas.get(0).isConcluida());
    }

    @Test
    void deveIgnorarLinhasInvalidas() throws IOException {
        Path arquivo = pastaTemporaria.resolve("tarefas.csv");
        Files.writeString(arquivo, "1;false;2026-11-01;Válida\nlinha quebrada\n\n");

        List<Tarefa> carregadas = new TarefaRepository(arquivo).carregar();

        assertEquals(1, carregadas.size());
        assertEquals("Válida", carregadas.get(0).getDescricao());
    }
}
