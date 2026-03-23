package com.proyecto.demo.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VentaDto {
    private Long id;
    private Long numeroFactura;
    private Double subtotal;
    private Double descuento;
    private Double impuesto;
    private Double total;
    private String tipoPago;
    private String observaciones;
    private ClienteDto cliente;
    private AlmacenDto almacen;
    private List<DetalleVentaDto> detalleVentas;
    private String estado;
    private Date fechaActualizacion;
    private Date fechaRegistro;
}
