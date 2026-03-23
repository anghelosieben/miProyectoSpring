package com.proyecto.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto {
    private Long id;
    private String nit;
    private String nombre;
    private String direccion;
    private String telefono;
    private String estado;
}
