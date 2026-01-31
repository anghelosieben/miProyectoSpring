package com.proyecto.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaController {
    @GetMapping("/hola")
    public String saludar() {
        return "¡Hola Anghelooo! Tu Spring Boot 4.0.2 está funcionando perfecto 🚀";
    }

    @GetMapping("/")
    public String raiz() {
        return "Bienvenido a mi proyecto Spring Boot + Angular";
    }
}
