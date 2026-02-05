package com.proyecto.demo.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author anghelo muñoz lopez
 * @version 1.0
 * Clase que representa los permisos en el sistema. 
 * fecha: 27/06/2024
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name; // "READ_PRIVILEGES", "WRITE_PRIVILEGES"
    
    private String description;  // Opcional: "Permiso para leer información de usuarios"
}
