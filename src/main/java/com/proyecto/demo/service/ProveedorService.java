package com.proyecto.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proyecto.demo.dto.ProveedorDto;
import com.proyecto.demo.model.entity.Proveedor;

public interface ProveedorService {
    List<ProveedorDto> findAll();
    Optional<ProveedorDto> findById(Long id);
    ProveedorDto save(ProveedorDto proveedorDto);
    void deleteById(Long id);
    Page<ProveedorDto> findAllPageable(Pageable pageable);
    Optional<Proveedor> findEntityById(Long id);
}
