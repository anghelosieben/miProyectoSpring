package com.proyecto.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.proyecto.demo.dto.VentaDto;
import com.proyecto.demo.model.entity.Venta;

@Mapper(componentModel = "spring", uses = {DetalleVentaMapper.class, ClienteMapper.class, AlmacenMapper.class})
public interface VentaMapper {
    
    @Mapping(target = "cliente", source = "venta.cliente")
    @Mapping(target = "almacen", source = "venta.almacen")
    @Mapping(target = "detalleVentas", source = "venta.detalleVentas")
    VentaDto toDto(Venta venta);
    
    Venta toEntity(VentaDto ventaDto);
}
