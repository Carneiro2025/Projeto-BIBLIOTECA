package com.example.ReservaBiblioteca.exception;

public class LivroNaoEncontradoException extends RuntimeException {

    public LivroNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}