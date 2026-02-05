package com.proyecto.demo.model.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
public class Entidad {
    @Column(name = "usuario_registro")
    private Long usuarioRegistro;
    @Column(name = "usuario_actualizacion")
    private Long usuarioActualizacion;
    @Column(name = "fecha_registro")
    private Date fechaRegistro;
    @Column(name = "fecha_actualizacion")
    private Date fechaActualizacion;
    private String estado;
    public Entidad() {
        this.fechaRegistro = new Date();           // fecha actual al crear
        this.fechaActualizacion = new Date();      // misma fecha al crear
        this.estado = "ACTIVO";                    // valor por defecto más común
        this.usuarioRegistro = 100L;          // valor por defecto temporal
        this.usuarioActualizacion = 100L; 
        // se llenarán cuando se sepa quién está creando/actualizando
        
    }

}
