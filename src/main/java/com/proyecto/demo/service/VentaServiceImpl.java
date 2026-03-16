package com.proyecto.demo.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

/**
 * @author Anghelo Muñoz Lopez
 * @since 2026-02-25
 */
@Service
@Transactional(readOnly = true)
public class VentaServiceImpl implements VentaService {
    private final VentasRepository ventasRepository;
    private final ProductoRepository productoRepository;
    private final MovimientoInventarioRepository movimientoInventarioRepository;
    private final StockAlmacenRepository stockAlmacenRepository;
    private final SecurityUtils securityUtils;

    public VentaServiceImpl(VentasRepository ventasRepository, ProductoRepository productoRepository, 
                           MovimientoInventarioRepository movimientoInventarioRepository,
                           StockAlmacenRepository stockAlmacenRepository,
                           SecurityUtils securityUtils) {
        this.ventasRepository = ventasRepository;
        this.productoRepository = productoRepository;
        this.movimientoInventarioRepository = movimientoInventarioRepository;
        this.stockAlmacenRepository = stockAlmacenRepository;
        this.securityUtils = securityUtils;
    }

    @Override
    public List<Venta> findAll() {
        // TODO Auto-generated method stub
        var pVentas = ventasRepository.findAll();
        return pVentas;
    }

    @Override
    public Optional<Venta> findById(Long id) {
        // TODO Auto-generated method stub
        return ventasRepository.findById(id);
    }

    @Override
    @Transactional
    public Venta realizarVenta(Venta venta) {
        // Obtener usuario y almacén del contexto de seguridad
        User usuario = securityUtils.getCurrentUser();
        Almacen almacen = securityUtils.getCurrentAlmacen();
        
        // Asignar usuario y almacén a la venta
        venta.setAlmacen(almacen);
        
        // 1. Validaciones básicas
        if (venta.getCliente() == null) {
            throw new IllegalArgumentException("La venta debe tener un cliente");
        }
        if (venta.getDetalleVentas() == null || venta.getDetalleVentas().isEmpty()) {
            throw new IllegalArgumentException("La venta debe tener al menos un detalle");
        }

        double subtotal = 0.0;
        double descuentoTotal = 0.0;

        // 2. Procesar cada detalle - validar stock
        for (DetalleVenta detalle : venta.getDetalleVentas()) {
            Producto producto = detalle.getProducto();
            Integer cantidad = detalle.getCantidad();
            if (cantidad == null || cantidad <= 0) {
                throw new IllegalArgumentException("Cantidad inválida en detalle");
            }

            // Verificar stock suficiente en StockAlmacen
            Optional<StockAlmacen> stockOpt = stockAlmacenRepository.findByProductoAndAlmacen(producto, almacen);
            int stockActual = stockOpt.map(s -> s.getCantidadActual()).orElse(0);
            
            if (stockActual < cantidad) {
                throw new IllegalStateException("Stock insuficiente en almacén para: " + producto.getNombre() + ". Stock actual: " + stockActual);
            }

            // Calcular valores del detalle
            double precio = producto.getPrecioVenta();
            detalle.setPrecioUnitario(precio);
            double subDetalle = cantidad * precio;
            detalle.setSubtotal(subDetalle);

            subtotal += subDetalle;

            double descuentoItem = detalle.getDescuentoItem() != null ? detalle.getDescuentoItem() : 0;
            descuentoTotal += descuentoItem;

            // Sincronizar relación bidireccional
            detalle.setVenta(venta);
        }

        // 3. Calcular totales finales
        venta.setSubtotal(subtotal);
        venta.setDescuento(descuentoTotal);
        venta.setImpuesto(subtotal * 0.13);
        venta.setTotal(subtotal + venta.getImpuesto() - descuentoTotal);

        // 4. Guardar la venta + detalles (cascade)
        Venta ventaGuardada = ventasRepository.save(venta);

        // 5. Registrar movimientos y actualizar stock en StockAlmacen
        for (DetalleVenta detalle : ventaGuardada.getDetalleVentas()) {
            Producto producto = productoRepository.findById(detalle.getProducto().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + detalle.getProducto().getId()));

            // Actualizar stock en StockAlmacen
            StockAlmacen stockAlmacen = stockAlmacenRepository
                    .findByProductoAndAlmacen(producto, almacen)
                    .orElseThrow(() -> new IllegalArgumentException("No hay stock para el producto en este almacén"));
            
            // Guardar stock anterior antes de modificar
            int stockAnterior = stockAlmacen.getCantidadActual();
            int stockPosterior = stockAnterior - detalle.getCantidad();
            
            stockAlmacen.setCantidadActual(stockPosterior);
            stockAlmacenRepository.save(stockAlmacen);
            
            // Registrar movimiento de inventario
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

        return ventaGuardada;
    }

}
