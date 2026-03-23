package com.proyecto.demo.mapper;

import org.mapstruct.Mapper;

import com.proyecto.demo.dto.AlmacenDto;
import com.proyecto.demo.model.entity.Almacen;

@Mapper(componentModel = "spring")
public interface AlmacenMapper {
    AlmacenDto toDto(Almacen almacen);
    Almacen toEntity(AlmacenDto almacenDto);
}
