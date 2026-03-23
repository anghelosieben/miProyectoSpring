package com.proyecto.demo.mapper;

import org.mapstruct.Mapper;

import com.proyecto.demo.dto.ClienteDto;
import com.proyecto.demo.model.entity.Cliente;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    ClienteDto toDto(Cliente cliente);
    Cliente toEntity(ClienteDto clienteDto);
}
