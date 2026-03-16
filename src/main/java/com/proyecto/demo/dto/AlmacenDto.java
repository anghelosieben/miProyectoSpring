package com.proyecto.demo.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * @author Anghelo Muñoz Lopez
 * @since 2026-03-12
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlmacenDto {
    private Long id;
    private String nombre;
    //private String ubicacion;
}
