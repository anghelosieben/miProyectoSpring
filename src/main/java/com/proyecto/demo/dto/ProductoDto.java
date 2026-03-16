package com.proyecto.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Anghelo Muñoz Lopez
 * @since 2026-03-16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDto {
    private Long id;
    private String codigo;
    private String nombre;
    private String detalle;
    private Long precioCompra;
    private Long precioVenta;
    private String imagen;
    private Long stock;
    private String nombreCategoria;
}
