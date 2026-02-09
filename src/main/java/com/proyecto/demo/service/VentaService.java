package com.proyecto.demo.service;

import java.util.List;

import com.proyecto.demo.model.entity.Venta;

public interface VentaService {

    public List<Venta> findAll();
    public Venta findById(Long id);
    public Venta realizarVenta(Venta venta);
    
}
