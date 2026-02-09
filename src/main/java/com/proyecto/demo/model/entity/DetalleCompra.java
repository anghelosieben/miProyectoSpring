package com.proyecto.demo.model.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "detalle_compras")
@Data
public class DetalleCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
    private Integer cantidad; 
    private Double precio_unitario; 
    private Double subtotal; 
    private String lote; 

    @Column(name = "fecha_vencimiento")
    private Date fechaVencimiento; 
    @ManyToOne
    @JoinColumn(name = "compra_id")
    private Compra compra; 
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto; 
}
