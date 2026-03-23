package com.proyecto.demo.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.demo.dto.CompraDto;
import com.proyecto.demo.dto.DetalleCompraDto;
import com.proyecto.demo.mapper.AlmacenMapper;
import com.proyecto.demo.mapper.CompraMapper;
import com.proyecto.demo.mapper.ProductoMapper;
import com.proyecto.demo.mapper.ProveedorMapper;
import com.proyecto.demo.model.entity.Almacen;
import com.proyecto.demo.model.entity.Compra;
import com.proyecto.demo.model.entity.DetalleCompra;
import com.proyecto.demo.model.entity.MovimientoInventario;
import com.proyecto.demo.model.entity.Producto;
import com.proyecto.demo.model.entity.StockAlmacen;
import com.proyecto.demo.repository.CompraRepository;
import com.proyecto.demo.repository.MovimientoInventarioRepository;
import com.proyecto.demo.repository.ProductoRepository;
import com.proyecto.demo.repository.StockAlmacenRepository;
import com.proyecto.demo.security.utils.SecurityUtils;

@Service
@Transactional(readOnly = false)
public class CompraServiceImpl implements CompraService {
    private final CompraRepository compraRepository;
    private final MovimientoInventarioRepository movimientoInventarioRepository;
    private final ProductoRepository productoRepository;
    private final StockAlmacenRepository stockAlmacenRepository;
    private final SecurityUtils securityUtils;
    private final CompraMapper compraMapper;
    private final ProveedorMapper proveedorMapper;
    private final AlmacenMapper almacenMapper;
    private final ProductoMapper productoMapper;

    public CompraServiceImpl(CompraRepository compraRepository, 
                            MovimientoInventarioRepository movimientoInventarioRepository, 
                            ProductoRepository productoRepository,
                            StockAlmacenRepository stockAlmacenRepository,
                            SecurityUtils securityUtils,
                            CompraMapper compraMapper,
                            ProveedorMapper proveedorMapper,
                            AlmacenMapper almacenMapper,
                            ProductoMapper productoMapper) {
        this.compraRepository = compraRepository;
        this.movimientoInventarioRepository = movimientoInventarioRepository;
        this.productoRepository = productoRepository;
        this.stockAlmacenRepository = stockAlmacenRepository;
        this.securityUtils = securityUtils;
        this.compraMapper = compraMapper;
        this.proveedorMapper = proveedorMapper;
        this.almacenMapper = almacenMapper;
        this.productoMapper = productoMapper;
    }

    @Override
    @Transactional
    public CompraDto realizarCompra(CompraDto compraDto) {
        Almacen almacen = securityUtils.getCurrentAlmacen();
        
        if (compraDto.getDetalleCompras() == null || compraDto.getDetalleCompras().isEmpty()) {
            throw new IllegalArgumentException("La compra debe tener al menos un detalle");
        }
        
        Compra compra = compraMapper.toEntity(compraDto);
        double subtotal = 0.0;

        for (DetalleCompra detalle : compra.getDetalleCompras()) {
            Producto producto = detalle.getProducto();
            if (producto == null || producto.getId() == null) {
                throw new IllegalArgumentException("Todo detalle debe tener un producto válido");
            }

            Integer cantidad = detalle.getCantidad();
            if (cantidad == null || cantidad <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
            }

            double precio = detalle.getPrecio_unitario();
            if (precio <= 0) {
                precio = producto.getPrecioCompra();
                detalle.setPrecio_unitario(precio);
            }

            double subDetalle = cantidad * precio;
            detalle.setSubtotal(subDetalle);
            subtotal += subDetalle;

            detalle.setCompra(compra);
        }
        
        var compraGuardada = compraRepository.save(compra);

        for (DetalleCompra detalle : compraGuardada.getDetalleCompras()) {
            Producto producto = productoRepository.findById(detalle.getProducto().getId()).orElse(null);
            
            StockAlmacen stockAlmacen = stockAlmacenRepository
                    .findByProductoAndAlmacen(producto, almacen)
                    .orElse(null);
            
            int stockAnterior = (stockAlmacen != null) ? stockAlmacen.getCantidadActual() : 0;
            
            if (stockAlmacen == null) {
                stockAlmacen = StockAlmacen.builder()
                        .producto(producto)
                        .almacen(almacen)
                        .cantidadActual(0)
                        .build();
            }
            
            int stockPosterior = stockAnterior + detalle.getCantidad();
            stockAlmacen.setCantidadActual(stockPosterior);
            stockAlmacenRepository.save(stockAlmacen);
            
            MovimientoInventario mov = MovimientoInventario.builder()
                    .producto(producto)
                    .almacen(almacen)
                    .cantidad(BigDecimal.valueOf(detalle.getCantidad()))
                    .stockAnterior(BigDecimal.valueOf(stockAnterior))
                    .stockPosterior(BigDecimal.valueOf(stockPosterior))
                    .tipoMovimiento("COMPRA")
                    .documentoReferencia("Compra ID: " + compraGuardada.getId())
                    .observacion("Movimiento generado por compra")
                    .build();

            movimientoInventarioRepository.save(mov);
        }
        return compraMapper.toDto(compraGuardada);
    }

    @Override
    public List<CompraDto> findAll() {
        return compraRepository.findAll().stream()
                .map(compraMapper::toDto)
                .toList();
    }

    @Override
    public Optional<CompraDto> findById(Long id) {
        return compraRepository.findById(id)
                .map(compraMapper::toDto);
    }

    @Override
    public CompraDto save(CompraDto compraDto) {
        Compra compra = compraMapper.toEntity(compraDto);
        Compra saved = compraRepository.save(compra);
        return compraMapper.toDto(saved);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Compra> compraOpt = compraRepository.findById(id);
        if (compraOpt.isPresent()) {
            Compra compra = compraOpt.get();
            compra.setEstado("AN");
            compraRepository.save(compra);
        }
    }

    @Override
    public Page<CompraDto> findAllPageable(Pageable pageable) {
        return compraRepository.findAll(pageable)
                .map(compraMapper::toDto);
    }

    @Override
    public Optional<Compra> findEntityById(Long id) {
        return compraRepository.findById(id);
    }

}
