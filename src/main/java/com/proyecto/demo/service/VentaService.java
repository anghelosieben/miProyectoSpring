package com.proyecto.demo.service;

import java.util.List;
import java.util.Optional;

import com.proyecto.demo.model.entity.Venta;

public interface VentaService {

    public List<Venta> findAll();
    public Optional<Venta> findById(Long id);
    public Venta realizarVenta(Venta venta);
    
}
