package com.proyecto.demo.service;

import java.util.List;
import java.util.Optional;

import com.proyecto.demo.dto.VentaDto;
import com.proyecto.demo.model.entity.Venta;

public interface VentaService {

    List<VentaDto> findAll();
    Optional<VentaDto> findById(Long id);
    VentaDto realizarVenta(VentaDto ventaDto);
    Optional<Venta> findEntityById(Long id);
}
