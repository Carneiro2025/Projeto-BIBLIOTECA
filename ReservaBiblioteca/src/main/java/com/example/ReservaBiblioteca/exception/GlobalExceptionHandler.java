package com.example.ReservaBiblioteca.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ==================================================
    // ERROS DE VALIDAÇÃO
    // ==================================================

    @ExceptionHandler(MethodArgumentNotValidException.class)

    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->

                errors.put(
                        error.getField(),
                        error.getDefaultMessage()));

        return ResponseEntity
                .badRequest()
                .body(errors);
    }

    // ==================================================
    // LIVRO NÃO ENCONTRADO
    // ==================================================

    @ExceptionHandler(LivroNaoEncontradoException.class)

    public ResponseEntity<Map<String, Object>> handleLivroNaoEncontrado(
            LivroNaoEncontradoException ex) {

        Map<String, Object> erro = new HashMap<>();

        erro.put("data", LocalDateTime.now());

        erro.put("status", 404);

        erro.put("erro", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(erro);
    }

    // ==================================================
    // USUÁRIO NÃO ENCONTRADO
    // ==================================================

    @ExceptionHandler(UsuarioNaoEncontradoException.class)

    public ResponseEntity<Map<String, Object>> handleUsuarioNaoEncontrado(
            UsuarioNaoEncontradoException ex) {

        Map<String, Object> erro = new HashMap<>();

        erro.put("data", LocalDateTime.now());

        erro.put("status", 404);

        erro.put("erro", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(erro);
    }

    // ==================================================
    // LIVRO INDISPONÍVEL
    // ==================================================

    @ExceptionHandler(LivroIndisponivelException.class)

    public ResponseEntity<Map<String, Object>> handleLivroIndisponivel(
            LivroIndisponivelException ex) {

        Map<String, Object> erro = new HashMap<>();

        erro.put("data", LocalDateTime.now());

        erro.put("status", 400);

        erro.put("erro", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(erro);
    }

    // ==================================================
    // ERRO GENÉRICO
    // ==================================================

    @ExceptionHandler(Exception.class)

    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {

        Map<String, Object> erro = new HashMap<>();

        erro.put("data", LocalDateTime.now());

        erro.put("status", 500);

        erro.put("erro", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(erro);
    }
}
