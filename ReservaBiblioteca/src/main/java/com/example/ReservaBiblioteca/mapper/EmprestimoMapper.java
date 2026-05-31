package com.example.ReservaBiblioteca.mapper;

import com.example.ReservaBiblioteca.dto.EmprestimoResponseDTO;
import com.example.ReservaBiblioteca.entity.Emprestimo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmprestimoMapper {

    @Mapping(source = "usuario.nome", target = "usuario")
    @Mapping(source = "livro.titulo", target = "livro")
    EmprestimoResponseDTO toDTO(Emprestimo emprestimo);
}