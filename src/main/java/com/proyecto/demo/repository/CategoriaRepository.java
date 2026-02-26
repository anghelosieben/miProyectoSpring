package com.proyecto.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.demo.model.entity.Categoria;

/**
 * @author Anghelo Muñoz Lopez
 * @since 2026-02-25
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findAll();
}
