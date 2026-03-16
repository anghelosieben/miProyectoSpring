package com.proyecto.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.demo.model.entity.Almacen;
import com.proyecto.demo.model.entity.Producto;
import com.proyecto.demo.model.entity.StockAlmacen;

/**
 * @author Anghelo Muñoz Lopez
 * @since 2026-02-25
 */
@Repository
public interface StockAlmacenRepository extends JpaRepository<StockAlmacen, Long> {
    Optional<StockAlmacen> findByProductoAndAlmacen(Producto producto, Almacen almacen);
    List<StockAlmacen> findByAlmacen(Almacen almacen);
}
