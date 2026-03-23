package com.proyecto.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proyecto.demo.dto.CompraDto;
import com.proyecto.demo.model.entity.Compra;

public interface CompraService {
    CompraDto realizarCompra(CompraDto compraDto);
    List<CompraDto> findAll();
    Optional<CompraDto> findById(Long id);
    CompraDto save(CompraDto compraDto);
    void deleteById(Long id);
    Page<CompraDto> findAllPageable(Pageable pageable);
    Optional<Compra> findEntityById(Long id);
}
