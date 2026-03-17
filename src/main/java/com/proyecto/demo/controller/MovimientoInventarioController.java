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
import com.proyecto.demo.model.entity.MovimientoInventario;
import com.proyecto.demo.service.MovimientoInventarioService;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Anghelo Muñoz Lopez
 * @since 2026-02-25
 */
@RestController
@RequestMapping("/api/movimientos-inventario")
public class MovimientoInventarioController {

    private final MovimientoInventarioService movimientoService;

    public MovimientoInventarioController(MovimientoInventarioService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MovimientoInventario>>> listarTodos(HttpServletRequest request) {
        List<MovimientoInventario> lista = movimientoService.findAll();
        return ResponseEntity.ok(ApiResponse.success(lista, "Lista de movimientos", request.getRequestURI()));
    }

    @GetMapping("/paginar")
    public ResponseEntity<ApiResponse<Page<MovimientoInventario>>> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MovimientoInventario> pagina = movimientoService.findAllPageable(pageable);
        return ResponseEntity.ok(ApiResponse.success(pagina, "Movimientos paginados", request.getRequestURI()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MovimientoInventario>> getById(@PathVariable Long id, HttpServletRequest request) {
        MovimientoInventario movimiento = movimientoService.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado con ID: " + id));
        return ResponseEntity.ok(ApiResponse.success(movimiento, "Movimiento encontrado", request.getRequestURI()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MovimientoInventario>> crear(@RequestBody MovimientoInventario movimiento, HttpServletRequest request) {
        MovimientoInventario saved = movimientoService.save(movimiento);
        return ResponseEntity.ok(ApiResponse.success(saved, "Movimiento creado", request.getRequestURI()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MovimientoInventario>> actualizar(@PathVariable Long id, @RequestBody MovimientoInventario movimiento, HttpServletRequest request) {
        movimiento.setId(id);
        MovimientoInventario updated = movimientoService.save(movimiento);
        return ResponseEntity.ok(ApiResponse.success(updated, "Movimiento actualizado", request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        movimientoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
