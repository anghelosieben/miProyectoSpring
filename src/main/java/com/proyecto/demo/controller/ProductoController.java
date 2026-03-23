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

import com.proyecto.demo.dto.ProductoDto;
import com.proyecto.demo.exceptions.ApiResponse;
import com.proyecto.demo.service.ProductoService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductoDto>>> listarTodos() {
        List<ProductoDto> productos = productoService.findAll();
        return ResponseEntity.ok(ApiResponse.success(productos, "Lista de productos", "/api/productos"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductoDto>> getById(@PathVariable Long id, HttpServletRequest request) {
        ProductoDto product = productoService.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        return ResponseEntity.ok(ApiResponse.success(product, "Producto encontrado", request.getRequestURI()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<ProductoDto>> crear(@RequestBody ProductoDto producto, HttpServletRequest request) {
        ProductoDto saved = productoService.save(producto);
        return ResponseEntity.ok(ApiResponse.success(saved, "Producto creado", request.getRequestURI()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductoDto>> actualizar(@PathVariable Long id, @RequestBody ProductoDto producto, HttpServletRequest request) {
        producto.setId(id);
        ProductoDto updated = productoService.save(producto);
        return ResponseEntity.ok(ApiResponse.success(updated, "Producto actualizado", request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id, HttpServletRequest request) {
        productoService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Producto eliminado", request.getRequestURI()));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<ApiResponse<List<ProductoDto>>> obtenerPorNombre(@PathVariable String nombre, HttpServletRequest request) {
        List<ProductoDto> productos = productoService.findByNombreOrCodigo(nombre);
        if (productos.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.success(productos, "No se encontraron productos", request.getRequestURI()));
        }
        return ResponseEntity.ok(ApiResponse.success(productos, "Productos encontrados", request.getRequestURI()));
    }

    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<List<ProductoDto>>> buscarPorNombre(@RequestParam String nombre, HttpServletRequest request) {
        List<ProductoDto> productos = productoService.findByNombreOrCodigo(nombre);
        return ResponseEntity.ok(ApiResponse.success(productos, "Resultado de búsqueda", request.getRequestURI()));
    }

    @GetMapping("/paginar")
    public ResponseEntity<ApiResponse<Page<ProductoDto>>> listarProductos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(ApiResponse.success(productoService.obtenerTodos(pageable), "Productos paginados", "/api/productos/paginar"));
    }
}
