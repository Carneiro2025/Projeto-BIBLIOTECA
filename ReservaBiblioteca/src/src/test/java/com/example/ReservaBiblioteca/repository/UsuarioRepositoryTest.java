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

    @Test
    void deveBuscarUsuarioPorMatricula() {

    Usuario usuario = new Usuario();

    usuario.setNome("Rafael");
    usuario.setMatricula("2025001");
    usuario.setEmail("rafael@email.com");

    usuarioRepository.save(usuario);

    Usuario encontrado =
            usuarioRepository.findByMatricula("2025001");

    assertNotNull(encontrado);
    assertEquals("Rafael", encontrado.getNome());
   }

    @Test
    void deveBuscarUsuarioPorEmail() {

    Usuario usuario = new Usuario();

    usuario.setNome("Admin");
    usuario.setMatricula("9999");
    usuario.setEmail("admin@email.com");

    usuarioRepository.save(usuario);

    var encontrado =
            usuarioRepository.findByEmail("admin@email.com");

    assertTrue(encontrado.isPresent());
    assertEquals("Admin", encontrado.get().getNome());
}

}