package com.proyecto.demo.dto;

import java.util.Date;
import java.util.List;

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
public class CompraDto {
    private Long id;
    private Long numeroFactura;
    private Double subtotal;
    private Double impuesto;
    private Double total;
    private String observaciones;
    private ProveedorDto proveedor;
    private List<DetalleCompraDto> detalleCompras;
    private AlmacenDto almacen;
    private String estado;
    private Date fechaActualizacion;
    private Date fechaRegistro;
}
