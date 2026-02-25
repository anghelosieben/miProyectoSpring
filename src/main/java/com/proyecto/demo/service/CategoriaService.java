package com.proyecto.demo.service;

import java.util.List;
import java.util.Optional;

import com.proyecto.demo.model.entity.Categoria;

public interface CategoriaService {
    List<Categoria> findAll();
    Optional<Categoria> findById(Long id);
    Categoria save(Categoria categoria);
    void deleteById(Long id);
}
