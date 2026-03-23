package com.proyecto.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.demo.dto.AlmacenDto;
import com.proyecto.demo.mapper.AlmacenMapper;
import com.proyecto.demo.model.entity.Almacen;
import com.proyecto.demo.repository.AlmacenRepository;

@Service
@Transactional
public class AlmacenServiceImpl implements AlmacenService {

    private final AlmacenRepository almacenRepository;
    private final AlmacenMapper almacenMapper;

    public AlmacenServiceImpl(AlmacenRepository almacenRepository, AlmacenMapper almacenMapper) {
        this.almacenRepository = almacenRepository;
        this.almacenMapper = almacenMapper;
    }

    @Override
    public List<AlmacenDto> findAll() {
        return almacenRepository.findAll().stream()
                .map(almacenMapper::toDto)
                .toList();
    }

    @Override
    public Optional<AlmacenDto> findById(Long id) {
        return almacenRepository.findById(id)
                .map(almacenMapper::toDto);
    }

    @Override
    public AlmacenDto save(AlmacenDto almacenDto) {
        Almacen almacen = almacenMapper.toEntity(almacenDto);
        Almacen saved = almacenRepository.save(almacen);
        return almacenMapper.toDto(saved);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Almacen> almacenOpt = almacenRepository.findById(id);
        if (almacenOpt.isPresent()) {
            Almacen almacen = almacenOpt.get();
            almacen.setEstado("AN");
            almacenRepository.save(almacen);
        }
    }

    @Override
    public Page<AlmacenDto> findAllPageable(Pageable pageable) {
        return almacenRepository.findAll(pageable)
                .map(almacenMapper::toDto);
    }

    @Override
    public Optional<Almacen> findEntityById(Long id) {
        return almacenRepository.findById(id);
    }
}
