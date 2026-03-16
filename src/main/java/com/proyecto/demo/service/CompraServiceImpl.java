package com.proyecto.demo.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

/**
 * @author Anghelo Muñoz Lopez
 */
@Service
@Transactional(readOnly = false)
public class CompraServiceImpl implements CompraService {
    private final CompraRepository compraRepository;
    private final MovimientoInventarioRepository movimientoInventarioRepository;
    private final ProductoRepository productoRepository;
    private final StockAlmacenRepository stockAlmacenRepository;
    private final SecurityUtils securityUtils;

    public CompraServiceImpl(CompraRepository compraRepository, 
                            MovimientoInventarioRepository movimientoInventarioRepository, 
                            ProductoRepository productoRepository,
                            StockAlmacenRepository stockAlmacenRepository,
                            SecurityUtils securityUtils) {
        this.compraRepository = compraRepository;
        this.movimientoInventarioRepository = movimientoInventarioRepository;
        this.productoRepository = productoRepository;
        this.stockAlmacenRepository = stockAlmacenRepository;
        this.securityUtils = securityUtils;
    }

    @Override
    @Transactional
    public Compra realizarCompra(Compra compra) {
        // Obtener el almacén del usuario autenticado
        Almacen almacen = securityUtils.getCurrentAlmacen();
        
        if (compra.getDetalleCompras() == null || compra.getDetalleCompras().isEmpty()) {
            throw new IllegalArgumentException("La compra debe tener al menos un detalle");
        }
        double subtotal = 0.0;

        // 2. Procesar cada detalle (validar, calcular, registrar movimiento, actualizar stock)
        for (DetalleCompra detalle : compra.getDetalleCompras()) {
            Producto producto = detalle.getProducto();
            if (producto == null || producto.getId() == null) {
                throw new IllegalArgumentException("Todo detalle debe tener un producto válido");
            }

            Integer cantidad = detalle.getCantidad();
            if (cantidad == null || cantidad <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
            }

            // Calcular subtotal del detalle
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

        // Actualizar stock en StockAlmacen
        for (DetalleCompra detalle : compraGuardada.getDetalleCompras()) {
            Producto producto = productoRepository.findById(detalle.getProducto().getId()).orElse(null);
            
            // Buscar o crear stock en el almacén
            StockAlmacen stockAlmacen = stockAlmacenRepository
                    .findByProductoAndAlmacen(producto, almacen)
                    .orElse(null);
            
            // Guardar stock anterior antes de modificar
            int stockAnterior = (stockAlmacen != null) ? stockAlmacen.getCantidadActual() : 0;
            
            if (stockAlmacen == null) {
                // Crear nuevo registro de stock
                stockAlmacen = StockAlmacen.builder()
                        .producto(producto)
                        .almacen(almacen)
                        .cantidadActual(0)
                        .build();
            }
            
            // Aumentar stock en el almacén
            int stockPosterior = stockAnterior + detalle.getCantidad();
            stockAlmacen.setCantidadActual(stockPosterior);
            stockAlmacenRepository.save(stockAlmacen);
            
            // Registrar movimiento de inventario
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
        return compraGuardada;
    }

    @Override
    public List<Compra> findAll() {
        return compraRepository.findAll();
    }

    @Override
    public Optional<Compra> findById(Long id) {
        return compraRepository.findById(id);
    }

    @Override
    public Compra save(Compra compra) {
        return compraRepository.save(compra);
    }

    @Override
    public void deleteById(Long id) {
        compraRepository.deleteById(id);
    }

    @Override
    public Page<Compra> findAllPageable(Pageable pageable) {
        return compraRepository.findAll(pageable);
    }

}
