package com.proyecto.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.demo.model.entity.Compra;
import com.proyecto.demo.service.CompraService;

@RestController
@RequestMapping("/api/compras")
public class CompraCotroller {
    private final CompraService compraService;
    
    public CompraCotroller(CompraService compraService) {
        this.compraService = compraService;
    }
    @PostMapping("/agregar")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Compra> realizarCompra(@RequestBody Compra compra) {
        System.out.println("Compra recibida en controller: " + compra);
        Compra nuevaCompra = compraService.realizarCompra(compra);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCompra);
    }
}
