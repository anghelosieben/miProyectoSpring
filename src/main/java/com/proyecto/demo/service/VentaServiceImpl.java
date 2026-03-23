package com.proyecto.demo.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.demo.dto.VentaDto;
import com.proyecto.demo.mapper.VentaMapper;
import com.proyecto.demo.model.entity.Almacen;
import com.proyecto.demo.model.entity.DetalleVenta;
import com.proyecto.demo.model.entity.MovimientoInventario;
import com.proyecto.demo.model.entity.Producto;
import com.proyecto.demo.model.entity.StockAlmacen;
import com.proyecto.demo.model.entity.User;
import com.proyecto.demo.model.entity.Venta;
import com.proyecto.demo.repository.MovimientoInventarioRepository;
import com.proyecto.demo.repository.ProductoRepository;
import com.proyecto.demo.repository.StockAlmacenRepository;
import com.proyecto.demo.repository.VentasRepository;
import com.proyecto.demo.security.utils.SecurityUtils;

@Service
@Transactional(readOnly = true)
public class VentaServiceImpl implements VentaService {
    private final VentasRepository ventasRepository;
    private final ProductoRepository productoRepository;
    private final MovimientoInventarioRepository movimientoInventarioRepository;
    private final StockAlmacenRepository stockAlmacenRepository;
    private final SecurityUtils securityUtils;
    private final VentaMapper ventaMapper;

    public VentaServiceImpl(VentasRepository ventasRepository, ProductoRepository productoRepository, 
                           MovimientoInventarioRepository movimientoInventarioRepository,
                           StockAlmacenRepository stockAlmacenRepository,
                           SecurityUtils securityUtils,
                           VentaMapper ventaMapper) {
        this.ventasRepository = ventasRepository;
        this.productoRepository = productoRepository;
        this.movimientoInventarioRepository = movimientoInventarioRepository;
        this.stockAlmacenRepository = stockAlmacenRepository;
        this.securityUtils = securityUtils;
        this.ventaMapper = ventaMapper;
    }

    @Override
    public List<VentaDto> findAll() {
        return ventasRepository.findAll().stream()
                .map(ventaMapper::toDto)
                .toList();
    }

    @Override
    public Optional<VentaDto> findById(Long id) {
        return ventasRepository.findById(id)
                .map(ventaMapper::toDto);
    }

    @Override
    @Transactional
    public VentaDto realizarVenta(VentaDto ventaDto) {
        User usuario = securityUtils.getCurrentUser();
        Almacen almacen = securityUtils.getCurrentAlmacen();
        
        Venta venta = ventaMapper.toEntity(ventaDto);
        venta.setAlmacen(almacen);
        
        if (venta.getCliente() == null) {
            throw new IllegalArgumentException("La venta debe tener un cliente");
        }
        if (venta.getDetalleVentas() == null || venta.getDetalleVentas().isEmpty()) {
            throw new IllegalArgumentException("La venta debe tener al menos un detalle");
        }

        double subtotal = 0.0;
        double descuentoTotal = 0.0;

        for (DetalleVenta detalle : venta.getDetalleVentas()) {
            Producto producto = detalle.getProducto();
            Integer cantidad = detalle.getCantidad();
            if (cantidad == null || cantidad <= 0) {
                throw new IllegalArgumentException("Cantidad inválida en detalle");
            }

            Optional<StockAlmacen> stockOpt = stockAlmacenRepository.findByProductoAndAlmacen(producto, almacen);
            int stockActual = stockOpt.map(s -> s.getCantidadActual()).orElse(0);
            
            if (stockActual < cantidad) {
                throw new IllegalStateException("Stock insuficiente en almacén para: " + producto.getNombre() + ". Stock actual: " + stockActual);
            }

            double precio = producto.getPrecioVenta();
            detalle.setPrecioUnitario(precio);
            double subDetalle = cantidad * precio;
            detalle.setSubtotal(subDetalle);

            subtotal += subDetalle;

            double descuentoItem = detalle.getDescuentoItem() != null ? detalle.getDescuentoItem() : 0;
            descuentoTotal += descuentoItem;

            detalle.setVenta(venta);
        }

        venta.setSubtotal(subtotal);
        venta.setDescuento(descuentoTotal);
        venta.setImpuesto(subtotal * 0.13);
        venta.setTotal(subtotal + venta.getImpuesto() - descuentoTotal);

        Venta ventaGuardada = ventasRepository.save(venta);

        for (DetalleVenta detalle : ventaGuardada.getDetalleVentas()) {
            Producto producto = productoRepository.findById(detalle.getProducto().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + detalle.getProducto().getId()));

            StockAlmacen stockAlmacen = stockAlmacenRepository
                    .findByProductoAndAlmacen(producto, almacen)
                    .orElseThrow(() -> new IllegalArgumentException("No hay stock para el producto en este almacén"));
            
            int stockAnterior = stockAlmacen.getCantidadActual();
            int stockPosterior = stockAnterior - detalle.getCantidad();
            
            stockAlmacen.setCantidadActual(stockPosterior);
            stockAlmacenRepository.save(stockAlmacen);
            
            MovimientoInventario mov = MovimientoInventario.builder()
                    .producto(producto)
                    .almacen(almacen)
                    .cantidad(BigDecimal.valueOf(detalle.getCantidad()).negate())
                    .stockAnterior(BigDecimal.valueOf(stockAnterior))
                    .stockPosterior(BigDecimal.valueOf(stockPosterior))
                    .tipoMovimiento("VENTA")
                    .documentoReferencia("Venta ID: " + ventaGuardada.getId())
                    .observacion("Movimiento generado por venta")
                    .build();

            movimientoInventarioRepository.save(mov);
        }

        return ventaMapper.toDto(ventaGuardada);
    }

    @Override
    public Optional<Venta> findEntityById(Long id) {
        return ventasRepository.findById(id);
    }

}
