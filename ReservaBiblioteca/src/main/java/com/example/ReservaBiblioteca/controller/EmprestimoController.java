package com.example.ReservaBiblioteca.controller;

import com.example.ReservaBiblioteca.dto.EmprestimoDTO;
import com.example.ReservaBiblioteca.dto.EmprestimoResponseDTO;
import com.example.ReservaBiblioteca.service.EmprestimoService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emprestimos")
@CrossOrigin(origins = "*")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping
    public ResponseEntity<EmprestimoResponseDTO> registrar(
            @Valid @RequestBody EmprestimoDTO dto) {

        EmprestimoResponseDTO response =
                emprestimoService.criarEmprestimo(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}/devolucao")
    public ResponseEntity<EmprestimoResponseDTO> devolver(
            @PathVariable Long id) {

        EmprestimoResponseDTO response =
                emprestimoService.finalizarEmprestimo(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<EmprestimoResponseDTO>> listar() {

        List<EmprestimoResponseDTO> lista =
                emprestimoService.listarEmprestimos();

        return ResponseEntity.ok(lista);
    }
}