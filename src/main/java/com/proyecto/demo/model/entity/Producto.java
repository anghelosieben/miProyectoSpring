package com.proyecto.demo.model.entity;

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
@Table(name = "productos")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto extends Entidad{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;  
    private String nombre;  
    //private Double precio;
    private String detalle; 
    
    @Column(name = "precio_compra")
    private Long precioCompra;
    @Column(name = "precio_venta")
    private Long precioVenta;
    private String imagen; 
    //id (PK), codigo, nombre, descripcion, categoria_id (FK), marca_id (FK), unidad_id (FK), precio_compra, precio_venta, stock_minimo, stock_maximo, imagen_url, activo
    private Long stock;
    
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}
