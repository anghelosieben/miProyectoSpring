package com.proyecto.demo.service;

import com.proyecto.demo.model.entity.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteService {
    List<Cliente> obtenerPorCiONit(String ciOrNit);
    List<Cliente> obtenerTodos();
    Cliente agregarCliente(Cliente cliente);
}
