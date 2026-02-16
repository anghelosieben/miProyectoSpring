package com.proyecto.demo.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.demo.model.entity.DetalleVenta;
import com.proyecto.demo.model.entity.MovimientoInventario;
import com.proyecto.demo.model.entity.Producto;
import com.proyecto.demo.model.entity.Venta;
import com.proyecto.demo.repository.MovimientoInventarioRepository;
import com.proyecto.demo.repository.ProductoRepository;
import com.proyecto.demo.repository.VentasRepository;

@Service
@Transactional(readOnly = true)
public class VentaServiceImpl implements VentaService {
    private final VentasRepository ventasRepository;
    private final ProductoRepository productoRepository;
    private final MovimientoInventarioRepository movimientoInventarioRepository;
    //private final DetalleVentasRepository DetalleVentasRepository;

    public VentaServiceImpl(VentasRepository ventasRepository, ProductoRepository productoRepository, MovimientoInventarioRepository movimientoInventarioRepository) {
        this.ventasRepository = ventasRepository;
        this.productoRepository = productoRepository;
        this.movimientoInventarioRepository = movimientoInventarioRepository;
    }

    @Override
    public List<Venta> findAll() {
        // TODO Auto-generated method stub
        var pVentas = ventasRepository.findAll();
        return pVentas;
    }

    @Override
    public Venta findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional
    public Venta realizarVenta(Venta venta) {
        // 1. Validaciones básicas
        if (venta.getCliente() == null) {
            throw new IllegalArgumentException("La venta debe tener un cliente");
        }
        if (venta.getDetalleVentas() == null || venta.getDetalleVentas().isEmpty()) {
            throw new IllegalArgumentException("La venta debe tener al menos un detalle");
        }

        double subtotal = 0.0;
        double descuentoTotal = 0.0;

        // 2. Procesar cada detalle (todo en memoria, sin guardar aún)
        for (DetalleVenta detalle : venta.getDetalleVentas()) {
            /*Producto producto = productoRepository.findById(detalle.getProducto().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + detalle.getProducto().getId()));*/
            Producto producto = detalle.getProducto();
            Integer cantidad = detalle.getCantidad();
            if (cantidad == null || cantidad <= 0) {
                throw new IllegalArgumentException("Cantidad inválida en detalle");
            }

            // Verificar stock suficiente (antes de cualquier cambio)
            if (producto.getStock() < cantidad) {
                throw new IllegalStateException("Stock insuficiente para: " + producto.getNombre());
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

        // 5. Registrar movimientos y actualizar stock (todo en la misma transacción)
        for (DetalleVenta detalle : ventaGuardada.getDetalleVentas()) {
            Producto producto = productoRepository.findById(detalle.getProducto().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + detalle.getProducto().getId()));

            MovimientoInventario mov = MovimientoInventario.builder()
                    .producto(producto)
                    .cantidad(BigDecimal.valueOf(detalle.getCantidad()).negate())
                    .tipoMovimiento("VENTA")
                    .documentoReferencia("Venta ID: " + ventaGuardada.getId())
                    .observacion("Movimiento generado por venta")
                    .build();

            movimientoInventarioRepository.save(mov);

            // Restar stock
            producto.setStock(producto.getStock() - detalle.getCantidad());
            productoRepository.save(producto);
        }

        return ventaGuardada;
    }

}
