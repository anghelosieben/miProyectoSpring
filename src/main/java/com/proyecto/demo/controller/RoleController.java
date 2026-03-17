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

import com.proyecto.demo.exceptions.ApiResponse;
import com.proyecto.demo.model.entity.Role;
import com.proyecto.demo.service.RoleService;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Anghelo Muñoz Lopez
 * @since 2026-02-25
 */
@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Role>>> listarTodos(HttpServletRequest request) {
        List<Role> lista = roleService.findAll();
        return ResponseEntity.ok(ApiResponse.success(lista, "Lista de roles", request.getRequestURI()));
    }

    @GetMapping("/paginar")
    public ResponseEntity<ApiResponse<Page<Role>>> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Role> pagina = roleService.findAllPageable(pageable);
        return ResponseEntity.ok(ApiResponse.success(pagina, "Roles paginados", request.getRequestURI()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Role>> getById(@PathVariable Long id, HttpServletRequest request) {
        Role role = roleService.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + id));
        return ResponseEntity.ok(ApiResponse.success(role, "Rol encontrado", request.getRequestURI()));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<ApiResponse<Role>> obtenerPorNombre(@PathVariable String nombre, HttpServletRequest request) {
        Role role = roleService.findByName(nombre)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + nombre));
        return ResponseEntity.ok(ApiResponse.success(role, "Rol encontrado", request.getRequestURI()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Role>> crear(@RequestBody Role role, HttpServletRequest request) {
        if (roleService.existsByName(role.getName())) {
            throw new RuntimeException("El rol ya existe");
        }
        Role nuevo = roleService.save(role);
        return ResponseEntity.ok(ApiResponse.success(nuevo, "Rol creado", request.getRequestURI()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Role>> actualizar(@PathVariable Long id, @RequestBody Role role, HttpServletRequest request) {
        Role roleExistente = roleService.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + id));
        role.setId(id);
        Role updated = roleService.save(role);
        return ResponseEntity.ok(ApiResponse.success(updated, "Rol actualizado", request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        roleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
