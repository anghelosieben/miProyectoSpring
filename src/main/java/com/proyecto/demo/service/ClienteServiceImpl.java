package com.proyecto.demo.service;

import com.proyecto.demo.model.entity.Cliente;
import com.proyecto.demo.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<Cliente> obtenerPorCiONit(String ciOrNit) {
        List<Cliente> cliente = clienteRepository.findByCi(ciOrNit);
        if (!cliente.isEmpty()) {
            return cliente;
        }
        return clienteRepository.findByNit(ciOrNit);
    }

    @Override
    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente agregarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
}
