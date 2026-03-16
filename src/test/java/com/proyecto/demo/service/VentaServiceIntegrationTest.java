/*package com.proyecto.demo.service;

import com.proyecto.demo.model.entity.*;
import com.proyecto.demo.repository.ClienteRepository;
import com.proyecto.demo.repository.MovimientoInventarioRepository;
import com.proyecto.demo.repository.ProductoRepository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")  // ← usa application-test.properties si lo tienes
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VentaServiceIntegrationTest {

    @Autowired
    private VentaServiceImpl ventaService;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ClienteRepository clienteRepository;  // asumo que tienes este repo

    @Autowired
    private MovimientoInventarioRepository movimientoRepository;

    private static Long idVentaGuardada = null;

    @Test
    @Order(1)
    @DisplayName("1. Crear y guardar una venta válida con detalles")
    void crearVentaValida() {
        // 1. Crear un cliente de prueba (si no existe, créalo o usa uno existente)
        Cliente cliente = clienteRepository.findById(1L)
                .orElseGet(() -> {
                    Cliente nuevo = new Cliente();
                    nuevo.setNombre("Cliente Prueba");
                    nuevo.setNit("123456");
                    return clienteRepository.save(nuevo);
                });

        // 2. Crear productos de prueba (o usa existentes)
        Producto prod1 = productoRepository.findById(1L)
                .orElseGet(() -> {
                    Producto p = new Producto();
                    p.setNombre("Producto Test 1");
                    p.setPrecioVenta(100L);
                    p.setStock(50L);
                    return productoRepository.save(p);
                });

        Producto prod2 = productoRepository.findById(2L)
                .orElseGet(() -> {
                    Producto p = new Producto();
                    p.setNombre("Producto Test 2");
                    p.setPrecioVenta(200L);
                    p.setStock(30L);
                    return productoRepository.save(p);
                });

        // 3. Crear la venta con detalles
        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setFormaPago("EFECTIVO");
        venta.setObservaciones("Venta de prueba para test");

        List<DetalleVenta> detalles = new ArrayList<>();

        DetalleVenta dv1 = new DetalleVenta();
        dv1.setProducto(prod1);
        dv1.setCantidad(2);
        dv1.setDescuentoItem(0.0);
        detalles.add(dv1);

        DetalleVenta dv2 = new DetalleVenta();
        dv2.setProducto(prod2);
        dv2.setCantidad(1);
        dv2.setDescuentoItem(10.0);
        detalles.add(dv2);

        venta.setDetalleVentas(detalles);

        // 4. Guardar la venta
        Venta guardada = ventaService.realizarVenta(venta);

        assertNotNull(guardada);
        assertNotNull(guardada.getId());
        assertEquals("EFECTIVO", guardada.getFormaPago());
        assertEquals(2, guardada.getDetalleVentas().size());

        // Guardamos el ID para los siguientes tests
        idVentaGuardada = guardada.getId();

        System.out.println("Venta guardada con ID: " + idVentaGuardada);
    }

    @Test
    @Order(2)
    @DisplayName("2. Verificar que la venta se puede recuperar por ID")
    void buscarVentaPorId() {
        assertNotNull(idVentaGuardada, "Debe haber una venta guardada del test anterior");

        Venta encontrada = ventaService.findById(idVentaGuardada);

        assertNotNull(encontrada);
        assertEquals(idVentaGuardada, encontrada.getId());
        assertEquals(2, encontrada.getDetalleVentas().size());
    }

    /*@Test
    @Order(3)
    @DisplayName("3. Verificar que se creó el movimiento de inventario y se actualizó el stock")
    void verificarMovimientoYStock() {
        assertNotNull(idVentaGuardada, "Debe haber una venta guardada");

        Venta venta = ventaService.findById(idVentaGuardada);

        // Buscar movimientos relacionados con esta venta
        List<MovimientoInventario> movimientos = movimientoRepository.findByDocumentoReferenciaContaining("Venta ID: " + idVentaGuardada);

        assertFalse(movimientos.isEmpty(), "Deben existir movimientos de inventario para esta venta");

        // Verificar que hay 2 movimientos (uno por cada detalle)
        assertEquals(2, movimientos.size());

        // Verificar que la cantidad es negativa (salida)
        for (MovimientoInventario mov : movimientos) {
            assertTrue(mov.getCantidad().compareTo(BigDecimal.ZERO) < 0);
            assertEquals("VENTA", mov.getTipoMovimiento());
        }

        // Verificar que el stock del producto se redujo
        Producto prod = productoRepository.findById(venta.getDetalleVentas().get(0).getProducto().getId()).orElseThrow();
        // Aquí tendrías que saber el stock inicial para comparar, pero al menos verifica que no sea null
        assertNotNull(prod.getStock());
    }*/

   /* @Test
    @Order(4)
    @DisplayName("4. Intentar crear venta sin detalles → debe fallar")
    void crearVentaSinDetalles_debeLanzarExcepcion() {
        Venta ventaInvalida = new Venta();
        ventaInvalida.setCliente(new Cliente()); // cliente dummy
        ventaInvalida.setDetalleVentas(new ArrayList<>()); // sin detalles

        assertThrows(IllegalArgumentException.class, () -> {
            ventaService.realizarVenta(ventaInvalida);
        });
    }

    @Test
    @Order(5)
    @DisplayName("5. Intentar crear venta sin cliente → debe fallar")
    void crearVentaSinCliente_debeLanzarExcepcion() {
        Venta ventaInvalida = new Venta();
        ventaInvalida.setDetalleVentas(List.of(new DetalleVenta())); // detalle dummy

        assertThrows(IllegalArgumentException.class, () -> {
            ventaService.realizarVenta(ventaInvalida);
        });
    }
    

}*/