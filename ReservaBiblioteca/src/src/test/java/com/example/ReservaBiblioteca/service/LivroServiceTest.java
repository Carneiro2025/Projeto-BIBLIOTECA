package com.example.ReservaBiblioteca.service;

import com.example.ReservaBiblioteca.dto.LivroDTO;
import com.example.ReservaBiblioteca.entity.Livro;
import com.example.ReservaBiblioteca.entity.Status;
import com.example.ReservaBiblioteca.repository.LivroRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private LivroService livroService;

    @Mock
    private LivroMapper livroMapper;

    @Test
    void deveCadastrarLivro() {

        LivroDTO dto = new LivroDTO();

        dto.setTitulo("Clean Code");
        dto.setAutor("Robert Martin");
        dto.setIsbn("123456");
        dto.setEditora("Alta Books");
        dto.setAno(2008);
        dto.setCategoria("Programação");

        Livro livro = new Livro();

        livro.setId(1L);
        livro.setTitulo(dto.getTitulo());
        livro.setAutor(dto.getAutor());
        livro.setIsbn(dto.getIsbn());
        livro.setEditora(dto.getEditora());
        livro.setAno(dto.getAno());
        livro.setCategoria(dto.getCategoria());
        livro.setStatus(Status.DISPONIVEL);

        when(livroRepository.save(any(Livro.class)))
                .thenReturn(livro);

        LivroDTO resultado = livroService.cadastrar(dto);

        assertNotNull(resultado);

        assertEquals("Clean Code", resultado.getTitulo());

        assertEquals("DISPONIVEL", resultado.getStatus());
    }

    @Test
    void deveBuscarLivroPorId() {

    Livro livro = new Livro();
    livro.setId(1L);
    livro.setTitulo("Java");

    LivroDTO dto = new LivroDTO();
    dto.setId(1L);
    dto.setTitulo("Java");

    when(livroRepository.findById(1L))
            .thenReturn(Optional.of(livro));

    when(livroMapper.toDTO(livro))
            .thenReturn(dto);

    LivroDTO resultado =
            livroService.buscarPorId(1L);

    assertNotNull(resultado);
    assertEquals("Java", resultado.getTitulo());
}

@Test
    void deveLancarExcecaoQuandoLivroNaoExistir() {

        // ARRANGE
        Long idInexistente = 999L;

        when(livroRepository.findById(idInexistente))
                .thenReturn(Optional.empty());

        // ACT + ASSERT
        assertThrows(
                RuntimeException.class,
                () -> livroService.editar(idInexistente, new LivroDTO())
        );
    }

}
