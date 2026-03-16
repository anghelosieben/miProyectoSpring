package com.proyecto.demo.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
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

import com.proyecto.demo.model.entity.Producto;
import com.proyecto.demo.model.entity.StockAlmacen;
import com.proyecto.demo.security.utils.SecurityUtils;
import com.proyecto.demo.service.ProductoService;
import com.proyecto.demo.service.StockAlmacenService;

import lombok.RequiredArgsConstructor;

/**
 * @author Anghelo Muñoz Lopez
 * @since 2026-02-25
 */
@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService productoService;
    private final StockAlmacenService stockAlmacenService;
    private final SecurityUtils securityUtils;

    @GetMapping
    public List<Producto> listarTodos() {
        return productoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        return productoService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Producto crear(@RequestBody Producto producto) {
        return productoService.save(producto);
    }

    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        producto.setId(id);
        return productoService.save(producto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        productoService.deleteById(id);
    }
    
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Producto>> obtenerPorNombre(@PathVariable String nombre) {
        return productoService.findByNombreOrCodigo(nombre)
                .isEmpty() ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(productoService.findByNombreOrCodigo(nombre));                
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(productoService.findByNombreOrCodigo(nombre));
    }
    
    @GetMapping("/paginar")
    public ResponseEntity<Page<Producto>> listarProductos(@RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(productoService.obtenerTodos(pageable));
    }
    
    // Endpoints de Stock por Almacén
    
    /**
     * Obtiene el stock de un producto específico en el almacén del usuario actual
     * GET /api/productos/{id}/stock
     */
    @GetMapping("/{id}/stock")
    public ResponseEntity<?> obtenerStockProducto(@PathVariable Long id) {
        try {
            var almacen = securityUtils.getCurrentAlmacen();
            var stock = stockAlmacenService.findByProductoAndAlmacen(id, almacen.getId());
            return ResponseEntity.ok(stock.orElse(null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * Obtiene todos los productos con su stock en el almacén del usuario actual
     * GET /api/productos/con-stock
     */
    @GetMapping("/con-stock")
    public ResponseEntity<?> obtenerProductosConStock() {
        try {
            var almacen = securityUtils.getCurrentAlmacen();
            var stocks = stockAlmacenService.findByAlmacen(almacen.getId());
            return ResponseEntity.ok(stocks);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}
