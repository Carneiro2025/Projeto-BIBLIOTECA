package com.example.ReservaBiblioteca.service;

import com.example.ReservaBiblioteca.dto.LivroDTO;
import com.example.ReservaBiblioteca.entity.Livro;
import com.example.ReservaBiblioteca.entity.Status;
import com.example.ReservaBiblioteca.mapper.LivroMapper;
import com.example.ReservaBiblioteca.repository.LivroRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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

    // ARRANGE
    LivroDTO dto = new LivroDTO();
    dto.setTitulo("Clean Code");
    dto.setAutor("Robert Martin");
    dto.setIsbn("123456");
    dto.setEditora("Alta Books");
    dto.setAno(2008);
    dto.setCategoria("Programação");

    Livro livroEntity = new Livro();
    livroEntity.setId(1L);
    livroEntity.setTitulo(dto.getTitulo());
    livroEntity.setAutor(dto.getAutor());
    livroEntity.setIsbn(dto.getIsbn());
    livroEntity.setEditora(dto.getEditora());
    livroEntity.setAno(dto.getAno());
    livroEntity.setCategoria(dto.getCategoria());
    livroEntity.setStatus(Status.DISPONIVEL);

    LivroDTO retornoDTO = new LivroDTO();
    retornoDTO.setId(1L);
    retornoDTO.setTitulo(dto.getTitulo());
    retornoDTO.setAutor(dto.getAutor());
    retornoDTO.setIsbn(dto.getIsbn());
    retornoDTO.setEditora(dto.getEditora());
    retornoDTO.setAno(dto.getAno());
    retornoDTO.setCategoria(dto.getCategoria());
    retornoDTO.setStatus("DISPONIVEL");

    when(livroMapper.toEntity(any(LivroDTO.class)))
            .thenReturn(livroEntity);

    when(livroRepository.save(any(Livro.class)))
            .thenReturn(livroEntity);

    when(livroMapper.toDTO(any(Livro.class)))
            .thenReturn(retornoDTO);

    // ACT
    LivroDTO resultado = livroService.cadastrar(dto);

    // ASSERT
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

    LivroDTO resultado = livroService.buscarPorId(1L);

    assertNotNull(resultado);
    assertEquals("Java", resultado.getTitulo());
}
@Test
void deveLancarExcecaoQuandoLivroNaoExistir() {

    Long idInexistente = 999L;

    when(livroRepository.findById(idInexistente))
            .thenReturn(Optional.empty());

    assertThrows(RuntimeException.class,
            () -> livroService.editar(idInexistente, new LivroDTO()));
}
}


