package com.proyecto.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proyecto.demo.model.entity.Proveedor;

/**
 * @author Anghelo Muñoz Lopez
 */
public interface ProveedorService {
    List<Proveedor> findAll();
    Optional<Proveedor> findById(Long id);
    Proveedor save(Proveedor proveedor);
    void deleteById(Long id);
    Page<Proveedor> findAllPageable(Pageable pageable);
}
