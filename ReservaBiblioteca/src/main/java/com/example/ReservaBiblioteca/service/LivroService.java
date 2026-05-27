package com.example.ReservaBiblioteca.service;

import com.example.ReservaBiblioteca.dto.LivroDTO;
import com.example.ReservaBiblioteca.entity.Livro;
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
    // LISTAR
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
                        livro.getTitulo().contains(titulo) ||
                        livro.getAutor().contains(autor) ||
                        livro.getIsbn().contains(isbn))
                .map(livroMapper::toDTO)
                .toList();
    }

    // =========================
    // EDITAR
    // =========================

    public LivroDTO editar(Long id, LivroDTO dto) {

        Livro livro = livroRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Livro não encontrado"));

        livro.setTitulo(dto.getTitulo());
        livro.setAutor(dto.getAutor());
        livro.setIsbn(dto.getIsbn());
        livro.setCategoria(dto.getCategoria());
        livro.setEditora(dto.getEditora());
        livro.setAno(dto.getAno());

        Livro atualizado = livroRepository.save(livro);

        return livroMapper.toDTO(atualizado);
    }

    // =========================
    // EXCLUIR
    // =========================

    public void excluir(Long id) {

        livroRepository.deleteById(id);
    }
}