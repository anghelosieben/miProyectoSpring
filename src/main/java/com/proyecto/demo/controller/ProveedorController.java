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
import com.proyecto.demo.model.entity.Proveedor;
import com.proyecto.demo.service.ProveedorService;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Anghelo Muñoz Lopez
 * @since 2026-02-25
 */
@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Proveedor>>> listarTodos(HttpServletRequest request) {
        List<Proveedor> lista = proveedorService.findAll();
        return ResponseEntity.ok(ApiResponse.success(lista, "Lista de proveedores", request.getRequestURI()));
    }

    @GetMapping("/paginar")
    public ResponseEntity<ApiResponse<Page<Proveedor>>> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Proveedor> pagina = proveedorService.findAllPageable(pageable);
        return ResponseEntity.ok(ApiResponse.success(pagina, "Proveedores paginados", request.getRequestURI()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Proveedor>> getById(@PathVariable Long id, HttpServletRequest request) {
        Proveedor proveedor = proveedorService.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + id));
        return ResponseEntity.ok(ApiResponse.success(proveedor, "Proveedor encontrado", request.getRequestURI()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Proveedor>> crear(@RequestBody Proveedor proveedor, HttpServletRequest request) {
        Proveedor saved = proveedorService.save(proveedor);
        return ResponseEntity.ok(ApiResponse.success(saved, "Proveedor creado", request.getRequestURI()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Proveedor>> actualizar(@PathVariable Long id, @RequestBody Proveedor proveedor, HttpServletRequest request) {
        proveedor.setId(id);
        Proveedor updated = proveedorService.save(proveedor);
        return ResponseEntity.ok(ApiResponse.success(updated, "Proveedor actualizado", request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        proveedorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
