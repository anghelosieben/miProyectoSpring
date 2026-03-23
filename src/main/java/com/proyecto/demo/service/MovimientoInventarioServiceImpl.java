package com.proyecto.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.demo.dto.MovimientoInventarioDto;
import com.proyecto.demo.mapper.MovimientoInventarioMapper;
import com.proyecto.demo.model.entity.MovimientoInventario;
import com.proyecto.demo.repository.MovimientoInventarioRepository;

@Service
@Transactional
public class MovimientoInventarioServiceImpl implements MovimientoInventarioService {

    private final MovimientoInventarioRepository movimientoRepository;
    private final MovimientoInventarioMapper movimientoMapper;

    public MovimientoInventarioServiceImpl(MovimientoInventarioRepository movimientoRepository, 
                                           MovimientoInventarioMapper movimientoMapper) {
        this.movimientoRepository = movimientoRepository;
        this.movimientoMapper = movimientoMapper;
    }

    @Override
    public List<MovimientoInventarioDto> findAll() {
        return movimientoRepository.findAll().stream()
                .map(movimientoMapper::toDto)
                .toList();
    }

    @Override
    public Optional<MovimientoInventarioDto> findById(Long id) {
        return movimientoRepository.findById(id)
                .map(movimientoMapper::toDto);
    }

    @Override
    public MovimientoInventarioDto save(MovimientoInventarioDto movimientoDto) {
        MovimientoInventario movimiento = movimientoMapper.toEntity(movimientoDto);
        MovimientoInventario saved = movimientoRepository.save(movimiento);
        return movimientoMapper.toDto(saved);
    }

    @Override
    public void deleteById(Long id) {
        Optional<MovimientoInventario> movimientoOpt = movimientoRepository.findById(id);
        if (movimientoOpt.isPresent()) {
            MovimientoInventario movimiento = movimientoOpt.get();
            movimiento.setEstado("AN");
            movimientoRepository.save(movimiento);
        }
    }

    @Override
    public Page<MovimientoInventarioDto> findAllPageable(Pageable pageable) {
        return movimientoRepository.findAll(pageable)
                .map(movimientoMapper::toDto);
    }

    @Override
    public Optional<MovimientoInventario> findEntityById(Long id) {
        return movimientoRepository.findById(id);
    }
}
