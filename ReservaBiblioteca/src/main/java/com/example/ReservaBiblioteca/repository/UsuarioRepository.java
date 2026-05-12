package com.example.ReservaBiblioteca.repository;

import com.example.ReservaBiblioteca.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByMatricula(String matricula);
}
    