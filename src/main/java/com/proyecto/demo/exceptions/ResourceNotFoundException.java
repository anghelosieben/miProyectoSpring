package com.proyecto.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Esta anotación es opcional si ya usas GlobalExceptionHandler, 
// pero ayuda a Spring a saber el status por defecto.
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    // Constructor que acepta un mensaje personalizado
    public ResourceNotFoundException(String message) {
        super(message);
    }
}