package com.proyecto.demo.mapper;

import org.mapstruct.Mapper;

import com.proyecto.demo.dto.DetalleVentaDto;
import com.proyecto.demo.model.entity.DetalleVenta;

@Mapper(componentModel = "spring", uses = {ProductoMapper.class})
public interface DetalleVentaMapper {
    DetalleVentaDto toDto(DetalleVenta detalleVenta);
    DetalleVenta toEntity(DetalleVentaDto detalleVentaDto);
}
