package com.proyecto.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.demo.dto.CategoriaDto;
import com.proyecto.demo.mapper.CategoriaMapper;
import com.proyecto.demo.model.entity.Categoria;
import com.proyecto.demo.repository.CategoriaRepository;

@Service
@Transactional
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
    }

    @Override
    public List<CategoriaDto> findAll() {
        return categoriaRepository.findAll().stream()
                .map(categoriaMapper::toDto)
                .toList();
    }

    @Override
    public Optional<CategoriaDto> findById(Long id) {
        return categoriaRepository.findById(id)
                .map(categoriaMapper::toDto);
    }

    @Override
    public CategoriaDto save(CategoriaDto categoriaDto) {
        Categoria categoria = categoriaMapper.toEntity(categoriaDto);
        Categoria saved = categoriaRepository.save(categoria);
        return categoriaMapper.toDto(saved);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Categoria> categoriaOpt = categoriaRepository.findById(id);
        if (categoriaOpt.isPresent()) {
            Categoria categoria = categoriaOpt.get();
            categoria.setEstado("AN");
            categoriaRepository.save(categoria);
        }
    }

    @Override
    public Optional<Categoria> findEntityById(Long id) {
        return categoriaRepository.findById(id);
    }
}
