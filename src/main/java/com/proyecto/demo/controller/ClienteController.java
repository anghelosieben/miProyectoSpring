package com.proyecto.demo.controller;

import com.proyecto.demo.model.entity.Cliente;
import com.proyecto.demo.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    /*@GetMapping("/{ciOrNit}")
    public ResponseEntity<Cliente> obtenerPorCiONit(@PathVariable String ciOrNit) {
        Optional<Cliente> cliente = clienteService.obtenerPorCiONit(ciOrNit);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }*/

    @GetMapping("/buscar")
    public ResponseEntity<List<Cliente>> buscarPorCiONit(@RequestParam String termino) {
        List<Cliente> clientes = clienteService.obtenerPorCiONit(termino);
        if (clientes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clientes);
    }

    @GetMapping
    public List<Cliente> obtenerTodos() {
        return clienteService.obtenerTodos();
    }

    @PostMapping
    public ResponseEntity<Cliente> agregarCliente(@RequestBody Cliente cliente) {
        Cliente nuevoCliente = clienteService.agregarCliente(cliente);
        return ResponseEntity.ok(nuevoCliente);
    }
}
