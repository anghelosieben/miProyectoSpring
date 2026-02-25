package com.proyecto.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proyecto.demo.model.entity.MovimientoInventario;

public interface MovimientoInventarioService {
    List<MovimientoInventario> findAll();
    Optional<MovimientoInventario> findById(Long id);
    MovimientoInventario save(MovimientoInventario movimiento);
    void deleteById(Long id);
    Page<MovimientoInventario> findAllPageable(Pageable pageable);
}
