package com.proyecto.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.demo.model.entity.Almacen;

/**
 * @author Anghelo Muñoz Lopez
 * @since 2026-02-25
 */
@Repository
public interface AlmacenRepository extends JpaRepository<Almacen, Long> {
}
