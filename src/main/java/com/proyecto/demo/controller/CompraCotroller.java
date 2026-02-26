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

import com.proyecto.demo.model.entity.Compra;
import com.proyecto.demo.service.CompraService;

/**
 * @author Anghelo Muñoz Lopez
 */
@RestController
@RequestMapping("/api/compras")
public class CompraCotroller {
    private final CompraService compraService;
    
    public CompraCotroller(CompraService compraService) {
        this.compraService = compraService;
    }

    @GetMapping
    public List<Compra> listarTodos() {
        return compraService.findAll();
    }

    @GetMapping("/paginar")
    public ResponseEntity<Page<Compra>> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(compraService.findAllPageable(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compra> obtenerPorId(@PathVariable Long id) {
        return compraService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/agregar")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Compra> realizarCompra(@RequestBody Compra compra) {
        System.out.println("Compra recibida en controller: " + compra);
        Compra nuevaCompra = compraService.realizarCompra(compra);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCompra);
    }

    @PutMapping("/{id}")
    public Compra actualizar(@PathVariable Long id, @RequestBody Compra compra) {
        compra.setId(id);
        return compraService.save(compra);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        compraService.deleteById(id);
    }
}
