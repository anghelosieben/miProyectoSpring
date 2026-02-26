package com.proyecto.demo.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.demo.model.entity.Compra;
import com.proyecto.demo.model.entity.DetalleCompra;
import com.proyecto.demo.model.entity.MovimientoInventario;
import com.proyecto.demo.model.entity.Producto;
import com.proyecto.demo.repository.CompraRepository;
import com.proyecto.demo.repository.MovimientoInventarioRepository;
import com.proyecto.demo.repository.ProductoRepository;

/**
 * @author Anghelo Muñoz Lopez
 */
@Service
@Transactional(readOnly = false)
public class CompraServiceImpl implements CompraService {
    private final CompraRepository compraRepository;
    private final MovimientoInventarioRepository movimientoInventarioRepository;
    private final ProductoRepository productoRepository;

    public CompraServiceImpl(CompraRepository compraRepository, MovimientoInventarioRepository movimientoInventarioRepository, ProductoRepository productoRepository) {
        this.compraRepository = compraRepository;
        this.movimientoInventarioRepository = movimientoInventarioRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional
    public Compra realizarCompra(Compra compra) {
        // 1. Validaciones básicas (importante para evitar datos inválidos)
        /*if (compra.getProveedor() == null) {
            throw new IllegalArgumentException("La compra debe tener un proveedor");
        }*/
       System.out.println("Compra recibida: " + compra);
        if (compra.getDetalleCompras() == null || compra.getDetalleCompras().isEmpty()) {
            throw new IllegalArgumentException("La compra debe tener al menos un detalle");
        }
        double subtotal = 0.0;

        // 2. Procesar cada detalle (validar, calcular, registrar movimiento, actualizar stock)
        for (DetalleCompra detalle : compra.getDetalleCompras()) {
            // Obtener producto (usamos el que ya viene cargado en el detalle, si no → buscamos)
            Producto producto = detalle.getProducto();
            if (producto == null || producto.getId() == null) {
                throw new IllegalArgumentException("Todo detalle debe tener un producto válido");
            }

            // Refrescamos el producto desde BD para tener el stock actual (evita concurrencia)
           /*  producto = productoRepository.findById(producto.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + producto.getId()));
            */
            Integer cantidad = detalle.getCantidad();
            if (cantidad == null || cantidad <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
            }

            // Calcular subtotal del detalle
            double precio = detalle.getPrecio_unitario();  // debe venir del frontend o del producto
            if (precio <= 0) {
                precio = producto.getPrecioCompra();  // fallback al precio de compra del producto
                detalle.setPrecio_unitario(precio);
            }

            double subDetalle = cantidad * precio;
            detalle.setSubtotal(subDetalle);
            subtotal += subDetalle;

            // Sincronizar relación bidireccional (si la tienes en la entidad)
            detalle.setCompra(compra);
        }
        
        var compraGuardada = compraRepository.save(compra);

        // TODO Auto-generated method stub
        for (DetalleCompra detalle : compra.getDetalleCompras()) {
            Producto producto = productoRepository.findById(detalle.getProducto().getId()).orElse(null);  // ya está cargado
            MovimientoInventario mov = MovimientoInventario.builder()
                    .producto(producto)
                    .cantidad(BigDecimal.valueOf(detalle.getCantidad()))
                    .tipoMovimiento("COMPRA")
                    .documentoReferencia("Compra ID: " + compraGuardada.getId())
                    .observacion("Movimiento generado por compra")
                    .build();

            producto.setStock(producto.getStock() + detalle.getCantidad());        
            productoRepository.save(producto);    
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
