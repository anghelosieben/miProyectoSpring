package com.proyecto.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proyecto.demo.model.entity.Almacen;

/**
 * @author Anghelo Muñoz Lopez
 * @since 2026-02-25
 */
public interface AlmacenService {
    List<Almacen> findAll();
    Optional<Almacen> findById(Long id);
    Almacen save(Almacen almacen);
    void deleteById(Long id);
    Page<Almacen> findAllPageable(Pageable pageable);
}
