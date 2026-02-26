package com.proyecto.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proyecto.demo.model.entity.Role;

/**
 * @author Anghelo Muñoz Lopez
 * @since 2026-02-25
 */
public interface RoleService {
    List<Role> findAll();
    Optional<Role> findById(Long id);
    Optional<Role> findByName(String name);
    Role save(Role role);
    void deleteById(Long id);
    Page<Role> findAllPageable(Pageable pageable);
    boolean existsByName(String name);
}
