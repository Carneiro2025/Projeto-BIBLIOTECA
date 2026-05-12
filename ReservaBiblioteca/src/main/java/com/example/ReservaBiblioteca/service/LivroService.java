package com.example.ReservaBiblioteca.service;

import com.example.ReservaBiblioteca.dto.LivroDTO;
import com.example.ReservaBiblioteca.entity.Livro;
import com.example.ReservaBiblioteca.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public List<LivroDTO> listarTodos() {
        return livroRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<LivroDTO> buscar(String titulo, String autor, String isbn) {
        if (isbn != null) {
            Livro livro = livroRepository.findByIsbn(isbn);
            return livro != null ? List.of(toDTO(livro)) : List.of();
        } else if (titulo != null) {
            return livroRepository.findByTituloContainingIgnoreCase(titulo).stream().map(this::toDTO).collect(Collectors.toList());
        } else if (autor != null) {
            return livroRepository.findByAutorContainingIgnoreCase(autor).stream().map(this::toDTO).collect(Collectors.toList());
        }
        return List.of();
    }

    public LivroDTO cadastrar(LivroDTO dto) {
        Livro livro = toEntity(dto);
        return toDTO(livroRepository.save(livro));
    }

    public LivroDTO editar(Long id, LivroDTO dto) {
        Livro livro = livroRepository.findById(id).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        livro.setTitulo(dto.getTitulo());
        livro.setAutor(dto.getAutor());
        livro.setIsbn(dto.getIsbn());
        livro.setEditora(dto.getEditora());
        livro.setAno(dto.getAno());
        return toDTO(livroRepository.save(livro));
    }

    public void excluir(Long id) {
        livroRepository.deleteById(id);
    }

    private LivroDTO toDTO(Livro livro) {
        LivroDTO dto = new LivroDTO();
        dto.setId(livro.getId());
        dto.setTitulo(livro.getTitulo());
        dto.setAutor(livro.getAutor());
        dto.setIsbn(livro.getIsbn());
        dto.setEditora(livro.getEditora());
        dto.setAno(livro.getAno());
        dto.setStatus(livro.getStatus().name());
        return dto;
    }

    private Livro toEntity(LivroDTO dto) {
        Livro livro = new Livro();
        livro.setTitulo(dto.getTitulo());
        livro.setAutor(dto.getAutor());
        livro.setIsbn(dto.getIsbn());
        livro.setEditora(dto.getEditora());
        livro.setAno(dto.getAno());
        return livro;
    }
}
