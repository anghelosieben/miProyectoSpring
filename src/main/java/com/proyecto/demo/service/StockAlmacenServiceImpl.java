package com.proyecto.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.demo.model.entity.Almacen;
import com.proyecto.demo.model.entity.Producto;
import com.proyecto.demo.model.entity.StockAlmacen;
import com.proyecto.demo.repository.StockAlmacenRepository;

/**
 * @author Anghelo Muñoz Lopez
 * @since 2026-02-25
 */
@Service
@Transactional
public class StockAlmacenServiceImpl implements StockAlmacenService {

    private final StockAlmacenRepository stockAlmacenRepository;

    public StockAlmacenServiceImpl(StockAlmacenRepository stockAlmacenRepository) {
        this.stockAlmacenRepository = stockAlmacenRepository;
    }

    @Override
    public Optional<StockAlmacen> findByProductoAndAlmacen(Long productoId, Long almacenId) {
        return stockAlmacenRepository.findByProductoAndAlmacen(
            Producto.builder().id(productoId).build(),
            Almacen.builder().id(almacenId).build()
        );
    }

    @Override
    public List<StockAlmacen> findByAlmacen(Long almacenId) {
        return stockAlmacenRepository.findByAlmacen(Almacen.builder().id(almacenId).build());
    }

    @Override
    public List<StockAlmacen> findAll() {
        return stockAlmacenRepository.findAll();
    }

    @Override
    public StockAlmacen save(StockAlmacen stockAlmacen) {
        return stockAlmacenRepository.save(stockAlmacen);
    }
}
