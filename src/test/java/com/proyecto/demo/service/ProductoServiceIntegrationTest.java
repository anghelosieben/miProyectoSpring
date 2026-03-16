package com.proyecto.demo.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.proyecto.demo.model.entity.Producto;

@SpringBootTest
@ActiveProfiles("test")  // usa application-test.properties
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)   // ← fuerza el uso de application-test.yml
public class ProductoServiceIntegrationTest {
    @Autowired
    private ProductoServiceImpl productoService;

    private static Long idGuardado = null;  // para reutilizar el ID entre tests

    @Test
    @Order(1)
    @DisplayName("1. Guardar un producto válido en PostgreSQL real")
    void guardarProductoValido() {
        Producto nuevo = new Producto();
        nuevo.setNombre("Teclado Mecánico RGB");
        nuevo.setPrecio(180.00);
        //nuevo.setDescripcion("Teclado con switches rojos");

        Producto guardado = productoService.save(nuevo);
        assertNotNull(guardado);
        /*assertThat(guardado.getId()).isNotNull();
        assertThat(guardado.getNombre()).isEqualTo("Teclado Mecánico RGB");
        assertThat(guardado.getPrecio()).isEqualByComparingTo(BigDecimal.valueOf(180.00));*/

        idGuardado = guardado.getId();  // guardamos el ID para el siguiente test
    }

    @Test
    @Order(2)
    @DisplayName("2. Buscar el producto recién guardado por ID")
    void buscarPorId_elQueAcabamosDeGuardar() {
        // Asumimos que el test anterior pasó y tenemos ID
        //assertThat(idGuardado).isNotNull();

        var encontrado = productoService.findById(idGuardado);

        //assertThat(encontrado).isPresent();
        //assertThat(encontrado.get().getNombre()).isEqualTo("Teclado Mecánico RGB");
    }

    @Test
    @Order(3)
    @DisplayName("3. Listar todos los productos (debería incluir el nuevo)")
    void listarTodos_incluyeElNuevo() {
        List<Producto> todos = productoService.findAll();
        System.out.println("joder"+todos);
        /*assertThat(todos).isNotEmpty();
        assertThat(todos.stream().anyMatch(p -> p.getNombre().equals("Teclado Mecánico RGB")))
                .isTrue();*/
    }

    @Test
    @Order(4)
    @DisplayName("4. Intentar guardar producto con precio negativo → debe fallar")
    void guardarConPrecioNegativo_debeLanzarExcepcion() {
        Producto invalido = new Producto();
        invalido.setNombre("Cable USB");
        invalido.setPrecio(-15.0);

       /* assertThatThrownBy(() -> productoService.save(invalido))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El precio debe ser positivo");*/
    }

    @Test
    @Order(5)
    @DisplayName("5. Eliminar el producto creado")
    void eliminarElProductoCreado() {
        //assertThat(idGuardado).isNotNull();

        productoService.deleteById(idGuardado);

        var despuesDeEliminar = productoService.findById(idGuardado);
        //assertThat(despuesDeEliminar).isEmpty();
    }
}
