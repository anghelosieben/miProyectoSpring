package com.proyecto.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proyecto.demo.model.entity.User;

/**
 * @author Anghelo Muñoz Lopez
 * @since 2026-02-25
 */
public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    User save(User user);
    void deleteById(Long id);
    Page<User> findAllPageable(Pageable pageable);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
