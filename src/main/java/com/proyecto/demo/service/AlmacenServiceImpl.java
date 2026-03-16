package com.proyecto.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.demo.model.entity.Almacen;
import com.proyecto.demo.repository.AlmacenRepository;

/**
 * @author Anghelo Muñoz Lopez
 * @since 2026-02-25
 */
@Service
@Transactional
public class AlmacenServiceImpl implements AlmacenService {

    private final AlmacenRepository almacenRepository;

    public AlmacenServiceImpl(AlmacenRepository almacenRepository) {
        this.almacenRepository = almacenRepository;
    }

    @Override
    public List<Almacen> findAll() {
        return almacenRepository.findAll();
    }

    @Override
    public Optional<Almacen> findById(Long id) {
        return almacenRepository.findById(id);
    }

    @Override
    public Almacen save(Almacen almacen) {
        return almacenRepository.save(almacen);
    }

    @Override
    public void deleteById(Long id) {
        almacenRepository.deleteById(id);
    }

    @Override
    public Page<Almacen> findAllPageable(Pageable pageable) {
        return almacenRepository.findAll(pageable);
    }
}
