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
import com.proyecto.demo.model.entity.Compra;
import com.proyecto.demo.service.CompraService;

import jakarta.servlet.http.HttpServletRequest;

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
    public ResponseEntity<ApiResponse<List<Compra>>> listarTodos(HttpServletRequest request) {
        List<Compra> lista = compraService.findAll();
        return ResponseEntity.ok(ApiResponse.success(lista, "Lista de compras", request.getRequestURI()));
    }

    @GetMapping("/paginar")
    public ResponseEntity<ApiResponse<Page<Compra>>> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Compra> pagina = compraService.findAllPageable(pageable);
        return ResponseEntity.ok(ApiResponse.success(pagina, "Compras paginadas", request.getRequestURI()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Compra>> getById(@PathVariable Long id, HttpServletRequest request) {
        Compra compra = compraService.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada con ID: " + id));
        return ResponseEntity.ok(ApiResponse.success(compra, "Compra encontrada", request.getRequestURI()));
    }

    @PostMapping("/agregar")
    public ResponseEntity<ApiResponse<Compra>> realizarCompra(@RequestBody Compra compra, HttpServletRequest request) {
        System.out.println("Compra recibida en controller: " + compra);
        Compra nueva = compraService.realizarCompra(compra);
        return ResponseEntity.ok(ApiResponse.success(nueva, "Compra creada", request.getRequestURI()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Compra>> actualizar(@PathVariable Long id, @RequestBody Compra compra, HttpServletRequest request) {
        compra.setId(id);
        Compra updated = compraService.save(compra);
        return ResponseEntity.ok(ApiResponse.success(updated, "Compra actualizada", request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        compraService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
