
package com.example.ReservaBiblioteca.exception;

public class LivroIndisponivelException
        extends RuntimeException {

    public LivroIndisponivelException(String mensagem) {

        super(mensagem);
    }
}
