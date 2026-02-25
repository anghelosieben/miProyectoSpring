package com.proyecto.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proyecto.demo.model.entity.Compra;

/**
 * @author Anghelo Muñoz Lopez
 */
public interface CompraService {
    Compra realizarCompra(Compra compra);
    List<Compra> findAll();
    Optional<Compra> findById(Long id);
    Compra save(Compra compra);
    void deleteById(Long id);
    Page<Compra> findAllPageable(Pageable pageable);
}
