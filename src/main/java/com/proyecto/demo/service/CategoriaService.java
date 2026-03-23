package com.proyecto.demo.service;

import java.util.List;
import java.util.Optional;

import com.proyecto.demo.dto.CategoriaDto;
import com.proyecto.demo.model.entity.Categoria;

public interface CategoriaService {
    List<CategoriaDto> findAll();
    Optional<CategoriaDto> findById(Long id);
    CategoriaDto save(CategoriaDto categoriaDto);
    void deleteById(Long id);
    Optional<Categoria> findEntityById(Long id);
}
