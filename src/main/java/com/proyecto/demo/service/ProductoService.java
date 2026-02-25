package com.proyecto.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proyecto.demo.model.entity.Producto;

public interface ProductoService {
    List<Producto> findAll();

    Optional<Producto> findById(Long id);

    Producto save(Producto producto);

    void deleteById(Long id);

    List<Producto> findByNombreOrCodigo(String nombre);
    Page<Producto> obtenerTodos(Pageable pageable);
}
