package com.proyecto.demo.mapper;

import org.mapstruct.Mapper;

import com.proyecto.demo.dto.RoleDto;
import com.proyecto.demo.model.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toDto(Role role);
    Role toEntity(RoleDto roleDto);
}
