package com.example.ReservaBiblioteca.repository;

import com.example.ReservaBiblioteca.entity.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional // garante que cada teste é revertido após execução (isolamento)
class LivroRepositoryTest {

    @Autowired
    private LivroRepository repository;

    @Test
    void deveBuscarPorTitulo() {

        Livro livro = new Livro();
        livro.setTitulo("Java Completo");
        livro.setAutor("Rafael");
        livro.setIsbn("9781234567890");

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
        livro.setIsbn("9780987654321");

        repository.save(livro);

        var resultado =
                repository.findByAutorContainingIgnoreCase("rafael");

        assertThat(resultado).hasSize(1);
    }
}