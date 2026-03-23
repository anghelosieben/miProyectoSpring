package com.proyecto.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proyecto.demo.dto.ProductoDto;
import com.proyecto.demo.model.entity.Producto;

public interface ProductoService {
    List<ProductoDto> findAll();
    Optional<ProductoDto> findById(Long id);
    ProductoDto save(ProductoDto productoDto);
    void deleteById(Long id);
    List<ProductoDto> findByNombreOrCodigo(String nombre);
    Page<ProductoDto> obtenerTodos(Pageable pageable);
    Optional<Producto> findEntityById(Long id);
}
