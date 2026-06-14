package com.example.ReservaBiblioteca.controller;

import com.example.ReservaBiblioteca.dto.LivroDTO;
import com.example.ReservaBiblioteca.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    // =========================
    // LISTAR TODOS
    // =========================

    @GetMapping
    public List<LivroDTO> listar() {
        return livroService.listarTodos();
    }

    // =========================
    // BUSCAR POR ID
    // =========================

    @GetMapping("/{id}")
    public ResponseEntity<LivroDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                livroService.buscarPorId(id));
    }

    // =========================
    // BUSCAR COM FILTROS
    // =========================

    @GetMapping("/buscar")
    public List<LivroDTO> buscar(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String autor,
            @RequestParam(required = false) String isbn) {

        return livroService.buscar(
                titulo,
                autor,
                isbn);
    }

    // =========================
    // CADASTRAR
    // =========================

    @PostMapping
    public ResponseEntity<LivroDTO> cadastrar(
            @Valid @RequestBody LivroDTO dto) {

        LivroDTO livroSalvo =
                livroService.cadastrar(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(livroSalvo);
    }

    // =========================
    // EDITAR
    // =========================

    @PutMapping("/{id}")
    public ResponseEntity<LivroDTO> editar(
            @PathVariable Long id,
            @Valid @RequestBody LivroDTO dto) {

        LivroDTO atualizado =
                livroService.editar(id, dto);

        return ResponseEntity.ok(atualizado);
    }

    // =========================
    // EXCLUIR
    // =========================

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        livroService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}





