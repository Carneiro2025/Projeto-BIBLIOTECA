package com.example.ReservaBiblioteca.controller;

import com.example.ReservaBiblioteca.dto.LivroDTO;
import com.example.ReservaBiblioteca.service.LivroService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LivroController.class)
@AutoConfigureMockMvc(addFilters = false)
public class LivroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LivroService livroService;

    /* =====================================================
       TESTE LISTAR LIVROS
    ===================================================== */

    @Test
    void deveListarLivros() throws Exception {

        LivroDTO dto = new LivroDTO();

        dto.setId(1L);
        dto.setTitulo("Clean Code");
        dto.setAutor("Robert Martin");
        dto.setIsbn("123");
        dto.setEditora("Alta Books");
        dto.setAno(2008);
        dto.setCategoria("Programação");
        dto.setStatus("DISPONIVEL");

        when(livroService.listarTodos())
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/livros"))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].titulo")
                        .value("Clean Code"))

                .andExpect(jsonPath("$[0].autor")
                        .value("Robert Martin"));
    }

    /* =====================================================
       TESTE CADASTRAR LIVRO
    ===================================================== */

    @Test
    void deveCadastrarLivro() throws Exception {

        LivroDTO dto = new LivroDTO();

        dto.setId(1L);
        dto.setTitulo("Clean Code");
        dto.setAutor("Robert Martin");
        dto.setIsbn("123");
        dto.setEditora("Alta Books");
        dto.setAno(2008);
        dto.setCategoria("Programação");
        dto.setStatus("DISPONIVEL");

        when(livroService.cadastrar(any(LivroDTO.class)))
                .thenReturn(dto);

        mockMvc.perform(post("/api/livros")

                        .contentType(MediaType.APPLICATION_JSON)

                        .content("""
                                {
                                    "titulo":"Clean Code",
                                    "autor":"Robert Martin",
                                    "isbn":"123",
                                    "editora":"Alta Books",
                                    "ano":2008,
                                    "categoria":"Programação"
                                }
                                """))

                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.titulo")
                        .value("Clean Code"))

                .andExpect(jsonPath("$.autor")
                        .value("Robert Martin"))

                .andExpect(jsonPath("$.status")
                        .value("DISPONIVEL"));
    }

    /* =====================================================
       TESTE VALIDAÇÃO
    ===================================================== */

    @Test
    void naoDeveCadastrarLivroSemTitulo() throws Exception {

        mockMvc.perform(post("/api/livros")

                        .contentType(MediaType.APPLICATION_JSON)

                        .content("""
                                {
                                    "titulo":"",
                                    "autor":"Robert Martin",
                                    "isbn":"123",
                                    "editora":"Alta Books",
                                    "ano":2008,
                                    "categoria":"Programação"
                                }
                                """))

                .andExpect(status().isBadRequest());
}

/* =====================================================
   TESTE EDITAR LIVRO
===================================================== */

@Test
void deveEditarLivro() throws Exception {

    LivroDTO dto = new LivroDTO();

    dto.setId(1L);
    dto.setTitulo("Spring Boot");
    dto.setAutor("Rafael");
    dto.setIsbn("999");
    dto.setEditora("Tech");
    dto.setAno(2024);
    dto.setCategoria("Backend");
    dto.setStatus("DISPONIVEL");

    when(livroService.editar(
            org.mockito.ArgumentMatchers.eq(1L),
            any(LivroDTO.class)))
            .thenReturn(dto);

    mockMvc.perform(put("/api/livros/1")

                    .contentType(MediaType.APPLICATION_JSON)

                    .content("""
                            {
                                "titulo":"Spring Boot",
                                "autor":"Rafael",
                                "isbn":"999",
                                "editora":"Tech",
                                "ano":2024,
                                "categoria":"Backend"
                            }
                            """))

            .andExpect(status().isOk())

            .andExpect(jsonPath("$.titulo")
                    .value("Spring Boot"))

            .andExpect(jsonPath("$.autor")
                    .value("Rafael"));
}

/* =====================================================
   TESTE EXCLUIR LIVRO
===================================================== */

@Test
void deveExcluirLivro() throws Exception {

    mockMvc.perform(delete("/api/livros/1"))

            .andExpect(status().isNoContent());
}
}