package com.proyecto.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoInventarioDto {
    private Long id;
    private Long productoId;
    private String productoNombre;
    private BigDecimal cantidad;
    private String tipoMovimiento;
    private LocalDateTime fechaMovimiento;
    private String documentoReferencia;
    private String observacion;
    private BigDecimal stockAnterior;
    private BigDecimal stockPosterior;
    //private Long almacenId;
    //private String almacenNombre;
    private AlmacenDto almacen; // Incluye el DTO del almacén para obtener su nombre y otros detalles si es necesario.
}
