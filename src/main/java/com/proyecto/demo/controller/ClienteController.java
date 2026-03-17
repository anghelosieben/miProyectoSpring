package com.proyecto.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.demo.exceptions.ApiResponse;
import com.proyecto.demo.model.entity.Cliente;
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
    public ResponseEntity<ApiResponse<List<Cliente>>> obtenerTodos(HttpServletRequest request) {
        List<Cliente> lista = clienteService.obtenerTodos();
        return ResponseEntity.ok(ApiResponse.success(lista, "Lista de clientes", request.getRequestURI()));
    }

    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<List<Cliente>>> buscarPorCiONit(@RequestParam String termino, HttpServletRequest request) {
        List<Cliente> clientes = clienteService.obtenerPorCiONit(termino);
        if (clientes.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.success(clientes, "No se encontraron clientes", request.getRequestURI()));
        }
        return ResponseEntity.ok(ApiResponse.success(clientes, "Clientes encontrados", request.getRequestURI()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Cliente>> agregarCliente(@RequestBody Cliente cliente, HttpServletRequest request) {
        Cliente nuevo = clienteService.agregarCliente(cliente);
        return ResponseEntity.ok(ApiResponse.success(nuevo, "Cliente creado", request.getRequestURI()));
    }
}
