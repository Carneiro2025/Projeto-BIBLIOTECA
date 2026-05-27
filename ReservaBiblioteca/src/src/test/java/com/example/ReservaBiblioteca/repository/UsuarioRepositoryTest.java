package com.example.ReservaBiblioteca.repository;

import com.example.ReservaBiblioteca.entity.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void deveSalvarUsuario() {

        Usuario usuario = new Usuario();

        usuario.setNome("Rafael");
        usuario.setMatricula("2025001");
        usuario.setEmail("rafael@email.com");
        usuario.setContato("81999999999");
        usuario.setSenha("123456");

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        assertNotNull(usuarioSalvo.getId());
    }
}