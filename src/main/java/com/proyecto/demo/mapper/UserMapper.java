package com.proyecto.demo.mapper;

import org.mapstruct.Mapper;

import com.proyecto.demo.dto.UserDto;
import com.proyecto.demo.model.entity.User;

@Mapper(componentModel = "spring", uses = {AlmacenMapper.class, RoleMapper.class})
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
