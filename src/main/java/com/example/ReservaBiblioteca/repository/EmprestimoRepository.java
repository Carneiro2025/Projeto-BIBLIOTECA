package com.example.ReservaBiblioteca.repository;

import com.example.ReservaBiblioteca.entity.Emprestimo;
import com.example.ReservaBiblioteca.entity.Usuario;
import com.example.ReservaBiblioteca.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    List<Emprestimo> findByUsuarioAndDevolvidoFalse(Usuario usuario);
    List<Emprestimo> findByLivroAndDevolvidoFalse(Livro livro);
}

