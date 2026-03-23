package com.proyecto.demo.mapper;

import org.mapstruct.Mapper;

import com.proyecto.demo.dto.ProveedorDto;
import com.proyecto.demo.model.entity.Proveedor;

@Mapper(componentModel = "spring")
public interface ProveedorMapper {
    ProveedorDto toDto(Proveedor proveedor);
    Proveedor toEntity(ProveedorDto proveedorDto);
}
