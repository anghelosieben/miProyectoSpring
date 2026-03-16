package com.proyecto.demo.service;

import java.util.List;
import java.util.Optional;

import com.proyecto.demo.model.entity.StockAlmacen;

/**
 * @author Anghelo Muñoz Lopez
 * @since 2026-02-25
 */
public interface StockAlmacenService {
    Optional<StockAlmacen> findByProductoAndAlmacen(Long productoId, Long almacenId);
    List<StockAlmacen> findByAlmacen(Long almacenId);
    List<StockAlmacen> findAll();
    StockAlmacen save(StockAlmacen stockAlmacen);
}
