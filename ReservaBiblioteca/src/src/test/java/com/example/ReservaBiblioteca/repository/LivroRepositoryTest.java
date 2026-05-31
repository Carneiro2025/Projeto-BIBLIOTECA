package com.example.ReservaBiblioteca.repository;

import com.example.ReservaBiblioteca.entity.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LivroRepositoryTest {

    @Autowired
    private LivroRepository repository;

    @Test
    void deveBuscarPorTitulo() {

        Livro livro = new Livro();
        livro.setTitulo("Java Completo");

        repository.save(livro);

        var resultado =
                repository.findByTituloContainingIgnoreCase("java");

        assertThat(resultado).hasSize(1);
    }

    @Test
    void deveBuscarPorAutor() {

        Livro livro = new Livro();
        livro.setTitulo("Spring");

        livro.setAutor("Rafael");

        repository.save(livro);

        var resultado =
                repository.findByAutorContainingIgnoreCase("rafael");

        assertThat(resultado).hasSize(1);
    }
}






