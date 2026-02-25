package com.proyecto.demo.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.demo.model.entity.MovimientoInventario;
import com.proyecto.demo.service.MovimientoInventarioService;

@RestController
@RequestMapping("/api/movimientos-inventario")
public class MovimientoInventarioController {

    private final MovimientoInventarioService movimientoService;

    public MovimientoInventarioController(MovimientoInventarioService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @GetMapping
    public List<MovimientoInventario> listarTodos() {
        return movimientoService.findAll();
    }

    @GetMapping("/paginar")
    public ResponseEntity<Page<MovimientoInventario>> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(movimientoService.findAllPageable(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoInventario> obtenerPorId(@PathVariable Long id) {
        return movimientoService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovimientoInventario crear(@RequestBody MovimientoInventario movimiento) {
        return movimientoService.save(movimiento);
    }

    @PutMapping("/{id}")
    public MovimientoInventario actualizar(@PathVariable Long id, @RequestBody MovimientoInventario movimiento) {
        movimiento.setId(id);
        return movimientoService.save(movimiento);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        movimientoService.deleteById(id);
    }
}
