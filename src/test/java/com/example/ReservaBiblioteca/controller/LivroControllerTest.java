package com.example.ReservaBiblioteca.controller;

import com.example.ReservaBiblioteca.dto.LivroDTO;
import com.example.ReservaBiblioteca.exception.LivroNaoEncontradoException;
import com.example.ReservaBiblioteca.service.LivroService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
    value = LivroController.class,
    excludeAutoConfiguration = {
        SecurityAutoConfiguration.class,
        SecurityFilterAutoConfiguration.class
    },
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = com.example.ReservaBiblioteca.security.SecurityConfig.class
    )
)
class LivroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LivroService livroService;

    @Test
    void deveListarLivros() throws Exception {

        LivroDTO dto = new LivroDTO();
        dto.setId(1L);
        dto.setTitulo("Clean Code");
        dto.setAutor("Robert Martin");
        dto.setIsbn("9780132350884");
        dto.setEditora("Alta Books");
        dto.setAno(2008);
        dto.setCategoria("Programação");
        dto.setStatus("DISPONIVEL");

        when(livroService.listarTodos()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/livros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Clean Code"))
                .andExpect(jsonPath("$[0].autor").value("Robert Martin"));
    }

    @Test
    void deveCadastrarLivro() throws Exception {

        LivroDTO dto = new LivroDTO();
        dto.setId(1L);
        dto.setTitulo("Clean Code");
        dto.setAutor("Robert Martin");
        dto.setIsbn("9780132350884");
        dto.setEditora("Alta Books");
        dto.setAno(2008);
        dto.setCategoria("Programação");
        dto.setStatus("DISPONIVEL");

        when(livroService.cadastrar(any(LivroDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/api/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "titulo":"Clean Code",
                            "autor":"Robert Martin",
                            "isbn":"9780132350884",
                            "editora":"Alta Books",
                            "ano":2008,
                            "categoria":"Programação"
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("Clean Code"));
    }

    @Test
    void naoDeveCadastrarLivroSemTitulo() throws Exception {

        mockMvc.perform(post("/api/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "titulo":"",
                            "autor":"Robert Martin",
                            "isbn":"9780132350884",
                            "editora":"Alta Books",
                            "ano":2008,
                            "categoria":"Programação"
                        }
                        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveEditarLivro() throws Exception {

        LivroDTO dto = new LivroDTO();
        dto.setId(1L);
        dto.setTitulo("Spring Boot");
        dto.setAutor("Rafael");
        dto.setIsbn("9999999999");
        dto.setEditora("Tech");
        dto.setAno(2024);
        dto.setCategoria("Backend");
        dto.setStatus("DISPONIVEL");

        when(livroService.editar(eq(1L), any(LivroDTO.class))).thenReturn(dto);

        mockMvc.perform(put("/api/livros/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "titulo":"Spring Boot",
                            "autor":"Rafael",
                            "isbn":"9999999999",
                            "editora":"Tech",
                            "ano":2024,
                            "categoria":"Backend"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Spring Boot"));
    }

    @Test
    void deveExcluirLivro() throws Exception {

        doNothing().when(livroService).excluir(1L);

        mockMvc.perform(delete("/api/livros/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveRetornar404QuandoLivroNaoExiste() throws Exception {

        when(livroService.buscarPorId(99L))
                .thenThrow(new LivroNaoEncontradoException("Livro não encontrado"));

        mockMvc.perform(get("/api/livros/99"))
                .andExpect(status().isNotFound());
    }
}