package com.proyecto.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proyecto.demo.dto.MovimientoInventarioDto;
import com.proyecto.demo.model.entity.MovimientoInventario;

public interface MovimientoInventarioService {
    List<MovimientoInventarioDto> findAll();
    Optional<MovimientoInventarioDto> findById(Long id);
    MovimientoInventarioDto save(MovimientoInventarioDto movimientoDto);
    void deleteById(Long id);
    Page<MovimientoInventarioDto> findAllPageable(Pageable pageable);
    Optional<MovimientoInventario> findEntityById(Long id);
}
