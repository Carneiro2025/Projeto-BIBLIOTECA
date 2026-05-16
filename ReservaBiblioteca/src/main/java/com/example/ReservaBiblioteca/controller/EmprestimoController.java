package com.example.ReservaBiblioteca.controller;

import com.example.ReservaBiblioteca.dto.EmprestimoDTO;
import com.example.ReservaBiblioteca.entity.Emprestimo;
import com.example.ReservaBiblioteca.service.EmprestimoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

// Define esta classe como um controlador REST
@RestController
// Define a rota base da API    
@RequestMapping("/api/emprestimos")
    // Injeta o serviço de empréstimos
public class EmprestimoController {
       // Construtor para injeção de dependência
    private final EmprestimoService emprestimoService;
    // Construtor para injeção de dependência
    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }
     // Endpoint para registrar um novo empréstimo
    // Método HTTP: POST
    @PostMapping
    public ResponseEntity<Emprestimo> registrar(@RequestBody EmprestimoDTO dto) {
        Emprestimo emprestimo = emprestimoService.criarEmprestimo(dto);
        // Retorna status 200 OK com o objeto criado
        return ResponseEntity.ok(emprestimo);
    }
     // Endpoint para devolver/finalizar um empréstimo
    // Método HTTP: PUT
    @PutMapping("/{id}/devolucao")
    public ResponseEntity<Emprestimo> devolver(@PathVariable Long id) {
        Emprestimo emprestimo = emprestimoService.finalizarEmprestimo(id);
        return ResponseEntity.ok(emprestimo);
    }
}
