package com.proyecto.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.demo.dto.ProductoDto;
import com.proyecto.demo.mapper.ProductoMapper;
import com.proyecto.demo.model.entity.Producto;
import com.proyecto.demo.repository.ProductoRepository;

@Service
@Transactional(readOnly = true)
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    public ProductoServiceImpl(ProductoRepository productoRepository, ProductoMapper productoMapper) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
    }

    @Override
    public List<ProductoDto> findAll() {
        return productoRepository.findAll().stream()
                .map(productoMapper::toDto)
                .toList();
    }

    @Override
    public Optional<ProductoDto> findById(Long id) {
        return productoRepository.findById(id)
                .map(productoMapper::toDto);
    }

    @Override
    @Transactional
    public ProductoDto save(ProductoDto productoDto) {
        if (productoDto.getPrecioCompra() == null || productoDto.getPrecioCompra() < 0) {
            throw new IllegalArgumentException("El precio de compra debe ser positivo");
        }
        if (productoDto.getPrecioVenta() == null || productoDto.getPrecioVenta() < 0) {
            throw new IllegalArgumentException("El precio de venta debe ser positivo");
        }
        if (productoDto.getNombre() == null || productoDto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }
        
        Producto producto = productoMapper.toEntity(productoDto);
        producto.setUsuarioRegistro(1000L);
        Producto saved = productoRepository.save(producto);
        return productoMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Optional<Producto> productoOpt = productoRepository.findById(id);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            producto.setEstado("AN");
            productoRepository.save(producto);
        }
    }

    @Override
    public List<ProductoDto> findByNombreOrCodigo(String nombre) {
        return productoRepository.buscarPorNombreOCodigo(nombre).stream()
                .map(productoMapper::toDto)
                .toList();
    }

    @Override
    public Page<ProductoDto> obtenerTodos(Pageable pageable) {
        return productoRepository.findAll(pageable)
                .map(productoMapper::toDto);
    }

    @Override
    public Optional<Producto> findEntityById(Long id) {
        return productoRepository.findById(id);
    }
}
