package com.example.ReservaBiblioteca.service;

import com.example.ReservaBiblioteca.dto.EmprestimoDTO;
import com.example.ReservaBiblioteca.dto.EmprestimoResponseDTO;
import com.example.ReservaBiblioteca.entity.Emprestimo;
import com.example.ReservaBiblioteca.entity.Livro;
import com.example.ReservaBiblioteca.entity.Status;
import com.example.ReservaBiblioteca.entity.Usuario;
import com.example.ReservaBiblioteca.mapper.EmprestimoMapper;
import com.example.ReservaBiblioteca.repository.EmprestimoRepository;
import com.example.ReservaBiblioteca.repository.LivroRepository;
import com.example.ReservaBiblioteca.repository.UsuarioRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmprestimoMapper emprestimoMapper;

    public EmprestimoService(
            EmprestimoRepository emprestimoRepository,
            LivroRepository livroRepository,
            UsuarioRepository usuarioRepository,
            EmprestimoMapper emprestimoMapper) {

        this.emprestimoRepository = emprestimoRepository;
        this.livroRepository = livroRepository;
        this.usuarioRepository = usuarioRepository;
        this.emprestimoMapper = emprestimoMapper;
    }

    // =========================
    // CRIAR EMPRÉSTIMO
    // =========================

    @Transactional
    public EmprestimoResponseDTO criarEmprestimo(EmprestimoDTO dto) {

        Usuario usuario = usuarioRepository
                .findById(dto.getUsuarioId())
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        Livro livro = livroRepository
                .findById(dto.getLivroId())
                .orElseThrow(() ->
                        new RuntimeException("Livro não encontrado"));

        if (livro.getStatus() == Status.EMPRESTADO) {

            throw new RuntimeException(
                    "Livro já está emprestado");
        }

        Emprestimo emprestimo = new Emprestimo();

        emprestimo.setUsuario(usuario);
        emprestimo.setLivro(livro);

        // Define automaticamente
        emprestimo.setDataEmprestimo(LocalDate.now());

        emprestimo.setDevolvido(false);

        livro.setStatus(Status.EMPRESTADO);

        livroRepository.save(livro);

        Emprestimo salvo =
                emprestimoRepository.save(emprestimo);

        return emprestimoMapper.toDTO(salvo);
    }

    // =========================
    // DEVOLVER LIVRO
    // =========================

    @Transactional
    public EmprestimoResponseDTO finalizarEmprestimo(
            Long emprestimoId) {

        Emprestimo emprestimo = emprestimoRepository
                .findById(emprestimoId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Empréstimo não encontrado"));

        if (emprestimo.isDevolvido()) {

            throw new RuntimeException(
                    "Este empréstimo já foi finalizado");
        }

        emprestimo.setDevolvido(true);

        emprestimo.setDataDevolucao(
                LocalDate.now());

        Livro livro = emprestimo.getLivro();

        livro.setStatus(Status.DISPONIVEL);

        livroRepository.save(livro);

        Emprestimo atualizado =
                emprestimoRepository.save(emprestimo);

        return emprestimoMapper.toDTO(atualizado);
    }

    // =========================
    // LISTAR
    // =========================

    public List<EmprestimoResponseDTO> listarEmprestimos() {

        return emprestimoRepository
                .findAll()
                .stream()
                .map(emprestimoMapper::toDTO)
                .toList();
    }
}








