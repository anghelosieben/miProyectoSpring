package com.proyecto.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.proyecto.demo.dto.CompraDto;
import com.proyecto.demo.model.entity.Compra;

@Mapper(componentModel = "spring", uses = {DetalleCompraMapper.class, ProveedorMapper.class, AlmacenMapper.class})
public interface CompraMapper {
    
    @Mapping(target = "proveedor", source = "compra.proveedor")
    @Mapping(target = "almacen", source = "compra.almacen")
    @Mapping(target = "detalleCompras", source = "compra.detalleCompras")
    CompraDto toDto(Compra compra);
    
    Compra toEntity(CompraDto compraDto);
}
