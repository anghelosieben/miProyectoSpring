package com.proyecto.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Usar la anotación de Spring

import com.proyecto.demo.model.entity.Producto;
import com.proyecto.demo.repository.ProductoRepositoryImpl;

@Service
@Transactional(readOnly = true)  // Todas las lecturas son readOnly por default
public class ProductoServiceImpl implements ProductoService {

   private final ProductoRepositoryImpl productoRepository;

    // Inyección por constructor (mejor práctica)
    public ProductoServiceImpl(ProductoRepositoryImpl productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Override
    public Optional<Producto> findById(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    @Transactional  // Esta sí escribe en BD, por eso @Transactional sin readOnly
    public Producto save(Producto producto) {
        // Aquí puedes agregar validaciones de negocio
        if (producto.getPrecio() == null || producto.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio debe ser positivo");
        }
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }

        return productoRepository.save(producto);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto con ID " + id + " no encontrado");
        }
        productoRepository.deleteById(id);
    }

}
