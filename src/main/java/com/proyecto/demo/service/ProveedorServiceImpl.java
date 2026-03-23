package com.proyecto.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.demo.dto.ProveedorDto;
import com.proyecto.demo.mapper.ProveedorMapper;
import com.proyecto.demo.model.entity.Proveedor;
import com.proyecto.demo.repository.ProveedorRepository;

@Service
@Transactional
public class ProveedorServiceImpl implements ProveedorService {

    private final ProveedorRepository proveedorRepository;
    private final ProveedorMapper proveedorMapper;

    public ProveedorServiceImpl(ProveedorRepository proveedorRepository, ProveedorMapper proveedorMapper) {
        this.proveedorRepository = proveedorRepository;
        this.proveedorMapper = proveedorMapper;
    }

    @Override
    public List<ProveedorDto> findAll() {
        return proveedorRepository.findAll().stream()
                .map(proveedorMapper::toDto)
                .toList();
    }

    @Override
    public Optional<ProveedorDto> findById(Long id) {
        return proveedorRepository.findById(id)
                .map(proveedorMapper::toDto);
    }

    @Override
    public ProveedorDto save(ProveedorDto proveedorDto) {
        Proveedor proveedor = proveedorMapper.toEntity(proveedorDto);
        Proveedor saved = proveedorRepository.save(proveedor);
        return proveedorMapper.toDto(saved);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Proveedor> proveedorOpt = proveedorRepository.findById(id);
        if (proveedorOpt.isPresent()) {
            Proveedor proveedor = proveedorOpt.get();
            proveedor.setEstado("AN");
            proveedorRepository.save(proveedor);
        }
    }

    @Override
    public Page<ProveedorDto> findAllPageable(Pageable pageable) {
        return proveedorRepository.findAll(pageable)
                .map(proveedorMapper::toDto);
    }

    @Override
    public Optional<Proveedor> findEntityById(Long id) {
        return proveedorRepository.findById(id);
    }
}
