package com.proyecto.demo.service;

import com.proyecto.demo.dto.ClienteDto;
import com.proyecto.demo.mapper.ClienteMapper;
import com.proyecto.demo.model.entity.Cliente;
import com.proyecto.demo.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteServiceImpl(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    @Override
    public List<ClienteDto> obtenerPorCiONit(String ciOrNit) {
        List<Cliente> clientes = clienteRepository.findByNitContaining(ciOrNit);
        if (!clientes.isEmpty()) {
            return clientes.stream().map(clienteMapper::toDto).toList();
        }
        return clienteRepository.findByNit(ciOrNit).stream()
                .map(clienteMapper::toDto)
                .toList();
    }

    @Override
    public List<ClienteDto> obtenerTodos() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper::toDto)
                .toList();
    }

    @Override
    public ClienteDto agregarCliente(ClienteDto clienteDto) {
        Cliente cliente = clienteMapper.toEntity(clienteDto);
        Cliente saved = clienteRepository.save(cliente);
        return clienteMapper.toDto(saved);
    }

    @Override
    public List<ClienteDto> buscarPorCiONit(String termino) {
        return clienteRepository.findByCiStartingWith(termino).stream()
                .map(clienteMapper::toDto)
                .toList();
    }

    @Override
    public Optional<Cliente> findEntityById(Long id) {
        return clienteRepository.findById(id);
    }
}
