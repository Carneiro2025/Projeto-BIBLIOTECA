package com.example.ReservaBiblioteca.exception;

public class EmprestimoNaoEncontradoException
        extends RuntimeException {

    public EmprestimoNaoEncontradoException(String mensagem) {

        super(mensagem);
    }
}
