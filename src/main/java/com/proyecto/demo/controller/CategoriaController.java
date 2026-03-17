package com.proyecto.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.demo.exceptions.ApiResponse;
import com.proyecto.demo.model.entity.Categoria;
import com.proyecto.demo.service.CategoriaService;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Anghelo Muñoz Lopez
 * @since 2026-02-25
 */
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Categoria>>> listarTodos(HttpServletRequest request) {
        List<Categoria> lista = categoriaService.findAll();
        return ResponseEntity.ok(ApiResponse.success(lista, "Lista de categorías", request.getRequestURI()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Categoria>> getById(@PathVariable Long id, HttpServletRequest request) {
        Categoria categoria = categoriaService.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
        return ResponseEntity.ok(ApiResponse.success(categoria, "Categoría encontrada", request.getRequestURI()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Categoria>> crear(@RequestBody Categoria categoria, HttpServletRequest request) {
        Categoria saved = categoriaService.save(categoria);
        return ResponseEntity.ok(ApiResponse.success(saved, "Categoría creada", request.getRequestURI()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Categoria>> actualizar(@PathVariable Long id, @RequestBody Categoria categoria, HttpServletRequest request) {
        categoria.setId(id);
        Categoria updated = categoriaService.save(categoria);
        return ResponseEntity.ok(ApiResponse.success(updated, "Categoría actualizada", request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        categoriaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
