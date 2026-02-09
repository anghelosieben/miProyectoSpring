package com.proyecto.demo.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inventario_movimientos")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimientoInventario extends Entidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private BigDecimal cantidad;  // positivo = entrada, negativo = salida

    @Column(nullable = false)
    private String tipoMovimiento; // "COMPRA", "VENTA", "AJUSTE_ENTRADA", "AJUSTE_SALIDA", "DEVOLUCION", "TRANSFERENCIA"

    @Column(name = "fecha_movimiento")
    private LocalDateTime fechaMovimiento = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;

    private String documentoReferencia; // número de factura, compra, etc.
    private String observacion;

    // Opcional: stock después del movimiento (para auditoría rápida)
    private BigDecimal stockPosterior;
}
