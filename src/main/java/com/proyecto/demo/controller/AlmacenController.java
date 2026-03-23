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

import com.proyecto.demo.dto.AlmacenDto;
import com.proyecto.demo.exceptions.ApiResponse;
import com.proyecto.demo.service.AlmacenService;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Anghelo Muñoz Lopez
 * @since 2026-02-25
 */
@RestController
@RequestMapping("/api/almacenes")
public class AlmacenController {

    private final AlmacenService almacenService;

    public AlmacenController(AlmacenService almacenService) {
        this.almacenService = almacenService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AlmacenDto>>> listarTodos(HttpServletRequest request) {
        List<AlmacenDto> lista = almacenService.findAll();
        return ResponseEntity.ok(ApiResponse.success(lista, "Lista de almacenes", request.getRequestURI()));
    }

    @GetMapping("/paginar")
    public ResponseEntity<ApiResponse<Page<AlmacenDto>>> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AlmacenDto> pagina = almacenService.findAllPageable(pageable);
        return ResponseEntity.ok(ApiResponse.success(pagina, "Almacenes paginados", request.getRequestURI()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AlmacenDto>> getById(@PathVariable Long id, HttpServletRequest request) {
        AlmacenDto almacen = almacenService.findById(id)
                .orElseThrow(() -> new RuntimeException("Almacén no encontrado con ID: " + id));
        return ResponseEntity.ok(ApiResponse.success(almacen, "Almacén encontrado", request.getRequestURI()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AlmacenDto>> crear(@RequestBody AlmacenDto almacen, HttpServletRequest request) {
        AlmacenDto saved = almacenService.save(almacen);
        return ResponseEntity.ok(ApiResponse.success(saved, "Almacén creado", request.getRequestURI()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AlmacenDto>> actualizar(@PathVariable Long id, @RequestBody AlmacenDto almacen, HttpServletRequest request) {
        almacen.setId(id);
        AlmacenDto updated = almacenService.save(almacen);
        return ResponseEntity.ok(ApiResponse.success(updated, "Almacén actualizado", request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        almacenService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
