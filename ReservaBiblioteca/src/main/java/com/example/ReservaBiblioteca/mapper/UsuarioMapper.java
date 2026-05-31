package com.example.ReservaBiblioteca.mapper;

import com.example.ReservaBiblioteca.dto.UsuarioDTO;
import com.example.ReservaBiblioteca.entity.Usuario;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    
    Usuario toEntity(UsuarioDTO dto);

    UsuarioDTO toDTO(Usuario usuario);
}

