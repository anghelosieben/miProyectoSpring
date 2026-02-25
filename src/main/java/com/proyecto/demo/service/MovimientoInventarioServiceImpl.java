package com.proyecto.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.demo.model.entity.MovimientoInventario;
import com.proyecto.demo.repository.MovimientoInventarioRepository;

@Service
@Transactional
public class MovimientoInventarioServiceImpl implements MovimientoInventarioService {

    private final MovimientoInventarioRepository movimientoRepository;

    public MovimientoInventarioServiceImpl(MovimientoInventarioRepository movimientoRepository) {
        this.movimientoRepository = movimientoRepository;
    }

    @Override
    public List<MovimientoInventario> findAll() {
        return movimientoRepository.findAll();
    }

    @Override
    public Optional<MovimientoInventario> findById(Long id) {
        return movimientoRepository.findById(id);
    }

    @Override
    public MovimientoInventario save(MovimientoInventario movimiento) {
        return movimientoRepository.save(movimiento);
    }

    @Override
    public void deleteById(Long id) {
        movimientoRepository.deleteById(id);
    }

    @Override
    public Page<MovimientoInventario> findAllPageable(Pageable pageable) {
        return movimientoRepository.findAll(pageable);
    }
}
