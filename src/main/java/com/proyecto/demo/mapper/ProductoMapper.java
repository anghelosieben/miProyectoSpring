package com.proyecto.demo.mapper;

import org.mapstruct.Mapper;

import com.proyecto.demo.dto.ProductoDto;
import com.proyecto.demo.model.entity.Producto;

@Mapper(componentModel = "spring")
public interface ProductoMapper {
    ProductoDto toDto(Producto producto);
    Producto toEntity(ProductoDto productoDto);
}
