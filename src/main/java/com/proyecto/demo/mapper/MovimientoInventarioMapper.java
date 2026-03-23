package com.proyecto.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.proyecto.demo.dto.MovimientoInventarioDto;
import com.proyecto.demo.model.entity.MovimientoInventario;

@Mapper(componentModel = "spring")
public interface MovimientoInventarioMapper {
    
    @Mapping(target = "productoId", source = "movimiento.producto.id")
    @Mapping(target = "productoNombre", source = "movimiento.producto.nombre")
    //@Mapping(target = "almacenId", source = "movimiento.almacen.id")
    //@Mapping(target = "almacenNombre", source = "movimiento.almacen.nombre")
    MovimientoInventarioDto toDto(MovimientoInventario movimiento);
    
    MovimientoInventario toEntity(MovimientoInventarioDto movimientoDto);
}
