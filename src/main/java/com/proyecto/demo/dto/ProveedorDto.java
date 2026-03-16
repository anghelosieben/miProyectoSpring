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
public class ProveedorDto {
    private Long id;
    private String nombre;
    private String nit;
    private String telefono;
    private String email;
    private String direccion;
    private String contactoPrincipal;
}
