package com.proyecto.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.demo.dto.ClienteDto;
import com.proyecto.demo.exceptions.ApiResponse;
import com.proyecto.demo.service.ClienteService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClienteDto>>> obtenerTodos(HttpServletRequest request) {
        List<ClienteDto> lista = clienteService.obtenerTodos();
        return ResponseEntity.ok(ApiResponse.success(lista, "Lista de clientes", request.getRequestURI()));
    }

    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<List<ClienteDto>>> buscarPorCiONit(@RequestParam String termino, HttpServletRequest request) {
        List<ClienteDto> clientes = clienteService.obtenerPorCiONit(termino);
        if (clientes.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.success(clientes, "No se encontraron clientes", request.getRequestURI()));
        }
        return ResponseEntity.ok(ApiResponse.success(clientes, "Clientes encontrados", request.getRequestURI()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ClienteDto>> agregarCliente(@RequestBody ClienteDto cliente, HttpServletRequest request) {
        ClienteDto nuevo = clienteService.agregarCliente(cliente);
        return ResponseEntity.ok(ApiResponse.success(nuevo, "Cliente creado", request.getRequestURI()));
    }
}
