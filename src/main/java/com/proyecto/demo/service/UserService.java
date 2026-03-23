package com.proyecto.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proyecto.demo.dto.UserDto;
import com.proyecto.demo.model.entity.User;

public interface UserService {
    List<UserDto> findAll();
    Optional<UserDto> findById(Long id);
    Optional<UserDto> findByUsername(String username);
    UserDto save(UserDto userDto);
    void deleteById(Long id);
    Page<UserDto> findAllPageable(Pageable pageable);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findEntityById(Long id);
}
