package com.proyecto.demo.dto;

import java.util.Date;

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
public class DetalleCompraDto {
    private Long id;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
    private String lote;
    private Date fechaVencimiento;
    private ProductoDto producto;
}
