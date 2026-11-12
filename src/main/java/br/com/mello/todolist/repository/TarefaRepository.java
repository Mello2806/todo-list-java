package br.com.mello.todolist.repository;

import br.com.mello.todolist.model.Tarefa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Salva e carrega as tarefas de um arquivo CSV.
 *
 * Formato de cada linha: id;concluida;dataCriacao;descricao
 * A descrição fica por último, então ela pode conter ";" sem quebrar a leitura.
 */
public class TarefaRepository {

    private static final String SEPARADOR = ";";

    private final Path arquivo;

    public TarefaRepository(Path arquivo) {
        this.arquivo = arquivo;
    }

    public List<Tarefa> carregar() {
        List<Tarefa> tarefas = new ArrayList<>();
        if (!Files.exists(arquivo)) {
            return tarefas;
        }

        try (BufferedReader leitor = Files.newBufferedReader(arquivo, StandardCharsets.UTF_8)) {
            String linha;
            int numeroLinha = 0;
            while ((linha = leitor.readLine()) != null) {
                numeroLinha++;
                if (linha.isBlank()) {
                    continue;
                }
                try {
                    tarefas.add(converterLinha(linha));
                } catch (RuntimeException e) {
                    System.err.println("Linha " + numeroLinha + " ignorada (formato inválido): " + linha);
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Erro ao ler o arquivo " + arquivo, e);
        }
        return tarefas;
    }

    public void salvar(List<Tarefa> tarefas) {
        try (BufferedWriter escritor = Files.newBufferedWriter(arquivo, StandardCharsets.UTF_8)) {
            for (Tarefa tarefa : tarefas) {
                escritor.write(converterTarefa(tarefa));
                escritor.newLine();
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Erro ao salvar o arquivo " + arquivo, e);
        }
    }

    private Tarefa converterLinha(String linha) {
        String[] partes = linha.split(SEPARADOR, 4);
        int id = Integer.parseInt(partes[0]);
        boolean concluida = Boolean.parseBoolean(partes[1]);
        LocalDate data = LocalDate.parse(partes[2]);
        String descricao = partes[3];
        return new Tarefa(id, descricao, concluida, data);
    }

    private String converterTarefa(Tarefa tarefa) {
        return String.join(SEPARADOR,
                String.valueOf(tarefa.getId()),
                String.valueOf(tarefa.isConcluida()),
                tarefa.getDataCriacao().toString(),
                tarefa.getDescricao());
    }
}
