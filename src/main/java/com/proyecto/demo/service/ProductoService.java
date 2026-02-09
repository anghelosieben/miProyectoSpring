package com.proyecto.demo.service;

import java.util.List;
import java.util.Optional;

import com.proyecto.demo.model.entity.Producto;

public interface ProductoService {
    List<Producto> findAll();

    Optional<Producto> findById(Long id);

    Producto save(Producto producto);

    void deleteById(Long id);

    List<Producto> findByNombreOrCodigo(String nombre);
}
