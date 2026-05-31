package com.example.ReservaBiblioteca.service;

import com.example.ReservaBiblioteca.dto.LivroDTO;
import com.example.ReservaBiblioteca.entity.Livro;
import com.example.ReservaBiblioteca.entity.Status;
import com.example.ReservaBiblioteca.exception.LivroNaoEncontradoException;
import com.example.ReservaBiblioteca.mapper.LivroMapper;
import com.example.ReservaBiblioteca.repository.LivroRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final LivroMapper livroMapper;

    public LivroService(
            LivroRepository livroRepository,
            LivroMapper livroMapper) {

        this.livroRepository = livroRepository;
        this.livroMapper = livroMapper;
    }

    // =========================
    // LISTAR TODOS
    // =========================

    public List<LivroDTO> listarTodos() {

        return livroRepository.findAll()
                .stream()
                .map(livroMapper::toDTO)
                .toList();
    }

    // =========================
    // CADASTRAR
    // =========================

    public LivroDTO cadastrar(LivroDTO dto) {

        Livro livro = livroMapper.toEntity(dto);

        // Status padrão
        livro.setStatus(Status.DISPONIVEL);

        Livro salvo = livroRepository.save(livro);

        return livroMapper.toDTO(salvo);
    }

    // =========================
    // BUSCAR
    // =========================

    public List<LivroDTO> buscar(
            String titulo,
            String autor,
            String isbn) {

        return livroRepository.findAll()
                .stream()
                .filter(livro ->

                (titulo == null || titulo.isBlank()
                        || livro.getTitulo().toLowerCase()
                                .contains(titulo.toLowerCase()))

                        ||

                        (autor == null || autor.isBlank()
                                || livro.getAutor().toLowerCase()
                                        .contains(autor.toLowerCase()))

                        ||

                        (isbn == null || isbn.isBlank()
                                || livro.getIsbn().contains(isbn)))
                .map(livroMapper::toDTO)
                .toList();
    }

    // =========================
    // BUSCAR POR ID
    // =========================

    public LivroDTO buscarPorId(Long id) {

        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new LivroNaoEncontradoException(
                        "Livro não encontrado com ID: " + id));

        return livroMapper.toDTO(livro);
    }

    // =========================
    // EDITAR
    // =========================

    public LivroDTO editar(Long id, LivroDTO dto) {

        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new LivroNaoEncontradoException(
                        "Livro não encontrado com ID: " + id));

        livro.setTitulo(dto.getTitulo());
        livro.setAutor(dto.getAutor());
        livro.setIsbn(dto.getIsbn());
        livro.setCategoria(dto.getCategoria());
        livro.setEditora(dto.getEditora());
        livro.setAno(dto.getAno());

        if (livro.getStatus() == null) {
            livro.setStatus(Status.DISPONIVEL);
        }

        Livro atualizado = livroRepository.save(livro);

        return livroMapper.toDTO(atualizado);
    }

    // =========================
    // EXCLUIR
    // =========================

    public void excluir(Long id) {

        if (!livroRepository.existsById(id)) {

            throw new LivroNaoEncontradoException(
                    "Livro não encontrado com ID: " + id);
        }

        livroRepository.deleteById(id);
    }
}
