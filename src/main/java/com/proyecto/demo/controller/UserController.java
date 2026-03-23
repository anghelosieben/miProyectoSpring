package com.proyecto.demo.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.demo.dto.UserDto;
import com.proyecto.demo.exceptions.ApiResponse;
import com.proyecto.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Anghelo Muñoz Lopez
 * @since 2026-02-25
 */
@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> listarTodos(HttpServletRequest request) {
        List<UserDto> lista = userService.findAll();
        return ResponseEntity.ok(ApiResponse.success(lista, "Lista de usuarios", request.getRequestURI()));
    }

    @GetMapping("/paginar")
    public ResponseEntity<ApiResponse<Page<UserDto>>> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserDto> pagina = userService.findAllPageable(pageable);
        return ResponseEntity.ok(ApiResponse.success(pagina, "Usuarios paginados", request.getRequestURI()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getById(@PathVariable Long id, HttpServletRequest request) {
        UserDto user = userService.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return ResponseEntity.ok(ApiResponse.success(user, "Usuario encontrado", request.getRequestURI()));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<ApiResponse<UserDto>> obtenerPorUsername(@PathVariable String username, HttpServletRequest request) {
        UserDto user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));
        return ResponseEntity.ok(ApiResponse.success(user, "Usuario encontrado", request.getRequestURI()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserDto>> crear(@RequestBody UserDto user, HttpServletRequest request) {
        if (userService.existsByUsername(user.getUsername())) {
            throw new RuntimeException("El username ya existe");
        }
        if (userService.existsByEmail(user.getEmail())) {
            throw new RuntimeException("El email ya existe");
        }
        UserDto nuevo = userService.save(user);
        return ResponseEntity.ok(ApiResponse.success(nuevo, "Usuario creado", request.getRequestURI()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> actualizar(@PathVariable Long id, @RequestBody UserDto user, HttpServletRequest request) {
        UserDto userExistente = userService.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        
        user.setId(id);
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            user.setPassword(userExistente.getPassword());
        }
        UserDto updated = userService.save(user);
        return ResponseEntity.ok(ApiResponse.success(updated, "Usuario actualizado", request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
