package com.proyecto.demo.mapper;

import org.mapstruct.Mapper;

import com.proyecto.demo.dto.DetalleCompraDto;
import com.proyecto.demo.model.entity.DetalleCompra;

@Mapper(componentModel = "spring", uses = {ProductoMapper.class})
public interface DetalleCompraMapper {
    DetalleCompraDto toDto(DetalleCompra detalleCompra);
    DetalleCompra toEntity(DetalleCompraDto detalleCompraDto);
}
