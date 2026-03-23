package com.proyecto.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proyecto.demo.dto.AlmacenDto;
import com.proyecto.demo.model.entity.Almacen;

public interface AlmacenService {
    List<AlmacenDto> findAll();
    Optional<AlmacenDto> findById(Long id);
    AlmacenDto save(AlmacenDto almacenDto);
    void deleteById(Long id);
    Page<AlmacenDto> findAllPageable(Pageable pageable);
    Optional<Almacen> findEntityById(Long id);
}
