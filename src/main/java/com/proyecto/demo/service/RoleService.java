package com.proyecto.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proyecto.demo.dto.RoleDto;
import com.proyecto.demo.model.entity.Role;

public interface RoleService {
    List<RoleDto> findAll();
    Optional<RoleDto> findById(Long id);
    Optional<RoleDto> findByName(String name);
    RoleDto save(RoleDto roleDto);
    void deleteById(Long id);
    Page<RoleDto> findAllPageable(Pageable pageable);
    boolean existsByName(String name);
    Optional<Role> findEntityById(Long id);
}
