package com.proyecto.demo.mapper;

import org.mapstruct.Mapper;

import com.proyecto.demo.dto.CategoriaDto;
import com.proyecto.demo.model.entity.Categoria;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    CategoriaDto toDto(Categoria categoria);
    Categoria toEntity(CategoriaDto categoriaDto);
}
