package com.example.ReservaBiblioteca.exception;

public class UsuarioNaoEncontradoException
        extends RuntimeException {

    public UsuarioNaoEncontradoException(String mensagem) {

        super(mensagem);
    }
}
