package com.example.ReservaBiblioteca.mapper;

import com.example.ReservaBiblioteca.dto.LivroDTO;
import com.example.ReservaBiblioteca.entity.Livro;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LivroMapper {

    Livro toEntity(LivroDTO dto);

    LivroDTO toDTO(Livro livro);
}
