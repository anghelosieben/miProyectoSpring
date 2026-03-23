package com.proyecto.demo.service;

import com.proyecto.demo.dto.ClienteDto;
import com.proyecto.demo.model.entity.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteService {
    List<ClienteDto> obtenerPorCiONit(String ciOrNit);
    List<ClienteDto> obtenerTodos();
    ClienteDto agregarCliente(ClienteDto clienteDto);
    List<ClienteDto> buscarPorCiONit(String termino);
    Optional<Cliente> findEntityById(Long id);
}
